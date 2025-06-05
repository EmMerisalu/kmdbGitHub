package merisalu.kmdb.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.Set;

@Data
public class MovieDTO {
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must be less than 200 characters")
    private String title;

    @Min(value = 1888, message = "Release year must be after 1888")
    @Max(value = 2100, message = "Release year must be before 2100")
    private Integer releaseYear;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    private Integer duration;

    private Set<Long> genreIds;
    private Set<Long> actorIds;
}