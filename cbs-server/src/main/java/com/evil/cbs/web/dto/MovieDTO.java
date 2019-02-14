package com.evil.cbs.web.dto;

import com.evil.cbs.domain.MovieSession;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class MovieDTO {
    private String name;
    private String description;
    private String imdbRating;
    private Duration movieDuration;
    private MovieSession movieSession;
    private Set<String> directors = new HashSet<>();
    private Set<String> actors = new HashSet<>();
    private String imagePath;
}
