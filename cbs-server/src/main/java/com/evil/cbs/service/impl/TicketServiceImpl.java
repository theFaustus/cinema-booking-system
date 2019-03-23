package com.evil.cbs.service.impl;

import com.evil.cbs.domain.Order;
import com.evil.cbs.domain.Ticket;
import com.evil.cbs.repository.TicketRepository;
import com.evil.cbs.service.BlowfishCryptoService;
import com.evil.cbs.service.PdfService;
import com.evil.cbs.service.QrService;
import com.evil.cbs.service.TicketService;
import com.evil.cbs.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TemplateEngine templateEngine;
    private final PdfService pdfService;
    private final QrService qrService;
    private final FileUtil fileUtil;
    private final BlowfishCryptoService cryptoService;

    @Override
    public List<Ticket> findByUserId(Long userId) {
        return ticketRepository.findByUserId(userId);
    }

    @Override
    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public File createTicket(Order order, String ticketTemplate) {
        String encryptedOrderId = cryptoService.encrypt(order.getId().toString());
        File qrImage = qrService.generateQrCodeImage(encryptedOrderId);
        Context context = makeContext(order, encryptedOrderId, qrImage);
        File pdfFile = fileUtil.createTempFile(".pdf");
        String html = templateEngine.process(ticketTemplate, context);
        pdfService.createPdf(pdfFile.getAbsolutePath(), html);
        deleteQrCodeImage(qrImage);
        return pdfFile;
    }

    private Context makeContext(Order order, String encryptedOrderId, File qrImage) {
        Context context = new Context();
        context.setVariable("order", order);
        context.setVariable("qrImagePath", qrImage.getName());
        context.setVariable("orderId", encryptedOrderId);
        return context;
    }

    private void deleteQrCodeImage(File qrImage) {
        try {
            Files.delete(qrImage.toPath());
        } catch (IOException e) {
            log.error("Error while trying to delete the qr-code image.", e);
        }
    }
}
