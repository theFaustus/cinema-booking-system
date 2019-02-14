package com.evil.cbs.web.dto;

import com.evil.cbs.domain.Hall;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HallDTO {
    private Long id;
    private String name;
    private String description;
    private String imagePath;
    private int numberOfSeats;

    public static HallDTO from(Hall h){
        return new HallDTO(h.getId(), h.getName(), h.getDescription(), h.getImagePath(), h.getSeats().size());
    }
}
