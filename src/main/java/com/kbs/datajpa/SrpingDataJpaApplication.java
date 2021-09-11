package com.kbs.datajpa;

import java.util.Optional;
import java.util.UUID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing  //JPA Auditing 사용
public class SrpingDataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SrpingDataJpaApplication.class, args);
	}

	
	//등록자, 수정자를 처리해주는 AuditorAware 스프링 빈 등록
	@Bean
	public AuditorAware<String> auditorProvider() {
	  return () -> Optional.of(UUID.randomUUID().toString());
	}
}
