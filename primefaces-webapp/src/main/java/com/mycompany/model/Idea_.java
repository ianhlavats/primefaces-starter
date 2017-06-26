package com.mycompany.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Idea.class)
public abstract class Idea_ extends com.mycompany.model.AbstractEntity_ {

	public static volatile SingularAttribute<Idea, Idea> parent;
	public static volatile SetAttribute<Idea, Idea> children;
	public static volatile SingularAttribute<Idea, User> author;
	public static volatile SingularAttribute<Idea, String> name;
	public static volatile SingularAttribute<Idea, String> description;

}

