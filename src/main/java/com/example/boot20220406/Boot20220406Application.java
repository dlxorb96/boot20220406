package com.example.boot20220406;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication

// filter적용시키기
@ServletComponentScan(basePackages = {
		"com.example.filter"
})
@PropertySource("classpath:global.properties")
// 컨트롤러, 환경설정파일
@ComponentScan(basePackages = {
		"com.example.controller",
		"com.example.config",
		"com.example.restcontroller",
		"com.example.service",
		"com.example.jwt",
		"com.example.schedule"
})

// 매퍼
@MapperScan(basePackages = {
		"com.example.mapper"
})

// 인테티(jpa) == DTO(mybatis)
@EntityScan(basePackages = { "com.example.entity" })

// 저장소(jap) ==매퍼(mybatis)
@EnableJpaRepositories(basePackages = { "com.example.repository" })

public class Boot20220406Application {

	public static void main(String[] args) {
		SpringApplication.run(Boot20220406Application.class, args);
		System.out.println("서버구동완료");
	}

}
