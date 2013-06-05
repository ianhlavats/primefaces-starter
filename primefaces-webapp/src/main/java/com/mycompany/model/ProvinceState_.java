package com.mycompany.model;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ProvinceState.class)
public abstract class ProvinceState_ extends com.mycompany.model.AbstractEntity_ {

	public static volatile SetAttribute<ProvinceState, City> cities;
	public static volatile SingularAttribute<ProvinceState, String> name;
	public static volatile SingularAttribute<ProvinceState, Integer> hashCode;
	public static volatile SingularAttribute<ProvinceState, Country> country;

}

