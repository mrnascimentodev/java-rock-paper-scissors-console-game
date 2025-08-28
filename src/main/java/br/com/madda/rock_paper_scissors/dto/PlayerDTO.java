package br.com.madda.rock_paper_scissors.dto;

import java.time.Instant;

public class PlayerDTO {
  private Long id;
  private String name;
  private Instant createdAt;
  private Instant updatedAt;

  public PlayerDTO() {}

  public PlayerDTO(Long id, String name, Instant createdAt, Instant updatedAt) {
    this.id = id;
    this.name = name;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }
}
