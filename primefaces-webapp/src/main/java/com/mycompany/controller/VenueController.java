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
package com.mycompany.controller;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;

import com.mycompany.model.City;
import com.mycompany.model.Country;
import com.mycompany.model.ProvinceState;
import com.mycompany.model.Venue;
import com.mycompany.service.CountryService;
import com.mycompany.service.VenueService;

/**
 * Controller class for venue-related functionality.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
@ManagedBean
@ViewScoped
public class VenueController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1127963225862339644L;

	@ManagedProperty(value = "#{countryService}")
	private CountryService countryService;

	private Venue venue;

	@ManagedProperty(value = "#{venueService}")
	private VenueService venueService;

	public void deleteVenue(ActionEvent evt) {
		venueService.deleteVenue(venue);
		this.venue = null;
	}

	public List<ProvinceState> findProvinceStatesByCountry(Country country) {
		return countryService.findProvinceStatesByCountry(country);
	}

	public List<Country> getCountries() {
		return countryService.getCountries();
	}

	public Venue getVenue() {
		return venue;
	}

	public List<Venue> getVenues() {
		return venueService.findVenues();
	}

	public void init(ComponentSystemEvent evt) {
		if (venue == null) {
			venue = new Venue();
			venue.setCity(new City());
		}
	}

	public void newVenue(ActionEvent event) {
		this.venue = new Venue();
		this.venue.setCity(new City());
	}

	public void saveVenue(ActionEvent evt) {
		try {
			// lookup existing city
			City city = countryService.findCity(venue.getCity().getName(), venue.getProvinceState());
			if (city == null) {
				venue.getCity().setProvinceState(venue.getProvinceState());
			} else {
				venue.setCity(city);
			}
			venueService.saveVenue(venue);
			FacesMessage msg = new FacesMessage();
			msg.setDetail("The venue has been saved!");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			String id = evt.getComponent().getClientId();
			FacesContext.getCurrentInstance().addMessage(id, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage();
			msg.setDetail("Failed to save venue: " + e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void setCountryService(CountryService countryService) {
		this.countryService = countryService;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}

	public void setVenueService(VenueService venueService) {
		this.venueService = venueService;
	}

	public void venueSelected(ValueChangeEvent evt) {
	}
}
