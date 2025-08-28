package br.com.madda.rock_paper_scissors.config.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import br.com.madda.rock_paper_scissors.config.DatabaseConfig;
import io.github.cdimascio.dotenv.Dotenv;

public class PostgreSQLDatabaseConfig implements DatabaseConfig {
  private static final Dotenv dotenv = Dotenv.configure().directory("./").ignoreIfMissing().load();

  protected static final String DB_URL = dotenv.get("DB_URL");
  protected static final String DB_USER = dotenv.get("DB_USER");
  protected static final String DB_PASSWORD = dotenv.get("DB_PASSWORD");

  @Override
  public Connection getConnection() throws SQLException {
    try {
      Class.forName("org.postgresql.Driver");

      if (DB_URL == null || DB_USER == null || DB_PASSWORD == null) {
        throw new SQLException(
            "Environment variables not defined. Please set: DB_URL, DB_USER, DB_PASSWORD");
      }

      Properties props = new Properties();
      props.setProperty("user", DB_USER);
      props.setProperty("password", DB_PASSWORD);
      props.setProperty("ssl", "true");
      props.setProperty("sslmode", "require");
      props.setProperty("channelBinding", "require");

      Connection connection = DriverManager.getConnection(DB_URL, props);
      createTables(connection);
      return connection;
    } catch (ClassNotFoundException e) {
      throw new SQLException("Driver PostgreSQL not found", e);
    }
  }

  @Override
  public boolean testConnection() {
    try (Connection conn = getConnection()) {
      return conn.isValid(5);
    } catch (SQLException e) {
      System.err.println("❌ Error connecting to PostgreSQL Neon: " + e.getMessage());
      return false;
    }
  }

  @Override
  public void closeConnection(Connection connection) throws SQLException {
    if (connection != null && !connection.isClosed()) {
      connection.close();
    }
  }

  @Override
  public String getDatabaseType() {
    return "PostgreSQL Neon Cloud Database";
  }

  private void createTables(Connection connection) throws SQLException {
    try (Statement stmt = connection.createStatement()) {
      stmt.execute("""
              CREATE TABLE IF NOT EXISTS players (
                  id BIGSERIAL PRIMARY KEY,
                  name VARCHAR(100) NOT NULL UNIQUE,
                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
              )
          """);

      stmt.execute("""
              CREATE TABLE IF NOT EXISTS matches (
                  id BIGSERIAL PRIMARY KEY,
                  player_id BIGINT NOT NULL,
                  player_play VARCHAR(20) NOT NULL,
                  computer_play VARCHAR(20) NOT NULL,
                  result VARCHAR(20) NOT NULL,
                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                  CONSTRAINT fk_match_player
                        FOREIGN KEY (player_id)
                        REFERENCES players(id)
                        ON DELETE CASCADE
              )
          """);

      stmt.execute("""
              CREATE INDEX IF NOT EXISTS idx_matches_player_id
              ON matches(player_id)
          """);

      stmt.execute("""
              CREATE INDEX IF NOT EXISTS idx_matches_created_at
              ON matches(created_at DESC)
          """);

      stmt.execute("""
              CREATE OR REPLACE FUNCTION update_updated_at_column()
              RETURNS TRIGGER AS $$
              BEGIN
                  NEW.updated_at = CURRENT_TIMESTAMP;
                  RETURN NEW;
              END;
              $$ language 'plpgsql'
          """);

      stmt.execute("""
              DO $$
              BEGIN
                  IF NOT EXISTS (
                      SELECT 1 FROM pg_trigger
                      WHERE tgname = 'update_players_updated_at'
                  ) THEN
                      CREATE TRIGGER update_players_updated_at
                          BEFORE UPDATE ON players
                          FOR EACH ROW
                          EXECUTE FUNCTION update_updated_at_column();
                  END IF;
              END $$
          """);

      System.out.println("✅ Tables created/verified successfully in PostgreSQL Neon");
    }
  }
}
