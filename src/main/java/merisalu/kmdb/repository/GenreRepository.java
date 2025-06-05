package merisalu.kmdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import merisalu.kmdb.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}