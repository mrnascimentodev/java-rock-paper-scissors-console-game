package br.com.madda.rock_paper_scissors.controller;

import br.com.madda.rock_paper_scissors.dto.PlayerDTO;
import br.com.madda.rock_paper_scissors.dto.ScoreboardDTO;
import br.com.madda.rock_paper_scissors.entity.Player;
import br.com.madda.rock_paper_scissors.entity.enums.Move;
import br.com.madda.rock_paper_scissors.entity.enums.Result;
import br.com.madda.rock_paper_scissors.mapper.PlayerMapper;
import br.com.madda.rock_paper_scissors.service.ComputerService;
import br.com.madda.rock_paper_scissors.service.GameService;
import br.com.madda.rock_paper_scissors.service.MatchService;
import br.com.madda.rock_paper_scissors.service.PlayerService;
import br.com.madda.rock_paper_scissors.view.ConsoleView;

public class GameController {
  private final ConsoleView view;
  private final ComputerService computerService;
  private final GameService gameService;
  private final PlayerService playerService;
  private final MatchService matchService;

  public GameController(ConsoleView view, ComputerService computerService, GameService gameService,
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
      PlayerDTO playerDTO = this.playerService.createOrFindPlayer(playerName);
      PlayerMapper playerMapper = new PlayerMapper();
    Player player = playerMapper.toEntity(playerDTO);

      this.view.displayPlayerMessage(player.getName());

      ScoreboardDTO historicalScoreboard = this.matchService.getScoreboard(player);
      if (historicalScoreboard.getTotalGames() > 0) {
        this.view.displayScoreHistory(historicalScoreboard);
      }

      boolean continuePlaying = true;

      while (continuePlaying) {
        try {
          Move playerMove = this.view.getPlayerMove();
          Move computerMove = this.computerService.makeMove();
          Result result = this.gameService.calculateResult(playerMove, computerMove);

          this.matchService.save(player, playerMove, computerMove, result);

          this.view.displayRoundResult(playerMove, computerMove, result);

          ScoreboardDTO updatedScoreboard =
              this.matchService.getScoreboard(player);
          this.view.displayScoreboard(updatedScoreboard);

          continuePlaying = this.view.playAgain();
        } catch (IllegalArgumentException e) {
          view.displayError(e.getMessage());
        }
      }

      this.view.showFarewell(this.matchService.getScoreboard(player));
    } catch (Exception e) {
      this.view.displayError("Internal game error: " + e.getMessage());
      this.view.close();
      e.printStackTrace();
    }
  }
}
