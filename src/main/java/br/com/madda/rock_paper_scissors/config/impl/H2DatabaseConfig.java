package br.com.madda.rock_paper_scissors.config.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import br.com.madda.rock_paper_scissors.config.DatabaseConfig;

public class H2DatabaseConfig implements DatabaseConfig {
  private static final String DB_URL = "jdbc:h2:mem:rockpaperscissors;DB_CLOSE_DELAY=-1";
  private static final String DB_USER = "sa";
  private static final String DB_PASSWORD = "";

  @Override
  public Connection getConnection() throws SQLException {
    Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    this.createTables(connection);
    return connection;
  }

  @Override
  public boolean testConnection() {
    try (Connection conn = getConnection()) {
      return conn.isValid(5);
    } catch (SQLException e) {
      System.err.println("❌ Error connecting to H2: " + e.getMessage());
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
    return "H2 In-Memory Database";
  }

  private void createTables(Connection connection) throws SQLException {
    try (Statement stmt = connection.createStatement()) {
      stmt.execute("""
              CREATE TABLE IF NOT EXISTS players (
                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                  name VARCHAR(100) NOT NULL,
                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
              )
          """);

      stmt.execute("""
              CREATE TABLE IF NOT EXISTS matches (
                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                  player_id BIGINT NOT NULL,
                  player_play VARCHAR(20) NOT NULL,
                  computer_play VARCHAR(20) NOT NULL,
                  result VARCHAR(20) NOT NULL,
                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                  FOREIGN KEY (player_id) REFERENCES players(id)
              )
          """);
    }
  }
}
