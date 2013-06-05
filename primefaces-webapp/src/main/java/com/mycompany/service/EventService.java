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

import java.util.Date;
import java.util.List;

import com.mycompany.model.City;
import com.mycompany.model.Event;
import com.mycompany.model.EventAttendance;
import com.mycompany.model.EventType;
import com.mycompany.model.ProvinceState;
import com.mycompany.model.User;

/**
 * Interfaces for an event service.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
public interface EventService {

	/**
	 * Finds all {@link Event} objects.
	 * 
	 * @return A List of Event objects.
	 */
	List<Event> findAllEvents();

	/**
	 * Finds all {@link EventAttendance} objects for a particular {@link User}.
	 * 
	 * @param user
	 *            The User object.
	 * @return A List of EventAttendance objects.
	 */
	List<EventAttendance> findAllEventsAttendanceByUser(User user);

	/**
	 * Finds the {@link EventAttendance} object for a particular {@link Event}
	 * and {@link User}.
	 * 
	 * @param event
	 *            The Event object.
	 * @param user
	 *            The User object.
	 * @return An EventAttendance object.
	 */
	EventAttendance findEventAttendance(Event event, User user);

	/**
	 * Finds an {@link Event} by ID.
	 * 
	 * @param id
	 *            The Event ID.
	 * @return An Event object.
	 */
	Event findEventById(Integer id);

	/**
	 * Finds a List of {@link Event} objects in a {@link City} and
	 * {@link ProvinceState} for a particular {@link EventType} and keyword.
	 * 
	 * @param city
	 *            The City object.
	 * @param provinceState
	 *            The ProvinceState object.
	 * @param eventType
	 *            The EventType object.
	 * @param keyword
	 *            A keyword.
	 * @return A List of Event objects.
	 */
	List<Event> findEvents(City city, ProvinceState provinceState, EventType eventType,
			String keyword);

	/**
	 * Finds a List of {@link Event} objects between a start and end date
	 * created by a particular {@link User}.
	 * 
	 * @param start
	 *            The start date.
	 * @param end
	 *            The end date.
	 * @param user
	 *            The User object.
	 * @return A List of Event objects.
	 */
	List<Event> findUserEvents(Date start, Date end, User user);

	/**
	 * Saves an {@link EventAttendance} object.
	 * 
	 * @param attendance
	 *            The EventAttendance object.
	 * @return The saved object.
	 */
	EventAttendance saveAttendance(EventAttendance attendance);

	/**
	 * Saves an {@link Event}.
	 * 
	 * @param event
	 *            The Event object.
	 * @return The saved object.
	 */
	Event saveEvent(Event event);

}
