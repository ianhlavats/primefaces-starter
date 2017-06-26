package com.mycompany.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(City.class)
public abstract class City_ extends com.mycompany.model.AbstractEntity_ {

	public static volatile SingularAttribute<City, Double> latitude;
	public static volatile SingularAttribute<City, String> name;
	public static volatile SingularAttribute<City, ProvinceState> provinceState;
	public static volatile SingularAttribute<City, Double> longitude;

}

