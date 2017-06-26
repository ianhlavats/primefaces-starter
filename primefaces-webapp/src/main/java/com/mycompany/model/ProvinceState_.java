package com.mycompany.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ProvinceState.class)
public abstract class ProvinceState_ extends com.mycompany.model.AbstractEntity_ {

	public static volatile SingularAttribute<ProvinceState, Country> country;
	public static volatile SetAttribute<ProvinceState, City> cities;
	public static volatile SingularAttribute<ProvinceState, String> name;

}

