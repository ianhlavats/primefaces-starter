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

import com.mycompany.model.Venue;

/**
 * Interface for a venue service.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
public interface VenueService {

	/**
	 * Deletes a {@link Venue} object.
	 * 
	 * @param venue
	 *            The Venue to delete.
	 */
	void deleteVenue(Venue venue);

	/**
	 * Finds a {@link Venue} object by ID.
	 * 
	 * @param id
	 *            The ID of the Venue.
	 * @return The Venue object.
	 */
	Venue findVenueById(Integer id);

	/**
	 * Finds a {@link List} of all {@link Venue} objects.
	 * 
	 * @return A List of Venue objects.
	 */
	List<Venue> findVenues();

	/**
	 * Saves a {@link Venue} object.
	 * 
	 * @param venue
	 *            The Venue to save.
	 * @return The saved object.
	 */
	Venue saveVenue(Venue venue);

}
