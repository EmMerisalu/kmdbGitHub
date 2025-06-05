package merisalu.kmdb.controller;

import merisalu.kmdb.dto.GenreDTO;
import merisalu.kmdb.dto.MovieDTO;
import merisalu.kmdb.service.GenreService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public List<GenreDTO> getAllGenres() {
        return genreService.getAllGenres();
    }

    @GetMapping("/{id}")
    public GenreDTO getGenreById(@PathVariable Long id) {
        return genreService.getGenreById(id);
    }

    @GetMapping("/{id}/movies")
    public List<MovieDTO> getMoviesByGenre(@PathVariable Long id) {
        return genreService.getMoviesByGenreId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenreDTO createGenre(@Valid @RequestBody GenreDTO genreDTO) {
        return genreService.createGenre(genreDTO);
    }

    @PatchMapping("/{id}")
    public GenreDTO updateGenre(@PathVariable Long id, @Valid @RequestBody GenreDTO genreDTO) {
        return genreService.updateGenre(id, genreDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGenre(@PathVariable Long id, 
                          @RequestParam(required = false, defaultValue = "false") boolean force) {
        genreService.deleteGenre(id, force);
    }
}