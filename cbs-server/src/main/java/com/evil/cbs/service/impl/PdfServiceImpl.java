package com.evil.cbs.service.impl;

import com.evil.cbs.service.PdfService;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Service
@RequiredArgsConstructor
public class PdfServiceImpl implements PdfService {

  private final ServletContext servletContext;

  @Override
  public void createPdf(String destinationPdf, String html) {
    servletContext.getResourcePaths("/");

    try (OutputStream os = new FileOutputStream(destinationPdf)) {
      PdfRendererBuilder builder = new PdfRendererBuilder();
      builder.withHtmlContent(html, getClass().getResource("/images").toString());
      builder.toStream(os);
      builder.run();
      os.close();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
