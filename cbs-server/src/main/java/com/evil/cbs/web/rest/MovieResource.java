package com.evil.cbs.web.rest;

import com.evil.cbs.domain.Movie;
import com.evil.cbs.service.MovieService;
import com.evil.cbs.web.dto.MovieDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/movies")
@Slf4j
public class MovieResource {

    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<Movie> addMovie(@Valid @RequestBody MovieDTO movieDTO){
        Movie movie;
        try {
            movie = Movie.MovieBuilder.aMovie()
                    .name(movieDTO.getName())
                    .description(movieDTO.getDescription())
                    .imdbRating(movieDTO.getImdbRating())
                    .movieDuration(movieDTO.getMovieDuration())
                    .actors(movieDTO.getActors())
                    .directors(movieDTO.getDirectors())
                    .build();
            movieService.saveMovie(movie);
        } catch (Exception e) {
            log.error("Movie not saved!", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(movie);
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies(){
        return ResponseEntity.status(HttpStatus.OK).body(movieService.findAll());
    }

    @GetMapping("/{movieId}/")
    public ResponseEntity<Movie> getMovieById(@PathVariable("movieId") Long movieId){
        return ResponseEntity.status(HttpStatus.OK).body(movieService.findById(movieId));
    }

    @DeleteMapping("/{movieId}/")
    public ResponseEntity<Movie> deleteMovieById(@PathVariable("movieId") Long movieId){
        movieService.deleteById(movieId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
