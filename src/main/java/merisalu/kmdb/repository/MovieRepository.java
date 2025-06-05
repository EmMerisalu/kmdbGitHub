package merisalu.kmdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import merisalu.kmdb.model.Movie;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByTitleContainingIgnoreCase(String title);
    List<Movie> findByGenresId(Long genreId);
    Page<Movie> findAll(Pageable pageable);
    List<Movie> findByReleaseYear(Integer year);
    List<Movie> findByActorsId(Long actorId);
}