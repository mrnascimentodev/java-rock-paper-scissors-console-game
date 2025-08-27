package br.com.madda.rock_paper_scissors.config;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseConfig {
  Connection getConnection() throws SQLException;

  boolean testConnection();

  void closeConnection(Connection connection) throws SQLException;

  String getDatabaseType();
}
