package br.com.madda.rock_paper_scissors;

import java.sql.Connection;
import br.com.madda.rock_paper_scissors.config.DatabaseConfig;
import br.com.madda.rock_paper_scissors.config.DatabaseConfigFactory;
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
      DatabaseConfig dbConfig = DatabaseConfigFactory.createAuto();

      if (!dbConfig.testConnection()) {
        System.err.println("❌ Failed to connect to database");
        return;
      }

      System.out.println("✅ Connected to: " + dbConfig.getDatabaseType());

      Connection connection = dbConfig.getConnection();

      PlayerRepository playerRepository = new PlayerRepository(connection);
      MatchRepository matchRepository = new MatchRepository(connection);

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

      connection.close();
      view.close();
    } catch (Exception e) {
      System.err.println("Error starting game: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
