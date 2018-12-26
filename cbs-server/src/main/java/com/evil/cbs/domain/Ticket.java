package com.evil.cbs.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "tickets", schema = "cbs")
public class Ticket extends AbstractEntity{
    @Column(name = "ticket_status")
    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus = TicketStatus.NOT_USED;
    @Column(name = "ticket_type")
    @Enumerated(EnumType.STRING)
    private TicketType ticketType = TicketType.SIMPLE;
    @OneToOne
    private MovieSession movieSession;
    @OneToOne
    private Seat bookedSeat;


    public static final class TicketBuilder {
        private TicketStatus ticketStatus = TicketStatus.NOT_USED;
        private TicketType ticketType = TicketType.SIMPLE;
        private MovieSession movieSession;
        private Seat bookedSeat;

        private TicketBuilder() {
        }

        public static TicketBuilder aTicket() {
            return new TicketBuilder();
        }

        public TicketBuilder ticketStatus(TicketStatus ticketStatus) {
            this.ticketStatus = ticketStatus;
            return this;
        }

        public TicketBuilder ticketType(TicketType ticketType) {
            this.ticketType = ticketType;
            return this;
        }

        public TicketBuilder movieSession(MovieSession movieSession) {
            this.movieSession = movieSession;
            return this;
        }

        public TicketBuilder bookedSeat(Seat bookedSeat) {
            this.bookedSeat = bookedSeat;
            return this;
        }

        public Ticket build() {
            Ticket ticket = new Ticket();
            ticket.setTicketStatus(ticketStatus);
            ticket.setTicketType(ticketType);
            ticket.setMovieSession(movieSession);
            ticket.setBookedSeat(bookedSeat);
            return ticket;
        }
    }
}
