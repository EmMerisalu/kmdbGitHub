package merisalu.kmdb.controller;

import merisalu.kmdb.dto.ActorDTO;
import merisalu.kmdb.dto.MovieDTO;
import merisalu.kmdb.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping(params = {"!actor", "!genre", "!year"})
    public Page<MovieDTO> getAllMovies(Pageable pageable) {
        int maxPageSize = 150;
        if (pageable.getPageSize() > maxPageSize) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Page size must not be greater than " + maxPageSize
            );
        }
        return movieService.getAllMovies(pageable);
    }




    @GetMapping("/{id}")
    public MovieDTO getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @GetMapping(params = "genre")
    public List<MovieDTO> getMoviesByGenre(@RequestParam Long genre) {
        return movieService.getMoviesByGenre(genre);
    }

    @GetMapping(params = "year")
    public List<MovieDTO> getMoviesByYear(@RequestParam Integer year) {
        return movieService.getMoviesByYear(year);
    }

    @GetMapping(params = "actor")
    public List<MovieDTO> getMoviesByActor(@RequestParam Long actor) {
        return movieService.getMoviesByActor(actor);
    }

    @GetMapping("/{movieId}/actors")
    public List<ActorDTO> getActorsByMovie(@PathVariable Long movieId) {
        return movieService.getActorsByMovie(movieId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDTO createMovie(@Valid @RequestBody MovieDTO movieDTO) {
        return movieService.createMovie(movieDTO);
    }

    @PatchMapping("/{id}")
    public MovieDTO updateMovie(@PathVariable Long id, @Valid @RequestBody MovieDTO movieDTO) {
        return movieService.updateMovie(id, movieDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable Long id, 
                          @RequestParam(required = false, defaultValue = "false") boolean force) {
        movieService.deleteMovie(id, force);
    }

    @GetMapping("/search")
    public List<MovieDTO> searchMovies(@RequestParam String title) {
        return movieService.searchByTitle(title);
    }
}