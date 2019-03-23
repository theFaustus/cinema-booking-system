package com.evil.cbs.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Data
public class Email {
  private String host;
  private String port;
  private String fromEmail;
  private String password;
  private String toEmail;
  private String subject;
  private String message;
  private List<String> attachedFiles = new ArrayList<>();

  public void setMailProperties(Properties props) {
    fromEmail = props.getProperty("email");
    password = props.getProperty("password");
    host = props.getProperty("host");
    port = props.getProperty("port");
  }
}
