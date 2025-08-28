package br.com.madda.rock_paper_scissors;

import br.com.madda.rock_paper_scissors.controller.GameController;
import br.com.madda.rock_paper_scissors.mapper.MatchMapper;
import br.com.madda.rock_paper_scissors.mapper.PlayerMapper;
import br.com.madda.rock_paper_scissors.repository.MatchRepository;
import br.com.madda.rock_paper_scissors.repository.PlayerRepository;
import br.com.madda.rock_paper_scissors.service.ComputerService;
import br.com.madda.rock_paper_scissors.service.GameService;
import br.com.madda.rock_paper_scissors.service.MatchService;
import br.com.madda.rock_paper_scissors.service.PlayerService;
import br.com.madda.rock_paper_scissors.view.ConsoleView;

public class App {
  public static void main(String[] args) {
    try {
      PlayerRepository playerRepository = new PlayerRepository();
      MatchRepository matchRepository = new MatchRepository();

      PlayerMapper playerMapper = new PlayerMapper();
      MatchMapper matchMapper = new MatchMapper();

      ConsoleView view = new ConsoleView();
      ComputerService computerService = new ComputerService();
      GameService gameService = new GameService();
      PlayerService playerService = new PlayerService(playerRepository, playerMapper);
      MatchService matchService = new MatchService(matchRepository, matchMapper);

      GameController controller =
          new GameController(view, computerService, gameService, playerService, matchService);

      controller.startGame();

      view.close();
    } catch (Exception e) {
      System.err.println("Error starting game: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
