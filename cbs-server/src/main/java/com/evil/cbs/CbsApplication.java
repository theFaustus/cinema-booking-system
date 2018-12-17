package com.evil.cbs;

import com.evil.cbs.config.CbsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages={"com.evil.cbs"})
@EnableTransactionManagement
@Import({ CbsConfiguration.class })
public class CbsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CbsApplication.class, args);
	}
}
