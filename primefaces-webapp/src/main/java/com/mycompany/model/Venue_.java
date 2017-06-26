package com.mycompany.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Venue.class)
public abstract class Venue_ extends com.mycompany.model.AbstractEntity_ {

	public static volatile SingularAttribute<Venue, Country> country;
	public static volatile SingularAttribute<Venue, String> phoneNumber;
	public static volatile SingularAttribute<Venue, City> city;
	public static volatile SingularAttribute<Venue, String> streetAddress;
	public static volatile SingularAttribute<Venue, String> name;
	public static volatile SetAttribute<Venue, Event> events;
	public static volatile SingularAttribute<Venue, ProvinceState> provinceState;

}

