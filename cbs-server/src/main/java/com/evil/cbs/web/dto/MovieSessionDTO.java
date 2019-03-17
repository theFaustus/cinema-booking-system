package com.evil.cbs.web.dto;

import com.evil.cbs.domain.MovieSession;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieSessionDTO {
    private Long movieSessionId;
    private Long hallId;
    private Long movieId;
    private String hallName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm")
    private LocalDateTime showTime;

    public static MovieSessionDTO from(MovieSession movieSession){
        return new MovieSessionDTO(movieSession.getId(),
                movieSession.getHall().getId(),
                movieSession.getMovie().getId(),
                movieSession.getHall().getName(),
                movieSession.getShowTime());
    }
}
