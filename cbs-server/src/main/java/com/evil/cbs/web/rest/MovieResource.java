package com.evil.cbs.web.rest;

import com.evil.cbs.domain.Movie;
import com.evil.cbs.service.MovieService;
import com.evil.cbs.web.form.AddMovieFormBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/api/movie")
public class MovieResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieResource.class);

    @Autowired
    private MovieService movieService;


    @RequestMapping(value = "/add-movie", method = RequestMethod.POST)
    public ResponseEntity addMovie(@Valid @RequestBody AddMovieFormBean addMovieFormBean){
        Movie movie;
        try {
            movie = Movie.MovieBuilder.aMovie()
                    .name(addMovieFormBean.getName())
                    .description(addMovieFormBean.getDescription())
                    .imdbRating(addMovieFormBean.getImdbRating())
                    .movieDuration(addMovieFormBean.getMovieDuration())
                    .actors(addMovieFormBean.getActors())
                    .directors(addMovieFormBean.getDirectors())
                    .build();
            movieService.saveMovie(movie);
        } catch (Exception e) {
            LOGGER.error("Movie not saved!", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(movie);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Movie> getAllMovies(){
        return movieService.findAll();
    }



}
