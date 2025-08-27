package br.com.madda.rock_paper_scissors.dto;

import java.time.LocalDateTime;

public class MatchDTO {
  private Long id;
  private Long playerId;
  private String playerMove;
  private String computerMove;
  private String result;
  private LocalDateTime createdAt;

  public MatchDTO() {}

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

  public String getPlayerMove() {
    return playerMove;
  }

  public void setPlayerMove(String playerMove) {
    this.playerMove = playerMove;
  }

  public String getComputerMove() {
    return computerMove;
  }

  public void setComputerMove(String computerMove) {
    this.computerMove = computerMove;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
