package com.evil.cbs.ticket.validator.service.impl;

import com.evil.cbs.domain.Order;
import com.evil.cbs.domain.Ticket;
import com.evil.cbs.domain.TicketStatus;
import com.evil.cbs.repository.OrderRepository;
import com.evil.cbs.repository.TicketRepository;
import com.evil.cbs.service.BlowfishCryptoService;
import com.evil.cbs.ticket.validator.domain.AttendStatus;
import com.evil.cbs.ticket.validator.domain.TripAttendAttempt;
import com.evil.cbs.ticket.validator.domain.TripAttendRequest;
import com.evil.cbs.ticket.validator.domain.ValidatorVerdict;
import com.evil.cbs.ticket.validator.repository.TripAttendAttemptRepository;
import com.evil.cbs.ticket.validator.service.ValidatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class ValidatorServiceImpl implements ValidatorService {
    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;
    private final TripAttendAttemptRepository tripAttendAttemptRepository;
    private final BlowfishCryptoService cryptoService;

    @Transactional
    @Override
    public ValidatorVerdict isAttenderAllowedIn(TripAttendRequest request) {
        String decryptedOrderId = "";
        try {
             decryptedOrderId = cryptoService.decrypt(request.getEncryptedOrderId());
        } catch (Exception e) {
            return new ValidatorVerdict(false, request.getEncryptedOrderId(), e.getMessage());
        }
        log.info("Attending trip with order id: " + decryptedOrderId);
        if (!decryptedOrderIdIsNumeric(decryptedOrderId))
            return new ValidatorVerdict(false, request.getEncryptedOrderId(), "EMPTY");
        return getValidatorVerdict(Long.valueOf(decryptedOrderId), request.getEncryptedOrderId());
    }

    private ValidatorVerdict getValidatorVerdict(long orderId, String encryptedOrderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            Ticket ticket = order.get().getTicket();
            boolean isAllowedIn = ticket.getTicketStatus() == TicketStatus.NOT_USED;
            if (isAllowedIn)
                markTicketAsUsed(ticket);
            saveTripAttendAttempt(ticket, isAllowedIn);
            return new ValidatorVerdict(isAllowedIn, encryptedOrderId, "EMPTY");
        }
        return new ValidatorVerdict(false, encryptedOrderId, "NOT_PRESENT");
    }

    private void markTicketAsUsed(Ticket ticket) {
        ticket.setTicketStatus(TicketStatus.USED);
        ticketRepository.save(ticket);
    }

    private void saveTripAttendAttempt(Ticket ticket, boolean isAllowedIn) {
        tripAttendAttemptRepository.save(TripAttendAttempt.builder()
                .ticket(ticket)
                .attendDate(LocalDate.now())
                .attemptStatus(isAllowedIn ? AttendStatus.SUCCESSFUL : AttendStatus.NOT_SUCCESSFUL)
                .build());
    }

    private boolean decryptedOrderIdIsNumeric(String decryptedOrderId) {
        Pattern p = Pattern.compile("\\d+");
        return p.matcher(decryptedOrderId)
                .matches();
    }
}
