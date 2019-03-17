package com.evil.cbs.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookedMovieDTO {
    private Long movieSessionId;
    private String seatNumber;
    private String username;

}
