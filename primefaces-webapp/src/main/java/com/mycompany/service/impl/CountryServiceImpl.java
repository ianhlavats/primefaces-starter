/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013 Ian Hlavats.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * 
 */
package com.mycompany.service.impl;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.servlet.ServletContext;

import com.mycompany.lifecycle.Initialized;
import com.mycompany.model.City;
import com.mycompany.model.Country;
import com.mycompany.model.ProvinceState;
import com.mycompany.service.CountryService;
import com.mycompany.util.Queries;

/**
 * 
 * Country service implementation class.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
@Named("countryService")
@Stateless
public class CountryServiceImpl extends AbstractService implements CountryService {

	/**
	 * {@inheritDoc}
	 */
	public ProvinceState createProvinceState(Country country, String name) {
		ProvinceState state = null;
		try {
			state = new ProvinceState();
			state.setName(name);
			state.setCountry(country);
			country.getProvinceStates().add(state);
			em.persist(state);
		} catch (Exception e) {
			logger.error("Unable to create province/state object:", e);
		}
		return state;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<City> findCitiesByState(ProvinceState state) {
		String jql = "select c from City c where c.provinceState = ?1 order by c.name";
		TypedQuery<City> query = em.createQuery(jql, City.class);
		query.setParameter(1, state);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	public City findCity(String name, ProvinceState provinceState) {
		City city = null;
		try {
			TypedQuery<City> query = em.createNamedQuery(
					Queries.CITY_FIND_BY_NAME_AND_PROVINCE_STATE, City.class);
			query.setParameter(1, name);
			query.setParameter(2, provinceState);
			city = query.getSingleResult();
		} catch (NoResultException e) {
		}
		return city;
	}

	/**
	 * {@inheritDoc}
	 */
	public City findCityById(Integer id) {
		return em.find(City.class, id);
	}

	private Country findCountryByCode(String countryCode) {
		return (Country) em.createNamedQuery(Queries.COUNTRY_FIND_BY_CODE)
							.setParameter(1, countryCode)
							.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	public Country findCountryById(Integer id) {
		return em.find(Country.class, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ProvinceState> findProvinceStatesByCountry(Country country) {
		String jql = "select p from ProvinceState p where p.country = ?1 order by p.name";
		TypedQuery<ProvinceState> query = em.createQuery(jql, ProvinceState.class);
		query.setParameter(1, country);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	public ProvinceState findStateById(Integer id) {
		return em.find(ProvinceState.class, id);
	}

	/**
	 * {@inheritDoc}
	 */
	public ProvinceState findStateByName(Country country, String stateName) {
		if (country == null) {
			return null;
		}
		for (ProvinceState state : country.getProvinceStates()) {
			if (state.getName().equals(stateName)) {
				return state;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Country> getCountries() {
		return em.createNamedQuery(Queries.COUNTRY_FIND_ALL).getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ProvinceState> getProvinceStates() {
		String jql = "select p from ProvinceState p where p.country.code = 'US' order by p.name";
		TypedQuery<ProvinceState> query = em.createQuery(jql, ProvinceState.class);
		return query.getResultList();
	}

	private void importCities() {
		InputStream in = getClass().getResourceAsStream("/cities.txt");
		Scanner scanner = new Scanner(in);
		Pattern pattern = Pattern.compile("([^:]+):([^:]+):([^:]+):(.*)");
		Matcher matcher = null;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			matcher = pattern.matcher(line);
			if (matcher.find()) {
				String countryCode = matcher.group(1);
				String stateName = matcher.group(2);
				String cityName = matcher.group(3);
				String coords = matcher.group(4);
				String[] array = coords.split(",");
				Double latitude = Double.parseDouble(array[0]);
				Double longitude = Double.parseDouble(array[1]);
				Country country = findCountryByCode(countryCode);
				ProvinceState state = findStateByName(country, stateName);
				if (state == null) {
					state = new ProvinceState();
					state.setName(stateName);
					state.setCountry(country);
					country.getProvinceStates().add(state);
					em.persist(state);
				}
				City city = new City();
				city.setName(cityName);
				city.setProvinceState(state);
				city.setLatitude(latitude);
				city.setLongitude(longitude);
				state.getCities().add(city);
				em.persist(city);
			}
		}
	}

	private void importCountries() {
		InputStream in = getClass().getResourceAsStream("/countries.txt");
		Scanner scanner = new Scanner(in);
		Pattern pattern = Pattern.compile("([A-Z]+):(.*)");
		Matcher matcher = null;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			matcher = pattern.matcher(line);
			if (matcher.find()) {
				String isoCode = matcher.group(1);
				String countryName = matcher.group(2);
				Country country = new Country();
				country.setCode(isoCode);
				country.setName(countryName);
				em.persist(country);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(@Observes @Initialized ServletContext context) throws Exception {
		List<Country> countries = getCountries();
		if (countries == null || countries.isEmpty()) {
			logger.info("Importing country and city data...");
			importCountries();
			importCities();
		}
	}

}
