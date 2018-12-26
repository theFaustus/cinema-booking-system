package com.evil.cbs.service.impl;

import com.evil.cbs.domain.Ticket;
import com.evil.cbs.repository.TicketRepository;
import com.evil.cbs.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Override
    public List<Ticket> findByUserId(Long userId) {
        return ticketRepository.findByUserId(userId);
    }

    @Override
    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }
}
