package br.com.madda.rock_paper_scissors.model.enums;

public enum Move {
  ROCK("Rock"), PAPER("Paper"), SCISSOR("Scissor");

  private final String name;

  Move(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public boolean win(Move another) {
    switch (this) {
      case ROCK:
        return another == SCISSOR;
      case PAPER:
        return another == ROCK;
      case SCISSOR:
        return another == PAPER;
      default:
        return false;
    }
  }

  public static Move fromString(String input) {
    switch (input.toLowerCase()) {
      case "rock":
      case "1":
        return ROCK;

      case "paper":
      case "2":
        return PAPER;

      case "scissor":
      case "3":
        return SCISSOR;

      default:
        throw new IllegalArgumentException("Invalid move: " + input);
    }
  }
}
