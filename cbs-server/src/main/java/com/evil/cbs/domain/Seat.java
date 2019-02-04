package com.evil.cbs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;


@Data
@EqualsAndHashCode(exclude = "hall", callSuper = true)
@Entity
@Table(name = "seats", schema = "cbs")
@ToString(exclude = "hall")
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

    public static final class SeatBuilder {
        private int price;
        private String seatNumber;
        private SeatStatus seatStatus = SeatStatus.FREE;
        private Hall hall;

        private SeatBuilder() {
        }

        public static SeatBuilder aSeat() {
            return new SeatBuilder();
        }

        public SeatBuilder price(int price) {
            this.price = price;
            return this;
        }

        public SeatBuilder seatNumber(String seatNumber) {
            this.seatNumber = seatNumber;
            return this;
        }

        public SeatBuilder seatStatus(SeatStatus seatStatus) {
            this.seatStatus = seatStatus;
            return this;
        }

        public SeatBuilder hall(Hall hall) {
            this.hall = hall;
            return this;
        }


        public Seat build() {
            Seat seat = new Seat();
            seat.setPrice(price);
            seat.setSeatNumber(seatNumber);
            seat.setSeatStatus(seatStatus);
            seat.setHall(hall);
            return seat;
        }
    }
}
