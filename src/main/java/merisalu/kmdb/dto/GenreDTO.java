package merisalu.kmdb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.Set;

@Data
public class GenreDTO {
    private Long id;

    @NotBlank(message = "Genre name is required")
    @Size(max = 100, message = "Genre name must be less than 100 characters")
    private String name;

    private Set<Long> movieIds;
}