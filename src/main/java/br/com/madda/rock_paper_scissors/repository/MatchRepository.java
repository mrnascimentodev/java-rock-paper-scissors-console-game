package br.com.madda.rock_paper_scissors.repository;

import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.com.madda.rock_paper_scissors.config.JPAConfig;
import br.com.madda.rock_paper_scissors.dto.ScoreboardDTO;
import br.com.madda.rock_paper_scissors.entity.Match;
import br.com.madda.rock_paper_scissors.entity.Player;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class MatchRepository {
  private static final Logger logger = LoggerFactory.getLogger(MatchRepository.class);

  public Match save(Match match) {
    EntityManager em = JPAConfig.createEntityManager();

    try {
      em.getTransaction().begin();

      if (match.getId() == null) {
        em.persist(match);
      } else {
        match = em.merge(match);
      }

      em.getTransaction().commit();
      logger.info("Game saved: {}", match.getId());

      return match;
    } catch (Exception e) {
      em.getTransaction().rollback();
      logger.error("Error saving game: {}", e.getMessage(), e);
      throw new RuntimeException("Error saving game", e);
    } finally {
      em.close();
    }
  }

  public List<Match> findByPlayer(Player player) throws SQLException {
    EntityManager em = JPAConfig.createEntityManager();

    try {
      TypedQuery<Match> query = em.createQuery(
          "SELECT m FROM Match m WHERE m.player = :player ORDER BY m.createdAt DESC", Match.class);
      query.setParameter("player", player);

      return query.getResultList();
    } catch (Exception e) {
      logger.error("Error fetching player matches: {}", e.getMessage(), e);
      return List.of();
    } finally {
      em.close();
    }
  }

  public ScoreboardDTO getScoreboardByPlayer(Player player) {
    EntityManager em = JPAConfig.createEntityManager();

    try {
      Object[] result = (Object[]) em.createQuery("""
              SELECT
                  SUM(CASE WHEN m.result = 'VICTORY' THEN 1 ELSE 0 END),
                  SUM(CASE WHEN m.result = 'DEFEAT' THEN 1 ELSE 0 END),
                  SUM(CASE WHEN m.result = 'TIE' THEN 1 ELSE 0 END)
              FROM Match m
              WHERE m.player = :player
          """).setParameter("player", player).getSingleResult();

      int victories = result[0] != null ? ((Number) result[0]).intValue() : 0;
      int defeats = result[1] != null ? ((Number) result[1]).intValue() : 0;
      int draws = result[2] != null ? ((Number) result[2]).intValue() : 0;

      return new ScoreboardDTO(player.getName(), victories, defeats, draws);
    } catch (Exception e) {
      logger.error("Error fetching player matches: {}", e.getMessage(), e);
      throw new RuntimeException("Error fetching matches", e);
    } finally {
      em.close();
    }
  }

}
