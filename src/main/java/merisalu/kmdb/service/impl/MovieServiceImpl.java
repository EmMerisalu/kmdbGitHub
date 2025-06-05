package merisalu.kmdb.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import merisalu.kmdb.dto.ActorDTO;
import merisalu.kmdb.dto.MovieDTO;
import merisalu.kmdb.exception.GlobalExceptionHandler;
import merisalu.kmdb.mapper.ActorMapper;
import merisalu.kmdb.mapper.GenreMapper;
import merisalu.kmdb.mapper.MovieMapper;
import merisalu.kmdb.model.Actor;
import merisalu.kmdb.model.Genre;
import merisalu.kmdb.model.Movie;
import merisalu.kmdb.repository.ActorRepository;
import merisalu.kmdb.repository.GenreRepository;
import merisalu.kmdb.repository.MovieRepository;
import merisalu.kmdb.service.MovieService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final ActorRepository actorRepository;

    private final MovieMapper movieMapper;
    private final ActorMapper actorMapper;

    public MovieServiceImpl(MovieRepository movieRepository,
                            GenreRepository genreRepository,
                            ActorRepository actorRepository,
                            MovieMapper movieMapper,
                            GenreMapper genreMapper,
                            ActorMapper actorMapper) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.actorRepository = actorRepository;
        this.movieMapper = movieMapper;
        this.actorMapper = actorMapper;
    }

    @Override
    public MovieDTO createMovie(MovieDTO movieDTO) {
        // Check if a movie with the same title already exists
        boolean exists = movieRepository.findByTitleContainingIgnoreCase(movieDTO.getTitle()).stream()
                .anyMatch(existingMovie -> existingMovie.getTitle().equalsIgnoreCase(movieDTO.getTitle()));
        if (exists) {
            throw new GlobalExceptionHandler.DuplicateResourceException(
                    "Movie with title '" + movieDTO.getTitle() + "' already exists.");
        }

        Movie movie = movieMapper.toEntity(movieDTO);

        // Handle relationships
        Set<Genre> genres = new HashSet<>();
        for (Long genreId : movieDTO.getGenreIds()) {
            genres.add(genreRepository.findById(genreId)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException(
                    "Genre not found with id: " + genreId))
            );
        }

        Set<Actor> actors = new HashSet<>();
        for (Long actorId : movieDTO.getActorIds()) {
            actors.add(actorRepository.findById(actorId)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException(
                    "Actor not found with id: " + actorId))
            );
        }

        movie.setGenres(genres);
        movie.setActors(actors);

        Movie savedMovie = movieRepository.save(movie);

        // Map back to DTO and manually set genreIds and actorIds
        MovieDTO responseDTO = movieMapper.toDTO(savedMovie);
        responseDTO.setGenreIds(
            savedMovie.getGenres().stream()
                .map(Genre::getId)
                .collect(Collectors.toSet())
        );
        responseDTO.setActorIds(
            savedMovie.getActors().stream()
                .map(Actor::getId)
                .collect(Collectors.toSet())
        );

        return responseDTO;
    }
    @Override
    public List<ActorDTO> getActorsByMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
            .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException(
                "Movie not found with id: " + movieId
            ));

        return movie.getActors().stream()
            .map(actorMapper::toDTO)
            .collect(Collectors.toList());
    }


    @Override
    public Page<MovieDTO> getAllMovies(Pageable pageable) {
        Page<Movie> moviePage = movieRepository.findAll(pageable);
        List<MovieDTO> movieDTOs = moviePage.getContent().stream()
                .map(movie -> {
                    MovieDTO dto = movieMapper.toDTO(movie);
                    dto.setGenreIds(
                        movie.getGenres().stream()
                            .map(Genre::getId)
                            .collect(Collectors.toSet())
                    );
                    dto.setActorIds(
                        movie.getActors().stream()
                            .map(Actor::getId)
                            .collect(Collectors.toSet())
                    );
                    return dto;
                })
                .collect(Collectors.toList());
        return new PageImpl<>(movieDTOs, pageable, moviePage.getTotalElements());
    }

    @Override
    public MovieDTO getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
            .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException(
                "Movie not found with id: " + id
            ));
        MovieDTO dto = movieMapper.toDTO(movie);
        dto.setGenreIds(
            movie.getGenres().stream()
                .map(Genre::getId)
                .collect(Collectors.toSet())
        );
        dto.setActorIds(
            movie.getActors().stream()
                .map(Actor::getId)
                .collect(Collectors.toSet())
        );
        return dto;
    }

    @Override
    public MovieDTO updateMovie(Long id, MovieDTO movieDTO) {
        Movie existingMovie = movieRepository.findById(id)
            .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException(
                "Movie not found with id: " + id
            ));

        movieMapper.updateEntity(movieDTO, existingMovie);

        // Update relationships
        Set<Genre> genres = new HashSet<>();
        for (Long genreId : movieDTO.getGenreIds()) {
            genres.add(genreRepository.findById(genreId)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException(
                    "Genre not found with id: " + genreId))
            );
        }

        Set<Actor> actors = new HashSet<>();
        for (Long actorId : movieDTO.getActorIds()) {
            actors.add(actorRepository.findById(actorId)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException(
                    "Actor not found with id: " + actorId))
            );
        }

        existingMovie.setGenres(genres);
        existingMovie.setActors(actors);

        Movie updatedMovie = movieRepository.save(existingMovie);

        // Map back to DTO and manually set genreIds and actorIds
        MovieDTO responseDTO = movieMapper.toDTO(updatedMovie);
        responseDTO.setGenreIds(
            updatedMovie.getGenres().stream()
                .map(Genre::getId)
                .collect(Collectors.toSet())
        );
        responseDTO.setActorIds(
            updatedMovie.getActors().stream()
                .map(Actor::getId)
                .collect(Collectors.toSet())
        );

        return responseDTO;
    }

    @Override
