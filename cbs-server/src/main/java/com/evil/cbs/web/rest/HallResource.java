package com.evil.cbs.web.rest;

import com.evil.cbs.domain.Hall;
import com.evil.cbs.domain.Movie;
import com.evil.cbs.domain.Seat;
import com.evil.cbs.service.HallService;
import com.evil.cbs.service.SeatService;
import com.evil.cbs.web.dto.HallDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/v1/api/halls")
@RequiredArgsConstructor
@Slf4j
public class HallResource {

    private final HallService hallService;
    private final SeatService seatService;


    @PostMapping
    public ResponseEntity<?> addHall(@Valid @RequestBody HallDTO hallDTO, @RequestParam(name = "numberOfSeats", defaultValue = "40") Integer numberOfSeats) {
        log.info("" + numberOfSeats);
        Hall hall;
        hall = Hall.builder()
                .name(hallDTO.getName())
                .imagePath(hallDTO.getImagePath())
                .description(hallDTO.getDescription())
                .seats(new HashSet<>())
                .build();
        log.info("" + hall);
        return ResponseEntity.status(HttpStatus.CREATED).body(hallService.saveHall(hall, numberOfSeats));
    }

    @GetMapping
    public ResponseEntity<List<HallDTO>> getAllHalls() {
        List<HallDTO> halls = hallService.findAll().stream().map(HallDTO::from).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(halls);
    }

    @GetMapping("/{hallId}/")
    public ResponseEntity<Hall> getHallById(@PathVariable("hallId") Long hallId) {
        return ResponseEntity.status(HttpStatus.OK).body(hallService.findById(hallId));
    }

    @GetMapping("/{hallId}/seats")
    public ResponseEntity<List<Seat>> getSeatsByHallId(@PathVariable("hallId") Long hallId) {
        return ResponseEntity.status(HttpStatus.OK).body(seatService.findAllByHallId(hallId));
    }


    @DeleteMapping("/{hallId}/")
    public ResponseEntity<Movie> deleteHallById(@PathVariable("hallId") Long hallId) {
        hallService.deleteById(hallId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
