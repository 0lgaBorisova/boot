package com.luxoft.springdb.lab1.model;

import java.io.Serializable;

public class CountryDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	private String codeName;

	public CountryDto(String name, String codeName) {
		this.name = name;
		this.codeName = codeName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public static CountryDto toDto(Country country) {
		return new CountryDto(country.getName(), country.getCodeName());
	}

	public static Country toDomainObject(CountryDto countryDto) {
		return new Country(countryDto.getName(),countryDto.getCodeName());
	}
}
