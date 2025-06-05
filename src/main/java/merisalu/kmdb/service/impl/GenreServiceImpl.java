package merisalu.kmdb.service.impl;

import org.springframework.stereotype.Service;
import merisalu.kmdb.dto.GenreDTO;
import merisalu.kmdb.dto.MovieDTO;
import merisalu.kmdb.exception.GlobalExceptionHandler;
import merisalu.kmdb.model.Genre;
import merisalu.kmdb.model.Movie;
import merisalu.kmdb.repository.GenreRepository;
import merisalu.kmdb.service.GenreService;
import merisalu.kmdb.mapper.GenreMapper;  // <-- import custom mapper
import merisalu.kmdb.mapper.MovieMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;  // <-- inject mapper
    private final MovieMapper movieMapper;

    public GenreServiceImpl(GenreRepository genreRepository, GenreMapper genreMapper, MovieMapper movieMapper) {
        this.genreRepository = genreRepository;
        this.genreMapper = genreMapper;
        this.movieMapper = movieMapper;
    }

    private GenreDTO toDtoWithMovieIds(Genre genre) {
        GenreDTO dto = genreMapper.toDTO(genre);
        Set<Long> movieIds = genre.getMovies().stream()
                .map(Movie::getId)
                .collect(Collectors.toSet());
        dto.setMovieIds(movieIds);
        return dto;
    }

    @Override
    public List<GenreDTO> getAllGenres() {
        return genreRepository.findAll().stream()
                .map(this::toDtoWithMovieIds)
                .collect(Collectors.toList());
    }

    @Override
    public GenreDTO getGenreById(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException(
                        "Genre not found with id: " + id));
        return toDtoWithMovieIds(genre);
    }

    @Override
    public GenreDTO createGenre(GenreDTO genreDTO) {
        boolean exists = genreRepository.findAll().stream()
                .anyMatch(existingGenre -> existingGenre.getName().equalsIgnoreCase(genreDTO.getName()));
        if (exists) {
            throw new GlobalExceptionHandler.DuplicateResourceException(
                    "Genre with name '" + genreDTO.getName() + "' already exists.");
        }

        Genre genre = genreMapper.toEntity(genreDTO);
        Genre savedGenre = genreRepository.save(genre);
        return toDtoWithMovieIds(savedGenre);
    }

    @Override
    public GenreDTO updateGenre(Long id, GenreDTO genreDTO) {
        Genre existingGenre = genreRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException(
                        "Genre not found with id: " + id));

        genreMapper.updateEntity(genreDTO, existingGenre);
        Genre updatedGenre = genreRepository.save(existingGenre);
        return toDtoWithMovieIds(updatedGenre);
    }

    @Override
    public void deleteGenre(Long id, boolean force) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException(
                        "Genre not found with id: " + id));

        if (!force && !genre.getMovies().isEmpty()) {
            throw new GlobalExceptionHandler.OperationNotAllowedException(
                    "Cannot delete genre with " + genre.getMovies().size() + " associated movies. Use force=true.");
        }

        if (force) {
            // Remove the genre from each movie's genre list
            for (Movie movie : genre.getMovies()) {
                movie.getGenres().remove(genre);
            }
            genre.getMovies().clear();
            genreRepository.save(genre);
        }

        genreRepository.delete(genre);
    }


    @Override
    public List<MovieDTO> getMoviesByGenreId(Long genreId) {
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException(
                        "Genre not found with id: " + genreId));

        return genre.getMovies().stream()
                .map(movieMapper::toDTO)
                .collect(Collectors.toList());
    }
}
