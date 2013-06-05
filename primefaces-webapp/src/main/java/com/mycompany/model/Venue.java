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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * JPA entity for venue data.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
@Entity
public class Venue extends AbstractEntity {

	private static final String[] excludedFields = new String[] { "events" };

	/**
	 * 
	 */
	private static final long serialVersionUID = 5049478213946349124L;

	private City city;

	private Country country;

	private Set<Event> events = new HashSet<Event>();

	private Double latitude;

	private Double longitude;

	private String name;

	private String phoneNumber;

	private ProvinceState provinceState;

	private String streetAddress;

	@Override
	public boolean equals(Object object) {
		return EqualsBuilder.reflectionEquals(this, object, excludedFields);
	}

	@ManyToOne(cascade = CascadeType.PERSIST)
	public City getCity() {
		return city;
	}

	/**
	 * This method returns the complete address of the venue.
	 * 
	 * @return A string.
	 */
	@Transient
	public String getCompleteAddress() {
		StringBuilder sb = new StringBuilder();
		sb.append(streetAddress);
		sb.append(", ");
		sb.append(city.getName());
		sb.append(", ");
		sb.append(provinceState.getName());
		sb.append(", ");
		sb.append(country.getName());
		return sb.toString();
	}

	@ManyToOne
	public Country getCountry() {
		return country;
	}

	@OneToMany(	cascade = CascadeType.REMOVE,
				mappedBy = "venue")
	public Set<Event> getEvents() {
		return events;
	}

	@Transient
	public Double getLatitude() {
		return latitude;
	}

	@Transient
	public Double getLongitude() {
		return longitude;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	@ManyToOne
	public ProvinceState getProvinceState() {
		return provinceState;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, excludedFields);
	}

	public void setCity(City city) {
		this.city = city;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setProvinceState(ProvinceState state) {
		this.provinceState = state;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

}
