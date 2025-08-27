package br.com.madda.rock_paper_scissors.controller;

import br.com.madda.rock_paper_scissors.dto.PlayerDTO;
import br.com.madda.rock_paper_scissors.dto.ScoreboardDTO;
import br.com.madda.rock_paper_scissors.model.enums.Move;
import br.com.madda.rock_paper_scissors.model.enums.Result;
import br.com.madda.rock_paper_scissors.service.ComputerService;
import br.com.madda.rock_paper_scissors.service.GameService;
import br.com.madda.rock_paper_scissors.service.MatchService;
import br.com.madda.rock_paper_scissors.service.PlayerService;
import br.com.madda.rock_paper_scissors.view.ConsoleView;

public class MatchController {
  private final ConsoleView view;
  private final ComputerService computerService;
  private final GameService gameService;
  private final PlayerService playerService;
  private final MatchService matchService;

  public MatchController(ConsoleView view, ComputerService computerService, GameService gameService,
      PlayerService playerService, MatchService matchService) {
    this.view = view;
    this.computerService = computerService;
    this.gameService = gameService;
    this.playerService = playerService;
    this.matchService = matchService;
  }

  public void startGame() {
    try {
      view.welcome();

      String playerName = view.getPlayerName();
      PlayerDTO player = this.playerService.createOrFindPlayer(playerName);

      this.view.displayPlayerMessage(player.getName());

      ScoreboardDTO historicalScoreboard =
          this.matchService.getScoreboard(player.getId(), player.getName());
      if (historicalScoreboard.getTotalGames() > 0) {
        this.view.displayScoreHistory(historicalScoreboard);
      }

      boolean continuePlaying = true;

      while (continuePlaying) {
        try {
          Move playerMove = this.view.getPlayerMove();
          Move computerMove = this.computerService.makeMove();
          Result result = this.gameService.calculateResult(playerMove, computerMove);

          this.matchService.save(player.getId(), playerMove, computerMove, result);

          this.view.displayRoundResult(playerMove, computerMove, result);

          ScoreboardDTO updatedScoreboard =
              this.matchService.getScoreboard(player.getId(), player.getName());
          this.view.displayScoreboard(updatedScoreboard);

          continuePlaying = this.view.playAgain();
        } catch (IllegalArgumentException e) {
          view.displayError(e.getMessage());
        }
      }
    } catch (Exception e) {
      this.view.displayError("Internal game error: " + e.getMessage());
      this.view.close();
      e.printStackTrace();
    }
  }
}
