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
package com.mycompany.service;

import java.util.List;

import javax.enterprise.event.Observes;
import javax.servlet.ServletContext;

import com.mycompany.lifecycle.Initialized;
import com.mycompany.model.City;
import com.mycompany.model.Country;
import com.mycompany.model.ProvinceState;

/**
 * Service class for country-related operations.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
public interface CountryService {

	/**
	 * Creates a {@link ProvinceState} object for a particular {@link Country}.
	 * 
	 * @param country
	 *            The Country in which the ProvinceState is located.
	 * @param value
	 *            The name of the ProvinceState.
	 * @return The newly created ProvinceState object.
	 */
	ProvinceState createProvinceState(Country country, String value);

	/**
	 * Finds a list of {@link City} objects for the given {@link ProvinceState}.
	 * 
	 * @param provinceState
	 *            The ProvinceState object for which to find cities.
	 * @return A List of City objects.
	 */
	List<City> findCitiesByState(ProvinceState provinceState);

	/**
	 * Finds a {@link City} by name in the {@link ProvinceState}.
	 * 
	 * @param name
	 *            The name of the city.
	 * @param provinceState
	 *            The province/state in which to find the city.
	 * @return A City object.
	 */
	City findCity(String name, ProvinceState provinceState);

	/**
	 * Finds a {@link City} object by ID.
	 * 
	 * @param id
	 *            The ID of the city.
	 * @return A City object.
	 */
	City findCityById(Integer id);

	/**
	 * Finds a {@link Country} object by ID.
	 * 
	 * @param id
	 *            The ID of the country.
	 * @return A Country object.
	 */
	Country findCountryById(Integer id);

	/**
	 * Finds a {@link List} of {@link ProvinceState} objects for a particular
	 * {@link Country}.
	 * 
	 * @param country
	 *            The Country object.
	 * @return A List of ProvinceState objects.
	 */
	List<ProvinceState> findProvinceStatesByCountry(Country country);

	/**
	 * Finds a {@link ProvinceState} object by ID.
	 * 
	 * @param id
	 *            The ID of the ProvinceState object.
	 * @return A ProvinceState object.
	 */
	ProvinceState findStateById(Integer id);

	/**
	 * Finds a {@link ProvinceState} object by name within a particular
	 * {@link Country}.
	 * 
	 * @param country
	 *            The Country object.
	 * @param name
	 *            The name of the ProvinceState to find.
	 * @return A ProvinceState object.
	 */
	ProvinceState findStateByName(Country country, String name);

	/**
	 * Returns all {@link Country} objects.
	 * 
	 * @return A List of Country objects.
	 */
	List<Country> getCountries();

	/**
	 * Returns all {@link ProvinceState} objects.
	 * 
	 * @return A List of ProvinceState objects.
	 */
	List<ProvinceState> getProvinceStates();

	/**
	 * Invoked during the {@link ServletContext} initialization to populate the
	 * database with sample data.
	 * 
	 * @param context
	 *            The {@link ServletContext} object.
	 * @throws Exception
	 *             If something goes wrong.
	 */
	void init(@Observes @Initialized ServletContext context) throws Exception;

}
