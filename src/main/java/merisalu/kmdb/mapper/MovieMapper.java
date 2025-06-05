package merisalu.kmdb.mapper;

import merisalu.kmdb.dto.MovieDTO;
import merisalu.kmdb.model.Actor;
import merisalu.kmdb.model.Genre;
import merisalu.kmdb.model.Movie;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
@Component
public class MovieMapper {

    public Movie toEntity(MovieDTO dto) {
        if (dto == null) return null;
        Movie entity = new Movie();
        entity.setTitle(dto.getTitle());
        entity.setReleaseYear(dto.getReleaseYear());
        entity.setDuration(dto.getDuration());
        return entity;
    }

    public MovieDTO toDTO(Movie entity) {
        if (entity == null) return null;
        MovieDTO dto = new MovieDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setReleaseYear(entity.getReleaseYear());
        dto.setDuration(entity.getDuration());

        if (entity.getGenres() != null) {
            Set<Long> genreIds = entity.getGenres().stream()
                .map(Genre::getId)
                .collect(Collectors.toSet());
            dto.setGenreIds(genreIds);
        }

        if (entity.getActors() != null) {
            Set<Long> actorIds = entity.getActors().stream()
                .map(Actor::getId)
                .collect(Collectors.toSet());
            dto.setActorIds(actorIds);
        }
        return dto;
    }

    public void updateEntity(MovieDTO dto, Movie entity) {
        if (dto == null || entity == null) return;
        entity.setTitle(dto.getTitle());
        entity.setReleaseYear(dto.getReleaseYear());
        entity.setDuration(dto.getDuration());
        entity.getGenres().clear();
        entity.getActors().clear();
        // Associations handled in service layer
    }
}
