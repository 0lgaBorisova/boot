package com.luxoft.springdb.lab1;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.luxoft.springdb.lab1.dao.CountryRepository;
import com.luxoft.springdb.lab1.model.Country;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class JdbcTest {
	private static final String[][] COUNTRY_INIT_DATA = {{"Australia", "AU"},
			{"Canada", "CA"}, {"France", "FR"}, {"Hong Kong", "HK"},
			{"Iceland", "IC"}, {"Japan", "JP"}, {"Nepal", "NP"},
			{"Russian Federation", "RU"}, {"Sweden", "SE"},
			{"Switzerland", "CH"}, {"United Kingdom", "GB"},
			{"United States", "US"}};

	@Autowired
	private CountryRepository countryRepository;


	private List<Country> expectedCountryList = new ArrayList<>();
	private List<Country> expectedCountryListStartsWithA = new ArrayList<>();
	private Country countryWithChangedName = new Country(1, "Russia", "RU");

	@Before
	public void setUp() throws Exception {
		initExpectedCountryLists();
	}

	@Test
	public void testCountryList() {
		List<Country> countryList = countryRepository.findAll();
		assertNotNull(countryList);
		assertEquals(expectedCountryList.size(), countryList.size());
		for (int i = 0; i < expectedCountryList.size(); i++) {
			assertEquals(expectedCountryList.get(i), countryList.get(i));
		}
	}

	@Test
	public void testCountryListStartsWithA() {
		List<Country> countryList = countryRepository.findAllByNameStartsWith("A");
		assertNotNull(countryList);
		assertEquals(expectedCountryListStartsWithA.size(), countryList.size());
		for (int i = 0; i < expectedCountryListStartsWithA.size(); i++) {
			assertEquals(expectedCountryListStartsWithA.get(i), countryList.get(i));
		}
	}

	@Test
	public void testCountryChange() throws Exception {
		countryRepository.update("Russia", "RU");
		assertEquals(countryWithChangedName, countryRepository.findByCodeName("RU").get());
	}

	private void initExpectedCountryLists() {
		for (int i = 0; i < COUNTRY_INIT_DATA.length; i++) {
			String[] countryInitData = COUNTRY_INIT_DATA[i];
			Country country = new Country(i, countryInitData[0], countryInitData[1]);
			expectedCountryList.add(country);
			if (country.getName().startsWith("A")) {
				expectedCountryListStartsWithA.add(country);
			}
		}
	}
}