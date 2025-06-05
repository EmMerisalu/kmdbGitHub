package merisalu.kmdb.service;

import merisalu.kmdb.dto.ActorDTO;
import merisalu.kmdb.dto.MovieDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface MovieService {
    MovieDTO createMovie(MovieDTO movieDTO);
    Page<MovieDTO> getAllMovies(Pageable pageable);
    MovieDTO getMovieById(Long id);
    MovieDTO updateMovie(Long id, MovieDTO movieDTO);
    void deleteMovie(Long id, boolean force);
    List<MovieDTO> searchByTitle(String title);
    List<MovieDTO> getMoviesByGenre(Long genreId);
    List<MovieDTO> getMoviesByYear(Integer year);
    List<MovieDTO> getMoviesByActor(Long actorId);
    List<ActorDTO> getActorsByMovie(Long movieId);
}