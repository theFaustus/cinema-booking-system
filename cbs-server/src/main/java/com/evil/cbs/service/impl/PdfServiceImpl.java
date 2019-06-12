package com.evil.cbs.service.impl;

import com.evil.cbs.service.PdfService;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class PdfServiceImpl implements PdfService {

  private final ServletContext servletContext;

  @Override
  public void createPdf(String destinationPdf, String html) {
    Set<String> resourcePaths = servletContext.getResourcePaths("/");
    log.info("resourcePaths - " + resourcePaths);

    try (OutputStream os = new FileOutputStream(destinationPdf)) {
      PdfRendererBuilder builder = new PdfRendererBuilder();
      String baseUri = servletContext.getResource("/images/").toString();
      log.info("baseUri - " + baseUri);
      log.info("content - " + servletContext.getResource("/images/").getContent());
      builder.withHtmlContent(html, baseUri);
      builder.toStream(os);
      builder.run();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
