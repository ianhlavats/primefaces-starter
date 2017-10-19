package com.mycompany.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Content.class)
public abstract class Content_ extends com.mycompany.model.AbstractEntity_ {

	public static volatile SingularAttribute<Content, Date> createdDate;
	public static volatile SingularAttribute<Content, String> viewId;
	public static volatile SingularAttribute<Content, User> createdBy;
	public static volatile SingularAttribute<Content, String> value;

}

