package merisalu.kmdb.mapper;

import merisalu.kmdb.dto.GenreDTO;
import merisalu.kmdb.model.Genre;
import merisalu.kmdb.model.Movie;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    public Genre toEntity(GenreDTO dto) {
        if (dto == null) return null;
        Genre entity = new Genre();
        entity.setName(dto.getName());
        return entity;
    }

    public GenreDTO toDTO(Genre entity) {
        if (entity == null) return null;
        GenreDTO dto = new GenreDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());

        if (entity.getMovies() != null) {
            Set<Long> movieIds = entity.getMovies().stream()
                .map(Movie::getId)
                .collect(Collectors.toSet());
            dto.setMovieIds(movieIds);
        }
        return dto;
    }

    public void updateEntity(GenreDTO dto, Genre entity) {
        if (dto == null || entity == null) return;
        entity.setName(dto.getName());
        entity.getMovies().clear();
    }
}
