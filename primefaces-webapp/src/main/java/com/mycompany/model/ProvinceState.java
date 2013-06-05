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
package com.mycompany.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * JPA entity for province/state data.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
@Entity
public class ProvinceState extends AbstractEntity {

	private static final String[] excludeFields = new String[] { "cities" };

	/**
	 * 
	 */
	private static final long serialVersionUID = 5984616633801173028L;

	private Set<City> cities = new HashSet<City>();

	private Country country;

	private String name;

	@Override
	public boolean equals(Object object) {
		return EqualsBuilder.reflectionEquals(this, object, excludeFields);
	}

	@OneToMany(mappedBy = "provinceState")
	public Set<City> getCities() {
		return cities;
	}

	@ManyToOne
	public Country getCountry() {
		return country;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, excludeFields);
	}

	public void setCities(Set<City> cities) {
		this.cities = cities;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public void setName(String name) {
		this.name = name;
	}
}
