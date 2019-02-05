package com.evil.cbs.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "tickets", schema = "cbs")
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
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
    @ManyToOne
    private User user;

}
