package br.com.madda.rock_paper_scissors.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import br.com.madda.rock_paper_scissors.dto.ScoreboardDTO;
import br.com.madda.rock_paper_scissors.model.Match;
import br.com.madda.rock_paper_scissors.model.enums.Move;
import br.com.madda.rock_paper_scissors.model.enums.Result;

public class MatchRepository {
  private final Connection connection;

  public MatchRepository(Connection connection) {
    this.connection = connection;
  }

  public Match save(Match match) throws SQLException {
    String sql =
        "INSERT INTO matches (player_id, player_play, computer_play, result, created_at) VALUES (?, ?, ?, ?, ?)";

    try (PreparedStatement stmt =
        connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setLong(1, match.getPlayerId());
      stmt.setString(2, match.getPlayerMove().name());
      stmt.setString(3, match.getComputerMove().name());
      stmt.setString(4, match.getResult().name());
      stmt.setTimestamp(5, Timestamp.valueOf(match.getCreatedAt()));

      int affectedRows = stmt.executeUpdate();

      if (affectedRows == 0) {
        throw new SQLException("Failed to insert match");
      }

      try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          match.setId(generatedKeys.getLong(1));
        }
      }
    }

    return match;
  }

  public List<Match> findByPlayer(Long playerId) throws SQLException {
    String sql = "SELECT * FROM matches WHERE player_id = ? ORDER BY created_at DESC";
    List<Match> matches = new ArrayList<>();

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setLong(1, playerId);

      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          matches.add(mapMatch(rs));
        }
      }
    }

    return matches;
  }

  public ScoreboardDTO getScoreboardByPlayer(Long playerId, String playerName) throws SQLException {
    String sql = """
            SELECT
                COUNT(CASE WHEN result = 'VICTORY' THEN 1 END) as victories,
                COUNT(CASE WHEN result = 'DEFEAT' THEN 1 END) as defeats,
                COUNT(CASE WHEN result = 'TIE' THEN 1 END) as draws
            FROM matches
            WHERE player_id = ?
        """;

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setLong(1, playerId);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return new ScoreboardDTO(playerName, rs.getInt("victories"), rs.getInt("defeats"),
              rs.getInt("draws"));
        }
      }
    }

    return new ScoreboardDTO(playerName, 0, 0, 0);
  }

  private Match mapMatch(ResultSet rs) throws SQLException {
    Match match = new Match();
    match.setId(rs.getLong("id"));
    match.setPlayerId(rs.getLong("player_id"));
    match.setPlayerMove(Move.fromString(rs.getString("player_play")));
    match.setComputerMove(Move.fromString(rs.getString("computer_play")));
    match.setResult(Result.valueOf(rs.getString("result")));
    match.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
    return match;
  }
}
