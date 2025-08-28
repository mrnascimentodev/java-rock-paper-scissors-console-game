package br.com.madda.rock_paper_scissors.config;

import java.util.HashMap;
import java.util.Map;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAConfig {
  private static final Logger logger = LoggerFactory.getLogger(JPAConfig.class);
  private static EntityManagerFactory entityManagerFactory;
  private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

  static {
    initialize();
  }

  private static void initialize() {
    try {
      runMigrations();

      entityManagerFactory = createEntityManagerFactory();

      logger.info("JPA configured successfully!");
    } catch (Exception e) {
      logger.error("Error configuring JPA: {}", e.getMessage(), e);
      throw new RuntimeException("JPA configuration failed", e);
    }
  }

  private static void runMigrations() {
    try {
      String dbUrl = getDbUrl();
      String dbUser = getDbUser();
      String dbPassword = getDbPassword();

      Flyway flyway = Flyway.configure().dataSource(dbUrl, dbUser, dbPassword)
          .locations("classpath:db/migration").baselineOnMigrate(true).validateOnMigrate(true)
          .load();

      flyway.migrate();
      logger.info("Migrations executed successfully!");

    } catch (Exception e) {
      logger.error("Error running migrations: {}", e.getMessage(), e);
      throw new RuntimeException("Migrations failed", e);
    }
  }

  private static EntityManagerFactory createEntityManagerFactory() {
    String persistenceUnit = isPostgreSQL() ? "postgresql-unit" : "h2-unit";

    if (isPostgreSQL()) {
      Map<String, String> properties = new HashMap<>();
      properties.put("jakarta.persistence.jdbc.url", getDbUrl());
      properties.put("jakarta.persistence.jdbc.user", getDbUser());
      properties.put("jakarta.persistence.jdbc.password", getDbPassword());

      return Persistence.createEntityManagerFactory(persistenceUnit, properties);
    } else {
      return Persistence.createEntityManagerFactory(persistenceUnit);
    }
  }

  public static EntityManager createEntityManager() {
    if (entityManagerFactory == null) {
      throw new IllegalStateException("EntityManagerFactory has not been initialized!");
    }
    return entityManagerFactory.createEntityManager();
  }

  public static void close() {
    if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
      entityManagerFactory.close();
      logger.info("EntityManagerFactory closed.");
    }
  }

  private static boolean isPostgreSQL() {
    String dbUrl = getDbUrl();
    return dbUrl != null && dbUrl.contains("postgresql");
  }

  private static String getDbUrl() {
    return dotenv.get("DB_URL", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
  }

  private static String getDbUser() {
    return dotenv.get("DB_USER", "sa");
  }

  private static String getDbPassword() {
    return dotenv.get("DB_PASSWORD", "");
  }

  public static void resetForTesting() {
    if (entityManagerFactory != null) {
      entityManagerFactory.close();
    }
    initialize();
  }
}
