package com.luxoft.springdb.lab1;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.luxoft.springdb.lab1.dao.CountryRepository;
import com.luxoft.springdb.lab1.model.Country;

@SpringBootApplication
public class Main {

	@Autowired
	private CountryRepository countryRepository;

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@PostConstruct
	public void init() {
		System.out.println(countryRepository.findAll());
	}
}
