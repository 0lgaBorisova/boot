package com.luxoft.springdb.lab1.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luxoft.springdb.lab1.dao.CountryNotFoundException;
import com.luxoft.springdb.lab1.dao.CountryRepository;
import com.luxoft.springdb.lab1.model.Country;
import com.luxoft.springdb.lab1.model.CountryDto;

import static com.luxoft.springdb.lab1.model.CountryDto.toDto;

@RestController
public class CountryController {

	private final CountryRepository repository;

	@Autowired
	public CountryController(CountryRepository repository) {
		this.repository = repository;
	}

	@GetMapping(value = "/countries")
	public List<CountryDto> get() {
		return repository
				.findAll()
				.stream()
				.map(CountryDto::toDto)
				.collect(Collectors.toList());
	}

	@PostMapping("/country/create")
	public ResponseEntity<CountryDto> create(@RequestBody CountryDto countryDto) {
		Country country = CountryDto.toDomainObject(countryDto);
		Country accountWithId = repository.save(country);
		return ResponseEntity.ok(toDto(accountWithId));
	}

	@PostMapping("/country/update")
	public void updateByCodeName(@RequestParam String codeName, @RequestParam String name) {
		repository.update(name, codeName);
	}

	@DeleteMapping("/country/delete/{id}")
	public void delete(@PathVariable("id") int id) {
		repository.deleteById(id);
	}


	@GetMapping(value = "/country/{id}")
	public CountryDto get(@PathVariable("id") int id) throws CountryNotFoundException {
		return toDto(repository
				             .findById(id)
				             .orElseThrow(CountryNotFoundException::new));
	}

	@ExceptionHandler(CountryNotFoundException.class)
	public ResponseEntity<String> handleNotEnoughFunds(CountryNotFoundException ex) {
		return ResponseEntity.badRequest().body("Not found");
	}
}
