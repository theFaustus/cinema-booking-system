package com.evil.cbs.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookedMovieDTO {
    private Long movieSessionId;
    private String seatNumber;
    private Long userId;
}
