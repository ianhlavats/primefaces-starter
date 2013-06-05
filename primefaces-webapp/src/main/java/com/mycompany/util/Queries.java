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
package com.mycompany.util;

import com.mycompany.model.City;
import com.mycompany.model.Content;
import com.mycompany.model.Country;
import com.mycompany.model.Event;
import com.mycompany.model.EventAttendance;
import com.mycompany.model.Gender;
import com.mycompany.model.ProvinceState;
import com.mycompany.model.RelationshipType;
import com.mycompany.model.User;

/**
 * Constant interface for named JPA queries.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
public interface Queries {

	/**
	 * Finds a {@link City} by name and {@link ProvinceState}.
	 */
	public static final String CITY_FIND_BY_NAME_AND_PROVINCE_STATE = "City.findByNameAndProvinceState";

	/**
	 * Finds a {@link Content} by name.
	 */
	public static final String CONTENT_FIND_BY_NAME = "Content.findByName";

	/**
	 * Finds all {@link Country} objects.
	 */
	public static final String COUNTRY_FIND_ALL = "Country.findAll";

	/**
	 * Finds a {@link Country} by code.
	 */
	public static final String COUNTRY_FIND_BY_CODE = "Country.findByCode";

	/**
	 * Finds all {@link EventAttendance} for a particular {@link User}.
	 */
	public static final String EVENT_ATTENDANCE_FIND_BY_USER = "EventAttendance.findByUser";

	/**
	 * Finds a List of {@link EventAttendance} for a particular {@link User} and
	 * {@link Event}.
	 */
	public static final String EVENT_ATTENDANCE_FIND_BY_USER_AND_EVENT = "EventAttendance.findByUserAndEvent";

	/**
	 * Find a count of all {@link EventAttendance} objects by {@link Gender}.
	 */
	public static final String EVENT_ATTENDANCE_FIND_GENDER_COUNT_BY_EVENT = "EventAttendance.findGenderCountByEvent";

	/**
	 * Find all {@link Event} objects.
	 */
	public static final String EVENT_FIND_ALL = "Event.findAll";

	/**
	 * Finds a {@link User} by partial name.
	 */
	public static final String USER_FIND_BY_PARTIAL_NAME = "User.findByPartialName";

	/**
	 * Finds a List of {@link User} objects by {@link RelationshipType} to
	 * another {@link User}.
	 */
	public static final String USER_FIND_BY_RELATIONSHIP_TYPE = "User.findByRelationshipType";

	/**
	 * Finds a {@link User} by username and password.
	 */
	public static final String USER_FIND_BY_USERNAME_PASSWORD = "User.findByUsernamePassword";

}
