package merisalu.kmdb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

@Data
public class ActorDTO {
    private Long id;

    @NotBlank(message = "Actor name is required")
    @Size(max = 200, message = "Name must be less than 200 characters")
    private String name;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    private Set<Long> movieIds;
}