package com.evil.cbs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;


@Data
@EqualsAndHashCode(exclude = "hall", callSuper = true)
@Entity
@Table(name = "seats", schema = "cbs")
@ToString(exclude = "hall")
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Seat extends AbstractEntity{
    @Column(name = "price")
    private int price;
    @Column(name = "seat_number")
    private String seatNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "seat_status")
    private SeatStatus seatStatus = SeatStatus.FREE;
    @ManyToOne
    @JsonIgnore
    private Hall hall;
    @Version
    private long version;

    public boolean isBooked(){
        return seatStatus.equals(SeatStatus.BOOKED);
    }


}
