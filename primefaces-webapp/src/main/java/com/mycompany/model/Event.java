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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.mycompany.util.Queries;

/**
 * JPA entity for event data.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
@Entity
@NamedQueries(@NamedQuery(	name = Queries.EVENT_FIND_ALL,
							query = "select e from Event e order by e.startDate desc"))
public class Event extends AbstractEntity {

	private static final String[] excludedFields = new String[] { "attendance" };

	/**
	 * 
	 */
	private static final long serialVersionUID = 1853185041344918956L;

	private Set<EventAttendance> attendance = new HashSet<EventAttendance>();

	private User createdBy;

	private String description;

	private Date endDate;

	private EventType eventType;

	private Date startDate;

	private String title;

	private Venue venue;

	@Override
	public boolean equals(Object object) {
		return EqualsBuilder.reflectionEquals(this, object, excludedFields);
	}

	@OneToMany(	cascade = CascadeType.REMOVE,
				mappedBy = "event")
	public Set<EventAttendance> getAttendance() {
		return attendance;
	}

	@ManyToOne
	public User getCreatedBy() {
		return createdBy;
	}

	public String getDescription() {
		return description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getEndDate() {
		return endDate;
	}

	@Enumerated(EnumType.STRING)
	public EventType getEventType() {
		return eventType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getStartDate() {
		return startDate;
	}

	public String getTitle() {
		return title;
	}

	@ManyToOne
	public Venue getVenue() {
		return venue;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, excludedFields);
	}

	public void setAttendance(Set<EventAttendance> usersAttended) {
		this.attendance = usersAttended;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}

}
