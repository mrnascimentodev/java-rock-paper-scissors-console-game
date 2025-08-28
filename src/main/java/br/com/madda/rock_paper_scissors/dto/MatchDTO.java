package br.com.madda.rock_paper_scissors.dto;

import java.time.Instant;
import br.com.madda.rock_paper_scissors.entity.Player;

public class MatchDTO {
  private Long id;
  private Player player;
  private String playerPlay;
  private String computerPlay;
  private String result;
  private Instant createdAt;

  public MatchDTO() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public String getPlayerPlay() {
    return playerPlay;
  }

  public void setPlayerPlay(String playerPlay) {
    this.playerPlay = playerPlay;
  }

  public String getComputerPlay() {
    return computerPlay;
  }

  public void setComputerPlay(String computerPlay) {
    this.computerPlay = computerPlay;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }
}
