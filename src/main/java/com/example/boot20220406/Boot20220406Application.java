package com.example.boot20220406;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication

@PropertySource("classpath:global.properties")
// 컨트롤러, 환경설정파일
@ComponentScan(basePackages = {
		"com.example.controller",
		"com.example.config",
		"com.example.restcontroller",
		"com.example.service"
})
@MapperScan(basePackages = {
		"com.example.mapper"
})
public class Boot20220406Application {

	public static void main(String[] args) {
		SpringApplication.run(Boot20220406Application.class, args);
		System.out.println("서버구동완료");
	}

}
