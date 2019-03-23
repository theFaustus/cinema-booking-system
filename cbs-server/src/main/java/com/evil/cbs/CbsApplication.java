package com.evil.cbs;

import com.evil.cbs.config.CbsConfiguration;
import com.evil.cbs.config.CbsSecurityConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.ServletContext;

@SpringBootApplication(scanBasePackages = {"com.evil.cbs"})
@EnableTransactionManagement
@Import({CbsConfiguration.class, CbsSecurityConfiguration.class})
public class CbsApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CbsApplication.class, args);
    }

}