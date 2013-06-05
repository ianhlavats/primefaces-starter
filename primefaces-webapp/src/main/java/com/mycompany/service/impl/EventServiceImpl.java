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

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.mycompany.model.City;
import com.mycompany.model.City_;
import com.mycompany.model.Event;
import com.mycompany.model.EventAttendance;
import com.mycompany.model.EventType;
import com.mycompany.model.Event_;
import com.mycompany.model.ProvinceState;
import com.mycompany.model.User;
import com.mycompany.model.Venue;
import com.mycompany.model.Venue_;
import com.mycompany.service.EventService;
import com.mycompany.util.Queries;

/**
 * Event service implementation class.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
@Named("eventService")
@Stateless
public class EventServiceImpl extends AbstractService implements EventService {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Event> findAllEvents() {
		return em.createNamedQuery(Queries.EVENT_FIND_ALL, Event.class).getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<EventAttendance> findAllEventsAttendanceByUser(User user) {
		TypedQuery<EventAttendance> query = em.createNamedQuery(
				Queries.EVENT_ATTENDANCE_FIND_BY_USER, EventAttendance.class);
		query.setParameter(1, user);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EventAttendance findEventAttendance(Event event, User user) {
		TypedQuery<EventAttendance> query = em.createNamedQuery(
				Queries.EVENT_ATTENDANCE_FIND_BY_USER_AND_EVENT, EventAttendance.class);
		query.setParameter(1, user);
		query.setParameter(2, event);
		List<EventAttendance> resultList = query.getResultList();
		if (!resultList.isEmpty()) {
			return resultList.get(0);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Event findEventById(Integer id) {
		return em.find(Event.class, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Event> findUserEvents(Date start, Date end, User user) {
		String jql = "select evt from Event evt where evt.createdBy = ?1 and evt.startDate between ?2 and ?3";
		TypedQuery<Event> query = em.createQuery(jql, Event.class);
		query.setParameter(1, user);
		query.setParameter(2, start);
		query.setParameter(3, end);
		List<Event> events = query.getResultList();
		return events;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EventAttendance saveAttendance(EventAttendance attendance) {
		EventAttendance saved = attendance;
		if (attendance.getVersion() == null) {
			em.persist(attendance);
		} else {
			saved = em.merge(attendance);
		}
		return saved;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Event saveEvent(Event event) {
		Event saved = event;
		if (event.getVersion() == null) {
			em.persist(event);
		} else {
			saved = em.merge(event);
		}
		return saved;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Event> findEvents(City city, ProvinceState provinceState, EventType eventType,
			String keyword) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Event> criteria = cb.createQuery(Event.class);
		Root<Event> event = criteria.from(Event.class);
		criteria.select(event);
		Predicate andClause = cb.conjunction();
		if (city != null) {
			Join<Event, Venue> joinedVenue = event.join(Event_.venue);
			Join<Venue, City> joinedCity = joinedVenue.join(Venue_.city);
			Predicate cityClause = cb.equal(joinedCity.get(City_.name), city.getName());
			andClause = cb.and(andClause, cityClause);
		}
		if (provinceState != null) {
			Join<Event, Venue> joinedVenue = event.join(Event_.venue);
			Predicate provinceStateClause = cb.equal(joinedVenue.get(Venue_.provinceState),
					provinceState);
			andClause = cb.and(andClause, provinceStateClause);
		}
		if (eventType != null) {
			Predicate eventTypeClause = cb.equal(event.get(Event_.eventType), eventType);
			andClause = cb.and(andClause, eventTypeClause);
		}
		if (keyword != null) {
			Predicate keywordClause = cb.like(event.get(Event_.title), "%" + keyword + "%");
			andClause = cb.and(andClause, keywordClause);
		}
		criteria.where(andClause);
		TypedQuery<Event> query = em.createQuery(criteria);
		return query.getResultList();
	}

}
