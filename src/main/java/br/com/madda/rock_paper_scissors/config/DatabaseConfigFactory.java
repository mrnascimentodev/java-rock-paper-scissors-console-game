package br.com.madda.rock_paper_scissors.config;

import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseConfigFactory {
  private static final Dotenv dotenv = Dotenv.configure().directory("./").ignoreIfMissing().load();

  public enum DatabaseType {
    H2, POSTGRESQL
  }

  public static DatabaseConfig create(DatabaseType type) {
    switch (type) {
      case H2:
        return new H2DatabaseConfig();
      case POSTGRESQL:
        return new PostgreSQLDatabaseConfig();
      default:
        throw new IllegalArgumentException("Unsupported database type: " + type);
    }
  }

  public static DatabaseConfig createAuto() {
    String dbUrl = dotenv.get("DB_URL");

    if (dbUrl != null && !dbUrl.trim().isEmpty()) {
      System.out.println("🔍 Detected PostgreSQL Neon configuration");
      return new PostgreSQLDatabaseConfig();
    } else {
      System.out.println("🔍 Using H2 in-memory database");
      return new H2DatabaseConfig();
    }
  }
}
