package com.evil.cbs.service;

import com.evil.cbs.domain.Order;
import com.evil.cbs.domain.Ticket;

import java.io.File;
import java.util.List;

public interface TicketService {
    List<Ticket> findByUserId(Long userId);

    Ticket saveTicket(Ticket ticket);

    File createTicket(Order order, String ticketTemplate);
}
