package com.mycompany.model;

import java.util.Date;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(User.class)
public abstract class User_ extends com.mycompany.model.AbstractEntity_ {

	public static volatile SingularAttribute<User, Boolean> acceptedTerms;
	public static volatile SingularAttribute<User, String> lastName;
	public static volatile SingularAttribute<User, Integer> clickCount;
	public static volatile SetAttribute<User, UserRelationship> fromRelationships;
	public static volatile SingularAttribute<User, String> theme;
	public static volatile SingularAttribute<User, Integer> hashCode;
	public static volatile SingularAttribute<User, String> emailAddress;
	public static volatile SetAttribute<User, UserRelationship> toRelationships;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, Country> country;
	public static volatile SingularAttribute<User, String> username;
	public static volatile SetAttribute<User, EventAttendance> eventsAttended;
	public static volatile SingularAttribute<User, String> phoneNumber;
	public static volatile SingularAttribute<User, Date> birthdate;
	public static volatile SingularAttribute<User, Gender> gender;
	public static volatile SingularAttribute<User, String> firstName;

}

