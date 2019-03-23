package com.evil.cbs.util;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class FileUtil {

  public File createTempFile() {
    return createTempFile(".tmp");
  }
  
  public File createTempFile(String suffix) {    
    try {
      String salt = String.valueOf(Math.random())
          .replace(".", "_");
      return File.createTempFile("qr_" + LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE) + "_" + salt, suffix);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
