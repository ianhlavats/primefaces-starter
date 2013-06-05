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

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.mycompany.util.Queries;

/**
 * JPA entity for user event attendance data.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "event", "user" }))
@NamedQueries({
		@NamedQuery(name = Queries.EVENT_ATTENDANCE_FIND_BY_USER_AND_EVENT,
					query = "select e from EventAttendance e where e.user = ?1 and e.event = ?2"),
		@NamedQuery(name = Queries.EVENT_ATTENDANCE_FIND_BY_USER,
					query = "select e from EventAttendance e where e.user = ?1 order by e.event.startDate desc"),
		@NamedQuery(name = Queries.EVENT_ATTENDANCE_FIND_GENDER_COUNT_BY_EVENT,
					query = "select e.event.startDate, count(e.user.gender) from EventAttendance e where e.user.gender = ?1 and e.confirmed is true group by e.event order by e.event.startDate ") })
public class EventAttendance extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4351135973935030713L;

	private boolean confirmed;

	private Event event;

	private int rating;

	private User user;

	@ManyToOne
	public Event getEvent() {
		return event;
	}

	public int getRating() {
		return rating;
	}

	@ManyToOne
	public User getUser() {
		return user;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean attended) {
		this.confirmed = attended;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
