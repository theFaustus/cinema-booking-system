package com.evil.cbs.domain;

import lombok.*;

import javax.persistence.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "movies", schema = "cbs")
public class Movie extends AbstractEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "imdb_rating")
    private String imdbRating;
    @Column(name = "movie_duration")
    private Duration movieDuration;
    @OneToOne(mappedBy = "movie", cascade = CascadeType.ALL)
    private MovieSession movieSession;
    @ElementCollection
    private List<String> directors = new ArrayList<>();
    @ElementCollection
    private List<String> actors = new ArrayList<>();


    public static final class MovieBuilder {
        private String name;
        private String description;
        private String imdbRating;
        private Duration movieDuration;
        private MovieSession movieSession;
        private List<String> directors = new ArrayList<>();
        private List<String> actors = new ArrayList<>();

        private MovieBuilder() {
        }

        public static MovieBuilder aMovie() {
            return new MovieBuilder();
        }

        public MovieBuilder name(String name) {
            this.name = name;
            return this;
        }

        public MovieBuilder description(String description) {
            this.description = description;
            return this;
        }

        public MovieBuilder imdbRating(String imdbRating) {
            this.imdbRating = imdbRating;
            return this;
        }

        public MovieBuilder movieDuration(Duration movieDuration) {
            this.movieDuration = movieDuration;
            return this;
        }

        public MovieBuilder movieSession(MovieSession movieSession) {
            this.movieSession = movieSession;
            return this;
        }

        public MovieBuilder directors(List<String> directors) {
            this.directors = directors;
            return this;
        }

        public MovieBuilder actors(List<String> actors) {
            this.actors = actors;
            return this;
        }

        public Movie build() {
            Movie movie = new Movie();
            movie.setName(name);
            movie.setDescription(description);
            movie.setImdbRating(imdbRating);
            movie.setMovieDuration(movieDuration);
            movie.setMovieSession(movieSession);
            movie.setDirectors(directors);
            movie.setActors(actors);
            return movie;
        }
    }
}
