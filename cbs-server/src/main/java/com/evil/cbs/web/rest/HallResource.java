package com.evil.cbs.web.rest;

import com.evil.cbs.domain.Hall;
import com.evil.cbs.web.dto.HallDTO;
import com.evil.cbs.domain.Movie;
import com.evil.cbs.service.HallService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/api/halls")
@RequiredArgsConstructor
@Slf4j
public class HallResource {

    private final HallService hallService;


    @PostMapping
    public ResponseEntity<Hall> addMovie(@Valid @RequestBody HallDTO hallDTO, @RequestParam(name = "numberOfSeats", defaultValue = "40") Integer numberOfSeats){
        Hall hall;
            hall = Hall.HallBuilder.aHall()
                    .name(hallDTO.getName())
                    .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(hallService.saveHall(hall, numberOfSeats));
    }

    @GetMapping
    public ResponseEntity<List<Hall>> getAllHalls(){
        return ResponseEntity.status(HttpStatus.OK).body(hallService.findAll());
    }

    @GetMapping("/{hallId}/")
    public ResponseEntity<Hall> getHallById(@PathVariable("hallId") Long hallId){
        return ResponseEntity.status(HttpStatus.OK).body(hallService.findById(hallId));
    }

    @DeleteMapping("/{hallId}/")
    public ResponseEntity<Movie> deleteMovieById(@PathVariable("hallId") Long hallId){
        hallService.deleteById(hallId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
