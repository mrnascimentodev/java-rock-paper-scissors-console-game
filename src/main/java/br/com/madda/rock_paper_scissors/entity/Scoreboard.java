package br.com.madda.rock_paper_scissors.entity;

public class Scoreboard {
  private int playerVictories;
  private int computerVictories;
  private int draws;

  public void addVictory() {
    playerVictories++;
  }

  public void addDefeat() {
    computerVictories++;
  }

  public void addTie() {
    draws++;
  }

  public int getPlayerVictories() {
    return playerVictories;
  }

  public int getComputerVictories() {
    return computerVictories;
  }

  public int getDraws() {
    return draws;
  }

  public int getTotalGames() {
    return getPlayerVictories() + getComputerVictories() + getDraws();
  }

  public void reset() {
    playerVictories = 0;
    computerVictories = 0;
    draws = 0;
  }
}
