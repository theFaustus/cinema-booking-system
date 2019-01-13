package com.evil.cbs.web.dto;

import com.evil.cbs.domain.Hall;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HallDTO {
    private Long id;
    private String name;

    public static HallDTO from(Hall h){
        return new HallDTO(h.getId(), h.getName());
    }
}
