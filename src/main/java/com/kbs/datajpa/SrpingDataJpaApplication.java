package com.kbs.datajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing  //JPA Auditing 사용
public class SrpingDataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SrpingDataJpaApplication.class, args);
	}

}
