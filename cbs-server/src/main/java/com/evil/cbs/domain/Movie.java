package com.evil.cbs.domain;

import lombok.*;

import javax.persistence.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
    @ElementCollection
    private Set<String> directors = new HashSet<>();
    @ElementCollection
    private Set<String> actors = new HashSet<>();

    public static final class MovieBuilder {
        private String name;
        private String description;
        private String imdbRating;
        private Duration movieDuration;
        private Set<String> directors = new HashSet<>();
        private Set<String> actors = new HashSet<>();

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

        public MovieBuilder directors(Set<String> directors) {
            this.directors = directors;
            return this;
        }

        public MovieBuilder actors(Set<String> actors) {
            this.actors = actors;
            return this;
        }

        public Movie build() {
            Movie movie = new Movie();
            movie.setName(name);
            movie.setDescription(description);
            movie.setImdbRating(imdbRating);
            movie.setMovieDuration(movieDuration);
            movie.setDirectors(directors);
            movie.setActors(actors);
            return movie;
        }
    }
}
