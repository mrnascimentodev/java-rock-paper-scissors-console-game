package br.com.madda.rock_paper_scissors.entity.enums;

public enum Result {
  VICTORY("You won!"), DEFEAT("You lost!"), TIE("Tied!");

  private final String message;

  Result(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
