package com.mycompany.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EventAttendance.class)
public abstract class EventAttendance_ extends com.mycompany.model.AbstractEntity_ {

	public static volatile SingularAttribute<EventAttendance, Integer> rating;
	public static volatile SingularAttribute<EventAttendance, Event> event;
	public static volatile SingularAttribute<EventAttendance, User> user;
	public static volatile SingularAttribute<EventAttendance, Boolean> confirmed;

}

