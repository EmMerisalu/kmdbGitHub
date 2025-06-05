package merisalu.kmdb.mapper;

import merisalu.kmdb.dto.ActorDTO;
import merisalu.kmdb.model.Actor;
import merisalu.kmdb.model.Movie;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
@Component
public class ActorMapper {

    public Actor toEntity(ActorDTO dto) {
        if (dto == null) return null;
        Actor entity = new Actor();
        entity.setName(dto.getName());
        entity.setBirthDate(dto.getBirthDate());
        return entity;
    }

    public ActorDTO toDTO(Actor entity) {
        if (entity == null) return null;
        ActorDTO dto = new ActorDTO();
        dto.setId(entity.getId());  // Reading ID to set in DTO is fine
        dto.setName(entity.getName());
        dto.setBirthDate(entity.getBirthDate());

        if (entity.getMovies() != null) {
            Set<Long> movieIds = entity.getMovies().stream()
                .map(Movie::getId)
                .collect(Collectors.toSet());
            dto.setMovieIds(movieIds);
        }
        return dto;
    }

    public void updateEntity(ActorDTO dto, Actor entity) {
        if (dto == null || entity == null) return;
        entity.setName(dto.getName());
        entity.setBirthDate(dto.getBirthDate());
        entity.getMovies().clear();
    }
}
