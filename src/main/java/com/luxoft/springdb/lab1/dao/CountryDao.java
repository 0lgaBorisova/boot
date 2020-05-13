package com.luxoft.springdb.lab1.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.luxoft.springdb.lab1.model.Country;

public class CountryDao extends JdbcDaoSupport {
	private static final String NEW_COUNTRY_NAME = "newCountryName";
	private static final String CODE_NAME = "codeName";
	private static final String GET_ALL_COUNTRIES_SQL = "select * from country";
	private static final String GET_COUNTRIES_BY_NAME_SQL = "select * from country where name like :name";
	private static final String GET_COUNTRY_BY_CODE_NAME_SQL = "select * from country where code_name =:" + CODE_NAME;

	private static final String UPDATE_COUNTRY_NAME = "update country SET name=:" + NEW_COUNTRY_NAME + " where code_name=:" + CODE_NAME;


	private static final CountryRowMapper COUNTRY_ROW_MAPPER = new CountryRowMapper();
	private static final String NAME = "name";


	public List<Country> getCountryList() {
		return getJdbcTemplate()
				.query(GET_ALL_COUNTRIES_SQL, COUNTRY_ROW_MAPPER);
	}

	public List<Country> getCountryListStartWith(String name) {
		SqlParameterSource param = new MapSqlParameterSource(NAME, name + "%");
		return getNamedParameterJdbcTemplate()
				.query(GET_COUNTRIES_BY_NAME_SQL, param, COUNTRY_ROW_MAPPER);
	}

	private NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return new NamedParameterJdbcTemplate(getDataSource());
	}

	public void updateCountryName(String codeName, String newCountryName) {
		MapSqlParameterSource params = new MapSqlParameterSource(CODE_NAME, codeName);
		params.addValue(NEW_COUNTRY_NAME, newCountryName);
		getNamedParameterJdbcTemplate()
				.execute(UPDATE_COUNTRY_NAME, params, PreparedStatement::execute);
	}

	public Country getCountryByCodeName(String codeName) {
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource(CODE_NAME, codeName);
		return getNamedParameterJdbcTemplate()
				.query(GET_COUNTRY_BY_CODE_NAME_SQL,
				       sqlParameterSource, COUNTRY_ROW_MAPPER).get(0);
	}
}
