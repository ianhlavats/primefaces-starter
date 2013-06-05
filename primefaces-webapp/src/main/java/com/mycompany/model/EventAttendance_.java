package com.mycompany.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(EventAttendance.class)
public abstract class EventAttendance_ extends com.mycompany.model.AbstractEntity_ {

	public static volatile SingularAttribute<EventAttendance, Boolean> confirmed;
	public static volatile SingularAttribute<EventAttendance, Event> event;
	public static volatile SingularAttribute<EventAttendance, Integer> rating;
	public static volatile SingularAttribute<EventAttendance, User> user;

}

