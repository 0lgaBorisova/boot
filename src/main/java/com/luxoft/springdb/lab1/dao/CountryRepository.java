package com.luxoft.springdb.lab1.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.luxoft.springdb.lab1.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

	List<Country> findAll();

	Optional<Country> findByCodeName(String codeName);

	List<Country> findAllByNameStartsWith(String name);

	@Modifying
	@Transactional
	@Query("update Country SET name=?1 where codeName=?2")
	void update(String name, String codeName);
}
