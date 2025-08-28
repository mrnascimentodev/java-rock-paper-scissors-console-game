package br.com.madda.rock_paper_scissors.entity;

import java.time.Instant;
import java.util.Objects;
import br.com.madda.rock_paper_scissors.entity.enums.Move;
import br.com.madda.rock_paper_scissors.entity.enums.Result;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "matches")
public class Match {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "player_id", nullable = false)
  private Player player;

  @Enumerated(EnumType.STRING)
  @Column(name = "player_play", nullable = false, length = 20)
  private Move playerPlay;

  @Enumerated(EnumType.STRING)
  @Column(name = "computer_play", nullable = false, length = 20)
  private Move computerPlay;

  @Enumerated(EnumType.STRING)
  @Column(name = "result", nullable = false, length = 20)
  private Result result;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @PrePersist
  protected void onCreate() {
    this.createdAt = Instant.now();
  }

  public Match() {}

  public Match(Player player, Move playerPlay, Move computerPlay, Result result) {
    this.player = player;
    this.playerPlay = playerPlay;
    this.computerPlay = computerPlay;
    this.result = result;
  }

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

  public Move getPlayerPlay() {
    return playerPlay;
  }

  public void setPlayerPlay(Move playerPlay) {
    this.playerPlay = playerPlay;
  }

  public Move getComputerPlay() {
    return computerPlay;
  }

  public void setComputerPlay(Move computerPlay) {
    this.computerPlay = computerPlay;
  }

  public Result getResult() {
    return result;
  }

  public void setResult(Result result) {
    this.result = result;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Match match = (Match) o;
    return Objects.equals(id, match.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Match{id=%d, playerPlay=%s, computerPlay=%s, result=%s, createdAt=%s}".formatted(id,
        playerPlay, computerPlay, result, createdAt);
  }
}
