package com.luxoft.springdb.lab1;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.luxoft.springdb.lab1.dao.CountryDao;
import com.luxoft.springdb.lab1.model.Country;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase
public class JdbcTest {
	private static final String[][] COUNTRY_INIT_DATA = {{"Australia", "AU"},
			{"Canada", "CA"}, {"France", "FR"}, {"Hong Kong", "HK"},
			{"Iceland", "IC"}, {"Japan", "JP"}, {"Nepal", "NP"},
			{"Russian Federation", "RU"}, {"Sweden", "SE"},
			{"Switzerland", "CH"}, {"United Kingdom", "GB"},
			{"United States", "US"}};

	private CountryDao countryDao;

	private List<Country> expectedCountryList = new ArrayList<>();
	private List<com.luxoft.springdb.lab1.model.Country> expectedCountryListStartsWithA = new ArrayList<>();
	private Country countryWithChangedName = new Country(1, "Russia", "RU");

	@Before
	public void setUp() throws Exception {
		DataSource dataSource = new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript("classpath:db-schema.sql")
				.addScript("classpath:db-init.sql")
				.build();
		countryDao = new CountryDao();
		countryDao.setDataSource(dataSource);
		initExpectedCountryLists();
	}

	@Test
	public void testCountryList() {
		List<Country> countryList = countryDao.getCountryList();
		assertNotNull(countryList);
		assertEquals(expectedCountryList.size(), countryList.size());
		for (int i = 0; i < expectedCountryList.size(); i++) {
			assertEquals(expectedCountryList.get(i), countryList.get(i));
		}
	}

	@Test
	public void testCountryListStartsWithA() {
		List<Country> countryList = countryDao.getCountryListStartWith("A");
		assertNotNull(countryList);
		assertEquals(expectedCountryListStartsWithA.size(), countryList.size());
		for (int i = 0; i < expectedCountryListStartsWithA.size(); i++) {
			assertEquals(expectedCountryListStartsWithA.get(i), countryList.get(i));
		}
	}

	@Test
	public void testCountryChange() {
		countryDao.updateCountryName("RU", "Russia");
		assertEquals(countryWithChangedName, countryDao.getCountryByCodeName("RU"));
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