package merisalu.kmdb.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "actors")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  

    private String name;

    private LocalDate birthDate;

    @ManyToMany(mappedBy = "actors")
    private Set<Movie> movies = new HashSet<>();

    public Actor() {}

    public Actor(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public LocalDate getBirthDate() { return birthDate; }
    public Set<Movie> getMovies() { return movies; }

    public void setName(String name) { this.name = name; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public void setMovies(Set<Movie> movies) { this.movies = movies; }
}
