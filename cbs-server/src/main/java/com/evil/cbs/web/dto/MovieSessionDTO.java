package com.evil.cbs.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MovieSessionDTO {
    private Long hallId;
    private Long movieId;
    private LocalDateTime showTime;
}
