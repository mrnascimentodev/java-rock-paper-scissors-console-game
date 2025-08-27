package br.com.madda.rock_paper_scissors.model;

import java.time.LocalDateTime;
import java.util.Objects;
import br.com.madda.rock_paper_scissors.model.enums.Move;
import br.com.madda.rock_paper_scissors.model.enums.Result;

public class Match {
  private Long id;
  private Long playerId;
  private Move playerMove;
  private Move computerMove;
  private Result result;
  private LocalDateTime createdAt;

  public Match() {}

  public Match(Long playerId, Move playerMove, Move computerMove, Result result) {
    this.playerId = playerId;
    this.playerMove = playerMove;
    this.computerMove = computerMove;
    this.result = result;
    this.createdAt = LocalDateTime.now();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getPlayerId() {
    return playerId;
  }

  public void setPlayerId(Long playerId) {
    this.playerId = playerId;
  }

  public Move getPlayerMove() {
    return playerMove;
  }

  public void setPlayerMove(Move playerMove) {
    this.playerMove = playerMove;
  }

  public Move getComputerMove() {
    return computerMove;
  }

  public void setComputerMove(Move computerMove) {
    this.computerMove = computerMove;
  }

  public Result getResult() {
    return result;
  }

  public void setResult(Result result) {
    this.result = result;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Match other = (Match) obj;
    return Objects.equals(id, other.id);
  }
}
