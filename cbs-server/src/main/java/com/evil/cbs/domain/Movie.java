package com.evil.cbs.domain;

import lombok.*;

import javax.persistence.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "movies", schema = "cbs")
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
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
    @Column(name = "image_path", nullable = true)
    private String imagePath;

}
