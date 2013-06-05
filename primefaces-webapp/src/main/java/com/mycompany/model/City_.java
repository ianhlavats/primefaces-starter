package com.mycompany.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(City.class)
public abstract class City_ extends com.mycompany.model.AbstractEntity_ {

	public static volatile SingularAttribute<City, ProvinceState> provinceState;
	public static volatile SingularAttribute<City, String> name;
	public static volatile SingularAttribute<City, Double> longitude;
	public static volatile SingularAttribute<City, Double> latitude;

}

