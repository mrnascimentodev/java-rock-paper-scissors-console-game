package br.com.madda.rock_paper_scissors.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.com.madda.rock_paper_scissors.config.JPAConfig;
import br.com.madda.rock_paper_scissors.entity.Player;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class PlayerRepository {
  private static final Logger logger = LoggerFactory.getLogger(PlayerRepository.class);

  public Player save(Player player) {
    EntityManager em = JPAConfig.createEntityManager();

    try {
      em.getTransaction().begin();

      if (player.getId() == null) {
        em.persist(player);
      } else {
        player = em.merge(player);
      }

      em.getTransaction().commit();
      logger.info("Player saved: {}", player.getName());

      return player;
    } catch (Exception e) {
      em.getTransaction().rollback();
      logger.error("Error saving player: {}", e.getMessage(), e);
      throw new RuntimeException("Error saving player", e);
    } finally {
      em.close();
    }
  }


  public Optional<Player> findById(Long id) throws SQLException {
    EntityManager em = JPAConfig.createEntityManager();

    try {
      Player player = em.find(Player.class, id);
      return Optional.ofNullable(player);
    } catch (Exception e) {
      logger.error("Error searching for player by ID: {}", e.getMessage(), e);
      return Optional.empty();
    } finally {
      em.close();
    }
  }

  public Optional<Player> findByName(String name) throws SQLException {
    EntityManager em = JPAConfig.createEntityManager();
    try {
      TypedQuery<Player> query =
          em.createQuery("SELECT p FROM Player p WHERE p.name = :name", Player.class);
      query.setParameter("name", name);

      Player player = query.getSingleResult();

      return Optional.of(player);
    } catch (NoResultException e) {
      return Optional.empty();
    } catch (Exception e) {
      logger.error("Error searching for player by name: {}", e.getMessage(), e);
      return Optional.empty();
    } finally {
      em.close();
    }
  }

  public List<Player> findAll() throws SQLException {
    EntityManager em = JPAConfig.createEntityManager();
    try {
      TypedQuery<Player> query =
          em.createQuery("SELECT p FROM Player p ORDER BY p.createdAt DESC", Player.class);

          return query.getResultList();
    } catch (Exception e) {
      logger.error("Error listing players: {}", e.getMessage(), e);
      return List.of();
    } finally {
      em.close();
    }
  }
}
