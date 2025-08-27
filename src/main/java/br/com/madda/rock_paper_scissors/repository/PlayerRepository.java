package br.com.madda.rock_paper_scissors.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import br.com.madda.rock_paper_scissors.model.Player;

public class PlayerRepository {
  private final Connection connection;

  public PlayerRepository(Connection connection) {
    this.connection = connection;
  }

  public Player save(Player player) throws SQLException {
    if (player.getId() == null) {
      return insert(player);
    }

    return update(player);
  }

  private Player insert(Player player) throws SQLException {
    String sql = "INSERT INTO players (name, created_at, updated_at) VALUES (?, ?, ?)";

    try (PreparedStatement stmt =
        connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, player.getName());
      stmt.setTimestamp(2, Timestamp.valueOf(player.getCreatedAt()));
      stmt.setTimestamp(3, Timestamp.valueOf(player.getUpdatedAt()));

      int affectedRows = stmt.executeUpdate();

      if (affectedRows == 0) {
        throw new SQLException("Failed to insert player");
      }

      try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          player.setId(generatedKeys.getLong(1));
        }
      }
    }

    return player;
  }

  private Player update(Player player) throws SQLException {
    String sql = "UPDATE players SET name = ?, updated_at = ? WHERE id = ?";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, player.getName());
      stmt.setTimestamp(2, Timestamp.valueOf(player.getUpdatedAt()));
      stmt.setLong(3, player.getId());

      stmt.executeUpdate();
    }

    return player;
  }

  public Optional<Player> findById(Long id) throws SQLException {
    String sql = "SELECT * FROM players WHERE id = ?";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setLong(1, id);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return Optional.of(mapPlayer(rs));
        }
      }
    }

    return Optional.empty();
  }

  public Optional<Player> findByName(String name) throws SQLException {
    String sql = "SELECT * FROM players WHERE name = ?";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, name);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return Optional.of(mapPlayer(rs));
        }
      }
    }

    return Optional.empty();
  }

  public List<Player> findAll() throws SQLException {
    String sql = "SELECT * FROM players ORDER BY name";
    List<Player> players = new ArrayList<>();

    try (PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        players.add(mapPlayer(rs));
      }
    }

    return players;
  }

  private Player mapPlayer(ResultSet rs) throws SQLException {
    Player player = new Player();
    player.setId(rs.getLong("id"));
    player.setName(rs.getString("name"));
    player.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
    player.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
    return player;
  }
}
