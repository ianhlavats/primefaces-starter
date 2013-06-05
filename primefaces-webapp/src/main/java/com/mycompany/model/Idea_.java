package com.mycompany.model;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Idea.class)
public abstract class Idea_ extends com.mycompany.model.AbstractEntity_ {

	public static volatile SingularAttribute<Idea, User> author;
	public static volatile SingularAttribute<Idea, String> description;
	public static volatile SingularAttribute<Idea, String> name;
	public static volatile SingularAttribute<Idea, Integer> hashCode;
	public static volatile SingularAttribute<Idea, Idea> parent;
	public static volatile SetAttribute<Idea, Idea> children;

}

