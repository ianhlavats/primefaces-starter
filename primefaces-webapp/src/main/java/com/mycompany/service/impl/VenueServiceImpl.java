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

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.TypedQuery;

import com.mycompany.model.Venue;
import com.mycompany.service.VenueService;

/**
 * Venue service implementation class.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
@Named("venueService")
@Stateless
public class VenueServiceImpl extends AbstractService implements VenueService {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteVenue(Venue venue) {
		Venue found = em.find(Venue.class, venue.getId());
		if (found != null) {
			em.remove(found);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Venue findVenueById(Integer id) {
		return em.find(Venue.class, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Venue> findVenues() {
		String jql = "select v from Venue v order by v.name";
		TypedQuery<Venue> query = em.createQuery(jql, Venue.class);
		List<Venue> venues = query.getResultList();
		return venues;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Venue saveVenue(Venue venue) {
		Venue saved = venue;
		if (venue.getVersion() == null) {
			em.persist(venue);
		} else {
			saved = em.merge(venue);
		}
		return saved;
	}

}
