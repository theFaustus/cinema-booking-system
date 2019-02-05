package com.evil.cbs.web.dto;

import com.evil.cbs.domain.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {
    private TicketStatus ticketStatus = TicketStatus.NOT_USED;
    private TicketType ticketType = TicketType.SIMPLE;
    private MovieSessionDTO movieSessionDTO;
    private Seat bookedSeat;
    private User user;

    public static TicketDTO from(Ticket ticket){
        return new TicketDTO(ticket.getTicketStatus(),
                ticket.getTicketType(),
                new MovieSessionDTO(
                        ticket.getMovieSession().getHall().getId(),
                        ticket.getMovieSession().getMovie().getId(),
                        ticket.getMovieSession().getHall().getName(),
                        ticket.getMovieSession().getShowTime()),
                ticket.getBookedSeat(),
                ticket.getUser());
    }
}
