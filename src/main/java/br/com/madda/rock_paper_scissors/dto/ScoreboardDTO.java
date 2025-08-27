package br.com.madda.rock_paper_scissors.dto;

public class ScoreboardDTO {
  private String playerName;
  private int victories;
  private int defeats;
  private int draws;
  private int totalGames;

  public ScoreboardDTO() {}

  public ScoreboardDTO(String playerName, int victories, int defeats, int draws) {
    this.playerName = playerName;
    this.victories = victories;
    this.defeats = defeats;
    this.draws = draws;
  }

  public String getPlayerName() {
    return playerName;
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  public int getVictories() {
    return victories;
  }

  public void setVictories(int victories) {
    this.victories = victories;
  }

  public int getDefeats() {
    return defeats;
  }

  public void setDefeats(int defeats) {
    this.defeats = defeats;
  }

  public int getDraws() {
    return draws;
  }

  public void setDraws(int draws) {
    this.draws = draws;
  }

  public int getTotalGames() {
    return totalGames;
  }

  public void setTotalGames(int totalGames) {
    this.totalGames = totalGames;
  }
}
