package br.com.madda.rock_paper_scissors.view;

import java.util.Scanner;
import br.com.madda.rock_paper_scissors.dto.ScoreboardDTO;
import br.com.madda.rock_paper_scissors.entity.enums.Move;
import br.com.madda.rock_paper_scissors.entity.enums.Result;

public class ConsoleView {
  private final Scanner scanner;

  public ConsoleView() {
    this.scanner = new Scanner(System.in);
  }

  public void close() {
    this.scanner.close();
  }

  public void welcome() {
    System.out.println("=================================");
    System.out.println("  WELCOME TO ROCK PAPER SCISSORS!  ");
    System.out.println("=================================");
    System.out.println();
  }

  public String getPlayerName() {
    System.out.print("Enter your name: ");
    return scanner.nextLine().trim();
  }

  public void displayPlayerMessage(String nome) {
    System.out.println("Hi, " + nome + "! Let's play!");
    System.out.println();
  }

  public void displayScoreHistory(ScoreboardDTO scoreboard) {
    System.out.println("--- YOUR HISTORY ---");
    System.out.println("Victories: " + scoreboard.getVictories());
    System.out.println("Defeats: " + scoreboard.getDefeats());
    System.out.println("Draws: " + scoreboard.getDraws());
    System.out.println("Total games: " + scoreboard.getTotalGames());
    System.out.println();
  }

  public Move getPlayerMove() {
    System.out.println("Choose your move:");
    System.out.println("1 - Rock");
    System.out.println("2 - Paper");
    System.out.println("3 - Scissors");
    System.out.print("Your choice: ");

    String input = scanner.nextLine().trim();
    return Move.fromString(input);
  }

  public void displayRoundResult(Move playerMove, Move computerMove, Result result) {
    System.out.println();
    System.out.println("--- ROUND RESULT ---");
    System.out.println("You chose: " + playerMove.getName());
    System.out.println("Computer chose: " + computerMove.getName());
    System.out.println(result.getMessage());
    System.out.println();
  }

  public void displayScoreboard(ScoreboardDTO scoreboard) {
    System.out.println("--- CURRENT SCOREBOARD ---");
    System.out.println("Player: " + scoreboard.getPlayerName());
    System.out.println("Victories: " + scoreboard.getVictories());
    System.out.println("Defeats: " + scoreboard.getDefeats());
    System.out.println("Draws: " + scoreboard.getDraws());
    System.out.println("Total games: " + scoreboard.getTotalGames());

    if (scoreboard.getTotalGames() > 0) {
      double percentageOfVictory =
          (double) scoreboard.getVictories() / scoreboard.getTotalGames() * 100;
      System.out.printf("Win rate: %.1f%%\n", percentageOfVictory);
    }
    System.out.println();
  }

  public boolean playAgain() {
    System.out.print("Do you want to play again? (y/n): ");
    String answer = scanner.nextLine().trim().toLowerCase();
    return answer.equals("y") || answer.equals("yes");
  }

  public void displayError(String message) {
    System.out.println("ERROR: " + message);
    System.out.println("Try again.");
    System.out.println();
  }

  public void showFarewell(ScoreboardDTO scoreboard) {
    System.out.println("=================================");
    System.out.println("         END OF THE GAME!");
    System.out.println("=================================");

    if (scoreboard.getTotalGames() > 0) {
      System.out.println("--- FINAL STATISTICS ---");
      if (scoreboard.getVictories() > scoreboard.getDefeats()) {
        System.out.println("Congratulations! You've had more wins! 🎉");
      } else if (scoreboard.getDefeats() > scoreboard.getVictories()) {
        System.out.println("The computer was better this time! 🤖");
      } else {
        System.out.println("Technical draw! They played very well! 🤝");
      }
    }

    System.out.println("Thanks for playing, " + scoreboard.getPlayerName() + "!");
    System.out.println("Your data has been saved. See you next time!");
  }
}
