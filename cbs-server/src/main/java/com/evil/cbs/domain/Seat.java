package com.evil.cbs.domain;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "seats", schema = "cbs")
public class Seat extends AbstractEntity{
    @Column(name = "price")
    private int price;
    @Column(name = "seat_number")
    private String seatNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "seat_status")
    private SeatStatus seatStatus = SeatStatus.FREE;
    @ManyToOne
    private Hall hall;
    @OneToOne(mappedBy = "bookedSeat")
    private Ticket ticket;


    public static final class SeatBuilder {
        private int price;
        private String seatNumber;
        private SeatStatus seatStatus = SeatStatus.FREE;
        private Hall hall;
        private Ticket ticket;

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

        public SeatBuilder ticket(Ticket ticket) {
            this.ticket = ticket;
            return this;
        }

        public Seat build() {
            Seat seat = new Seat();
            seat.setPrice(price);
            seat.setSeatNumber(seatNumber);
            seat.setSeatStatus(seatStatus);
            seat.setHall(hall);
            seat.setTicket(ticket);
            return seat;
        }
    }
}