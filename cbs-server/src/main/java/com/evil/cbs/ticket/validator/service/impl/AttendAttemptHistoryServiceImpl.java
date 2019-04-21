package com.evil.cbs.ticket.validator.service.impl;

import com.evil.cbs.domain.Order;
import com.evil.cbs.repository.OrderRepository;
import com.evil.cbs.service.BlowfishCryptoService;
import com.evil.cbs.ticket.validator.domain.TripAttendAttempt;
import com.evil.cbs.ticket.validator.domain.TripAttendAttemptHistory;
import com.evil.cbs.ticket.validator.repository.TripAttendAttemptRepository;
import com.evil.cbs.ticket.validator.service.AttendAttemptHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AttendAttemptHistoryServiceImpl implements AttendAttemptHistoryService {
    private final OrderRepository orderRepository;
    private final TripAttendAttemptRepository tripAttendAttemptRepository;
    private final BlowfishCryptoService cryptoService;

    @Override
    public TripAttendAttemptHistory getAttendAttemptHistory(String encryptedOrderId) {
        String decryptedOrderId = cryptoService.decrypt(encryptedOrderId);
        if (!decryptedOrderIdIsNumeric(decryptedOrderId))
            return new TripAttendAttemptHistory();
        long orderId = Long.valueOf(decryptedOrderId);
        Optional<Order> order = orderRepository.findById(orderId);
        if (!order.isPresent())
            return new TripAttendAttemptHistory();
        Long ticketId = order.get().getTicket().getId();
        List<TripAttendAttempt> attempts = tripAttendAttemptRepository.findAttendAttemptsForTicket(ticketId);
        return new TripAttendAttemptHistory(attempts);
    }

    private boolean decryptedOrderIdIsNumeric(String decryptedOrderId) {
        Pattern p = Pattern.compile("\\d+");
        return p.matcher(decryptedOrderId)
                .matches();
    }
}
