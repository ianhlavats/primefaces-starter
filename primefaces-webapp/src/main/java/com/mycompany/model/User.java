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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.mycompany.util.Queries;

/**
 * JPA entity for user data.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
@Entity
@Table(	name = "USERS",
		uniqueConstraints = { @UniqueConstraint(columnNames = "username"),
				@UniqueConstraint(columnNames = "emailaddress") })
@NamedQueries({
		@NamedQuery(name = Queries.USER_FIND_BY_USERNAME_PASSWORD,
					query = "select u from User u where u.username=?1 and u.password=?2"),
		@NamedQuery(name = Queries.USER_FIND_BY_RELATIONSHIP_TYPE,
					query = "select r.toUser from UserRelationship r where r.relationshipType = ?1 and r.fromUser = ?2 order by r.toUser.lastName, r.toUser.firstName"),
		@NamedQuery(name = Queries.USER_FIND_BY_PARTIAL_NAME,
					query = "select u from User u where u.firstName like :name or u.lastName like :name order by u.firstName, u.lastName") })
public class User extends AbstractEntity {

	private static final String[] excludedFields = new String[] { "fromRelationships",
			"toRelationships", "eventsAttended" };

	/**
	 * 
	 */
	private static final long serialVersionUID = 8009394076089033693L;

	private boolean acceptedTerms;

	private Date birthdate;

	private Integer clickCount;

	private Country country;

	private String emailAddress;

	private Set<EventAttendance> eventsAttended = new HashSet<EventAttendance>();

	private String firstName;

	private Set<UserRelationship> fromRelationships = new HashSet<UserRelationship>();

	private Gender gender;

	private String lastName;

	private String password;

	private String phoneNumber;

	private String theme;

	private Set<UserRelationship> toRelationships = new HashSet<UserRelationship>();

	private String username;

	@Override
	public boolean equals(Object object) {
		return EqualsBuilder.reflectionEquals(this, object, excludedFields);
	}

	@Temporal(TemporalType.DATE)
	public Date getBirthdate() {
		return birthdate;
	}

	public Integer getClickCount() {
		return clickCount;
	}

	@ManyToOne
	public Country getCountry() {
		return country;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	@OneToMany(mappedBy = "user")
	public Set<EventAttendance> getEventsAttended() {
		return eventsAttended;
	}

	public String getFirstName() {
		return firstName;
	}

	@OneToMany(mappedBy = "fromUser")
	public Set<UserRelationship> getFromRelationships() {
		return fromRelationships;
	}

	@Enumerated(EnumType.STRING)
	public Gender getGender() {
		return gender;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPassword() {
		return password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getTheme() {
		return theme;
	}

	@OneToMany(mappedBy = "toUser")
	public Set<UserRelationship> getToRelationships() {
		return toRelationships;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, excludedFields);
	}

	@Column(columnDefinition = "smallint not null default 0")
	public boolean isAcceptedTerms() {
		return acceptedTerms;
	}

	public void setAcceptedTerms(boolean acceptedTerms) {
		this.acceptedTerms = acceptedTerms;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public void setClickCount(Integer clicks) {
		this.clickCount = clicks;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setEventsAttended(Set<EventAttendance> eventsAttended) {
		this.eventsAttended = eventsAttended;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setFromRelationships(Set<UserRelationship> fromRelationships) {
		this.fromRelationships = fromRelationships;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public void setToRelationships(Set<UserRelationship> toRelationships) {
		this.toRelationships = toRelationships;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
