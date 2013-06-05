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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.persistence.EntityManager;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.mycompany.model.Event;
import com.mycompany.model.EventAttendance;
import com.mycompany.model.EventType;
import com.mycompany.model.User;
import com.mycompany.service.EventService;

/**
 * This class implements controller behavior for a number of screens in the
 * application. It supports the member dashboard, shows, and reviews screens.
 * This class is responsible for handling any calendar event related data and
 * functionality for the application.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
@ManagedBean
@ViewScoped
public class EventController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1256165484728132802L;

	private List<Event> allEvents;

	private List<EventAttendance> allEventsAttendance;

	private Event event;

	@ManagedProperty(value = "#{eventService}")
	private EventService eventService;

	private ScheduleModel userScheduleModel;

	/**
	 * This method is called when the user clicks on a date in the calendar. It
	 * sets the start and end date of the calendar {@link Event} object.
	 * 
	 * @param evt
	 *            The {@link SelectEvent} object.
	 */
	public void dateSelected(SelectEvent evt) {
		event.setStartDate((Date) evt.getObject());
		Calendar cal = Calendar.getInstance();
		cal.setTime((Date) evt.getObject());
		cal.roll(Calendar.HOUR_OF_DAY, 1);
		event.setEndDate(cal.getTime());
	}

	/**
	 * This method is called when the user moves a calendar {@link Event} from
	 * one date/time to another date/time. It updates the object and saves the
	 * changes to the database.
	 * 
	 * @param evt
	 *            The {@link ScheduleEntryMoveEvent} object.
	 */
	public void eventMoved(ScheduleEntryMoveEvent evt) {
		ScheduleEvent scheduleEvent = evt.getScheduleEvent();
		Event event = (Event) scheduleEvent.getData();
		event = eventService.findEventById(event.getId());
		event.setStartDate(scheduleEvent.getStartDate());
		event.setEndDate(scheduleEvent.getEndDate());
		eventService.saveEvent(event);
	}

	/**
	 * This method is called when the user resizes an event. It handles changing
	 * the start and/or end dates of the {@link Event} object and updates the
	 * database.
	 * 
	 * @param evt
	 *            The {@link ScheduleEntryResizeEvent} object.
	 */
	public void eventResized(ScheduleEntryResizeEvent evt) {
		ScheduleEvent scheduleEvent = evt.getScheduleEvent();
		Event event = (Event) scheduleEvent.getData();
		event = eventService.findEventById(event.getId());
		event.setStartDate(scheduleEvent.getStartDate());
		event.setEndDate(scheduleEvent.getEndDate());
		eventService.saveEvent(event);
	}

	/**
	 * This method is called when the user clicks on an existing event in the
	 * schedule. It assigns the selected event to an instance variable so it can
	 * be used for editing.
	 * 
	 * @param evt
	 *            The {@link SelectEvent} object.
	 */
	public void eventSelected(SelectEvent evt) {
		ScheduleEvent scheduleEvent = (ScheduleEvent) evt.getObject();
		event = (Event) scheduleEvent.getData();
	}

	/**
	 * This method returns all {@link Event} objects in the database.
	 * 
	 * @return A List of all objects.
	 */
	public List<Event> getAllEvents() {
		if (allEvents == null) {
			allEvents = eventService.findAllEvents();
		}
		return allEvents;
	}

	/**
	 * This method returns a list of {@link EventAttendance} objects
	 * encapsulating information about a user's attendance for a particular
	 * event.
	 * 
	 * @return A List of EventAttendance objects.
	 */
	public List<EventAttendance> getAllEventsAttendance() {
		if (allEventsAttendance == null) {
			allEventsAttendance = eventService.findAllEventsAttendanceByUser(getLoggedInUser());
		}
		return allEventsAttendance;
	}

	/**
	 * This method returns the currently selected {@link Event}.
	 * 
	 * @return An object.
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * This method returns an array of {@link EventType} enum objects.
	 * 
	 * @return An array of EventType objects.
	 */
	public EventType[] getEventTypes() {
		return EventType.values();
	}

	/**
	 * This method provides a {@link LazyScheduleModel} for the PrimeFaces
	 * schedule component. It loads all events for the current user that have a
	 * start date between two dates. The overridden
	 * {@link LazyScheduleModel#loadEvents(Date, Date)} method is called by
	 * PrimeFaces when data is needed for a particular date range.
	 * 
	 * @return A {@link ScheduleModel} object.
	 */
	public ScheduleModel getUserScheduleModel() {
		if (userScheduleModel == null) {
			userScheduleModel = new LazyScheduleModel() {

				private static final long serialVersionUID = 1L;

				@Override
				public void loadEvents(Date start, Date end) {
					clear();
					User user = getLoggedInUser();
					List<Event> events = eventService.findUserEvents(start, end, user);
					for (Event event : events) {
						String title = event.getTitle();
						Date startDate = event.getStartDate();
						Date endDate = event.getEndDate();
						addEvent(new DefaultScheduleEvent(title, startDate, endDate, event));
					}
				}
			};
		}
		return userScheduleModel;
	}

	/**
	 * This method is called by JSF when the view is first loaded. It loads all
	 * the {@link Event} objects from the database so they can be displayed in
	 * the UI, and prepares the {@link EventAttendance} model for the current
	 * user.
	 * 
	 * @param evt
	 *            The JSF {@link ComponentSystemEvent} object.
	 */
	public void init(ComponentSystemEvent evt) {
		if (event == null) {
			event = new Event();
			userScheduleModel = null;
		}
		if (allEvents == null) {
			// initialize user attendance model
			User user = getLoggedInUser();
			List<Event> events = getAllEvents();
			for (Event event : events) {
				EventAttendance attendance = eventService.findEventAttendance(event, user);
				if (attendance == null) {
					attendance = new EventAttendance();
					attendance.setEvent(event);
					attendance.setUser(user);
					eventService.saveAttendance(attendance);
				}
			}
		}
	}

	/**
	 * This method is called when the user clicks on the "New Event" button in
	 * the mobile web application. It simply creates a new {@link Event} object
	 * and assigns it to the {@link #event} instance variable.
	 * 
	 * @param evt
	 *            The JSF {@link ActionEvent} object.
	 */
	public void newEvent(ActionEvent evt) {
		event = new Event();
	}

	/**
	 * This method is called from the reviews screen to refresh the
	 * {@link EventAttendance} data. When the user reloads the dashboard, the
	 * {@link #getAllEventsAttendance()} method will see that this instance
	 * variable is null, and reload the data to update the graph.
	 * 
	 * @param event
	 *            The JSF {@link ActionEvent} object.
	 */
	public void refreshAttendance(ActionEvent event) {
		allEventsAttendance = null;
	}

	/**
	 * This method is called when the user clicks the "Save" button in the new
	 * event dialog. It saves the {@link Event} object to the database.
	 * 
	 * @param evt
	 *            The JSF {@link ActionEvent} object.
	 */
	public void saveEvent(ActionEvent evt) {
		event.setCreatedBy(getLoggedInUser());
		eventService.saveEvent(event);
		event = null;
		allEvents = null;
		allEventsAttendance = null;
		userScheduleModel = null;
	}

	/**
	 * Sets the {@link Event}.
	 * 
	 * @param event
	 *            The Event object.
	 */
	public void setEvent(Event event) {
		this.event = event;
	}

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}

	/**
	 * <p>
	 * This method updates the current user's EventAttendance object for a
	 * particular event. It uses the JPA {@link EntityManager#merge(Object)}
	 * method to save the object's changes to the database, and then replaces
	 * this object in the {@link #allEventsAttendance} list to ensure the latest
	 * object is loaded in the UI.
	 * </p>
	 * <p>
	 * Note: this method is called directly from the UI since it takes advantage
	 * of the Expression Language (EL) 2.2 feature that allows method argument
	 * passing from JSF.
	 * </p>
	 * 
	 * @param attendance
	 *            The {@link EventAttendance} object.
	 */
	public void updateAttendance(EventAttendance attendance) {
		EventAttendance updated = eventService.saveAttendance(attendance);
		allEventsAttendance.set(allEventsAttendance.indexOf(attendance), updated);
	}

}
