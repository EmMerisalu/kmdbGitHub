package merisalu.kmdb.service;
import merisalu.kmdb.dto.MovieDTO;
import merisalu.kmdb.dto.GenreDTO;
import java.util.List;

public interface GenreService {
    List<GenreDTO> getAllGenres();
    GenreDTO getGenreById(Long id);
    GenreDTO createGenre(GenreDTO genreDTO);
    GenreDTO updateGenre(Long id, GenreDTO genreDTO);
    void deleteGenre(Long id, boolean force);
    List<MovieDTO> getMoviesByGenreId(Long genreId);
}