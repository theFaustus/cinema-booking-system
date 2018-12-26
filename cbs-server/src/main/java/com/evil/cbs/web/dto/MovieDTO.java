package com.evil.cbs.web.dto;

import com.evil.cbs.domain.MovieSession;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.OneToOne;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
}
