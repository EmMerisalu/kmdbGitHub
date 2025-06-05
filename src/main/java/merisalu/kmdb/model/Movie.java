package merisalu.kmdb.model;


import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "movies")
public class Movie {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String title;

        private Integer releaseYear;

        private Integer duration;

    @ManyToMany
    @JoinTable(
        name = "movie_genre",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "movie_actor",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private Set<Actor> actors = new HashSet<>();

    public Movie() {}

    public Movie(String title, Integer releaseYear, Integer duration) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.duration = duration;
    }

 
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public Integer getReleaseYear() { return releaseYear; }
    public Integer getDuration() { return duration; }
    public Set<Genre> getGenres() { return genres; }
    public Set<Actor> getActors() { return actors; }

    public void setTitle(String title) { this.title = title; }
    public void setReleaseYear(Integer releaseYear) { this.releaseYear = releaseYear; }
    public void setDuration(Integer duration) { this.duration = duration; }
    public void setGenres(Set<Genre> genres) { this.genres = genres; }
    public void setActors(Set<Actor> actors) { this.actors = actors; }

    public void addGenre(Genre genre) {
        genres.add(genre);
        genre.getMovies().add(this);
    }

    public void removeGenre(Genre genre) {
        genres.remove(genre);
        genre.getMovies().remove(this);
    }

    public void addActor(Actor actor) {
        actors.add(actor);
        actor.getMovies().add(this);
    }

    public void removeActor(Actor actor) {
        actors.remove(actor);
        actor.getMovies().remove(this);
    }
}