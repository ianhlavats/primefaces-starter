package com.mycompany.model;

import java.util.Date;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Event.class)
public abstract class Event_ extends com.mycompany.model.AbstractEntity_ {

	public static volatile SingularAttribute<Event, Date> startDate;
	public static volatile SingularAttribute<Event, String> title;
	public static volatile SingularAttribute<Event, User> createdBy;
	public static volatile SingularAttribute<Event, String> description;
	public static volatile SetAttribute<Event, EventAttendance> attendance;
	public static volatile SingularAttribute<Event, Integer> hashCode;
	public static volatile SingularAttribute<Event, Date> endDate;
	public static volatile SingularAttribute<Event, EventType> eventType;
	public static volatile SingularAttribute<Event, Venue> venue;

}