public void deleteMovie(Long id, boolean force) {
    Movie movie = movieRepository.findById(id)
            .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException(
                    "Movie not found with id: " + id));

    if (!force && (!movie.getActors().isEmpty() || !movie.getGenres().isEmpty())) {
        throw new GlobalExceptionHandler.OperationNotAllowedException(
                "Cannot delete movie with associated actors or genres. Use force=true.");
    }

    if (force) {
        for (Actor actor : movie.getActors()) {
            actor.getMovies().remove(movie);
        }
        for (Genre genre : movie.getGenres()) {
            genre.getMovies().remove(movie);
        }
        movie.getActors().clear();
        movie.getGenres().clear();
        movieRepository.save(movie);
    }

    movieRepository.delete(movie);
}

    @Override
    public List<MovieDTO> searchByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(movie -> {
                    MovieDTO dto = movieMapper.toDTO(movie);
                    dto.setGenreIds(
                        movie.getGenres().stream()
                            .map(Genre::getId)
                            .collect(Collectors.toSet())
                    );
                    dto.setActorIds(
                        movie.getActors().stream()
                            .map(Actor::getId)
                            .collect(Collectors.toSet())
                    );
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> getMoviesByGenre(Long genreId) {
        return movieRepository.findByGenresId(genreId).stream()
                .map(movie -> {
                    MovieDTO dto = movieMapper.toDTO(movie);
                    dto.setGenreIds(
                        movie.getGenres().stream()
                            .map(Genre::getId)
                            .collect(Collectors.toSet())
                    );
                    dto.setActorIds(
                        movie.getActors().stream()
                            .map(Actor::getId)
                            .collect(Collectors.toSet())
                    );
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> getMoviesByYear(Integer year) {
        return movieRepository.findByReleaseYear(year).stream()
                .map(movie -> {
                    MovieDTO dto = movieMapper.toDTO(movie);
                    dto.setGenreIds(
                        movie.getGenres().stream()
                            .map(Genre::getId)
                            .collect(Collectors.toSet())
                    );
                    dto.setActorIds(
                        movie.getActors().stream()
                            .map(Actor::getId)
                            .collect(Collectors.toSet())
                    );
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> getMoviesByActor(Long actorId) {
        return movieRepository.findByActorsId(actorId).stream()
                .map(movie -> {
                    MovieDTO dto = movieMapper.toDTO(movie);
                    dto.setGenreIds(
                        movie.getGenres().stream()
                            .map(Genre::getId)
                            .collect(Collectors.toSet())
                    );
                    dto.setActorIds(
                        movie.getActors().stream()
                            .map(Actor::getId)
                            .collect(Collectors.toSet())
                    );
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
}
