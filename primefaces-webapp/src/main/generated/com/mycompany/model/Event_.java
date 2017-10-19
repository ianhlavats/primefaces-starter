package com.mycompany.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Event.class)
public abstract class Event_ extends com.mycompany.model.AbstractEntity_ {

	public static volatile SingularAttribute<Event, Venue> venue;
	public static volatile SingularAttribute<Event, User> createdBy;
	public static volatile SingularAttribute<Event, Date> endDate;
	public static volatile SingularAttribute<Event, String> description;
	public static volatile SingularAttribute<Event, EventType> eventType;
	public static volatile SingularAttribute<Event, String> title;
	public static volatile SetAttribute<Event, EventAttendance> attendance;
	public static volatile SingularAttribute<Event, Date> startDate;

}

