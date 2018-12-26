package com.evil.cbs;

import com.evil.cbs.config.CbsConfiguration;
import com.evil.cbs.domain.*;
import com.evil.cbs.repository.HallRepository;
import com.evil.cbs.repository.MovieRepository;
import com.evil.cbs.repository.MovieSessionRepository;
import com.evil.cbs.repository.SeatRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

@SpringBootApplication(scanBasePackages={"com.evil.cbs"})
@EnableTransactionManagement
@Import({ CbsConfiguration.class })
public class CbsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CbsApplication.class, args);
	}
}
