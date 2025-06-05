package merisalu.kmdb.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UtilityCleanupService {

    private final EntityManager entityManager;

    public UtilityCleanupService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void cleanOrphanedJoinTableEntries() {
        int deletedFromMovieGenre = entityManager.createNativeQuery("""
            DELETE FROM movie_genre
            WHERE movie_id NOT IN (SELECT id FROM movies)
            OR genre_id NOT IN (SELECT id FROM genres)
        """).executeUpdate();

        int deletedFromMovieActor = entityManager.createNativeQuery("""
            DELETE FROM movie_actor
            WHERE movie_id NOT IN (SELECT id FROM movies)
            OR actor_id NOT IN (SELECT id FROM actors)
        """).executeUpdate();

        System.out.printf("🧹 Cleanup complete: deleted %d orphaned entries from movie_genre and %d from movie_actor.%n",
                          deletedFromMovieGenre, deletedFromMovieActor);
    }
}
