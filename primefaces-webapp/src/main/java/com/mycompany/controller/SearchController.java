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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Overlay;

import com.mycompany.model.City;
import com.mycompany.model.Event;
import com.mycompany.model.EventType;
import com.mycompany.model.ProvinceState;
import com.mycompany.model.Venue;
import com.mycompany.service.CountryService;
import com.mycompany.service.EventService;

/**
 * Controller class for searching for shows and events.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
@ManagedBean
@ViewScoped
public class SearchController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3648708195333963881L;

	private boolean alcoholPermitted;

	private City city;

	@ManagedProperty(value = "#{countryService}")
	private CountryService countryService;

	@ManagedProperty(value = "#{eventService}")
	private EventService eventService;

	private EventType eventType;

	private String keyword;

	private String mapCenter;

	private Integer mapZoom;

	private int maxResults;

	private boolean noCoverCharge;

	private boolean outdoorVenue;

	private ProvinceState provinceState;

	private MapModel searchResultsModel;

	private Event selectedEvent;

	public SearchController() {
		searchResultsModel = new DefaultMapModel();
	}

	public void eventSelected(OverlaySelectEvent evt) {
		Overlay overlay = evt.getOverlay();
		this.selectedEvent = (Event) overlay.getData();
	}

	public List<City> getCitiesForState() {
		if (provinceState == null) {
			return null;
		}
		return countryService.findCitiesByState(provinceState);
	}

	public City getCity() {
		return city;
	}

	private LatLng getCoordinates(Venue venue) {
		LatLng coords = null;
		try {
			if (venue.getLatitude() == null || venue.getLongitude() == null) {
				// lookup address coordinates using Google Geocoding API
				String address = URLEncoder.encode(venue.getCompleteAddress(), "UTF-8");
				String uri = "http://maps.googleapis.com/maps/api/geocode/json?sensor=true&address="
						+ address;
				ObjectMapper mapper = new ObjectMapper();
				JsonNode root = mapper.readTree(new URL(uri));
				logger.info("{}", root);
				JsonNode geometry = root.findValue("geometry");
				if (geometry.isMissingNode()) {
					return null;
				}
				JsonNode location = geometry.path("location");
				JsonNode latitude = location.path("lat");
				JsonNode longitude = location.path("lng");
				City city = venue.getCity();
				if (city.getLatitude() == null || city.getLongitude() == null) {
					city.setLatitude(latitude.asDouble());
					city.setLongitude(longitude.asDouble());
					// Google Geocoding license does not permit storing in DB
				}
				venue.setLatitude(latitude.asDouble());
				venue.setLongitude(longitude.asDouble());
			}
			coords = new LatLng(venue.getLatitude(), venue.getLongitude());
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception: {}", e);
		} catch (MalformedURLException e) {
			logger.error("Exception: {}", e);
		} catch (IOException e) {
			logger.error("Exception: {}", e);
		}
		return coords;
	}

	public EventType getEventType() {
		return eventType;
	}

	public String getKeyword() {
		return keyword;
	}

	public String getMapCenter() {
		if (mapCenter == null) {
			if (city == null) {
				// center on USA
				mapCenter = "38.889722,-77.008889";
			} else {
				mapCenter = city.getLatitude() + "," + city.getLongitude();
			}
		}
		return mapCenter;
	}

	public Integer getMapZoom() {
		if (mapZoom == null) {
			mapZoom = 2;
		}
		return mapZoom;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public ProvinceState getProvinceState() {
		return provinceState;
	}

	public List<ProvinceState> getProvinceStates() {
		return countryService.getProvinceStates();
	}

	public MapModel getSearchResultsModel() {
		return searchResultsModel;
	}

	public Event getSelectedEvent() {
		return selectedEvent;
	}

	public void init(ComponentSystemEvent event) {
	}

	public boolean isAlcoholPermitted() {
		return alcoholPermitted;
	}

	public boolean isNoCoverCharge() {
		return noCoverCharge;
	}

	public boolean isOutdoorVenue() {
		return outdoorVenue;
	}

	/**
	 * This method searches the database for events.
	 * 
	 * @param evt
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void search(ActionEvent evt) {

		// Get the list of shows from our database
		List<Event> events = eventService.findEvents(city, provinceState, eventType, keyword);
		if (events.isEmpty()) {
			// zoom out to show world
			mapZoom = 2;
			return;
		}

		// reset center
		mapCenter = null;

		// zoom in to show city
		mapZoom = 12;

		// Build a model for the Google map
		for (Event event : events) {
			LatLng coords = getCoordinates(event.getVenue());
			if (coords == null) {
				logger.warn("Warning - no coordinates for location: "
						+ event.getVenue().getCompleteAddress());
				continue;
			}
			searchResultsModel.addOverlay(new Marker(coords, event.getTitle(), event));

			// center map on city of first event
			if (mapCenter == null) {
				City eventCity = event.getVenue().getCity();
				mapCenter = eventCity.getLatitude() + "," + eventCity.getLongitude();
			}
		}
	}

	public void setAlcoholPermitted(boolean alcoholPermitted) {
		this.alcoholPermitted = alcoholPermitted;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public void setCountryService(CountryService countryService) {
		this.countryService = countryService;
	}

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	public void setNoCoverCharge(boolean noCoverCharge) {
		this.noCoverCharge = noCoverCharge;
	}

	public void setOutdoorVenue(boolean outdoorVenue) {
		this.outdoorVenue = outdoorVenue;
	}

	public void setProvinceState(ProvinceState state) {
		this.provinceState = state;
	}

	public void setSelectedEvent(Event selectedEvent) {
		this.selectedEvent = selectedEvent;
	}

}
