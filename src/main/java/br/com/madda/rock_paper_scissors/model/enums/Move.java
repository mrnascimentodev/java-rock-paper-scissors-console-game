package br.com.madda.rock_paper_scissors.model.enums;

public enum Move {
  ROCK("Rock", "Scissors"), PAPER("Paper", "Rock"), SCISSOR("Scissor", "Paper");

  private final String name;
  private final String win;

  private Move(String name, String win) {
    this.name = name;
    this.win = win;
  }

  public String getName() {
    return this.name;
  }

  public boolean win(Move another) {
    return this.win.equals(another);
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
