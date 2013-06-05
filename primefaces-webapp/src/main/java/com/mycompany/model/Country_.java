package com.mycompany.model;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Country.class)
public abstract class Country_ extends com.mycompany.model.AbstractEntity_ {

	public static volatile SetAttribute<Country, ProvinceState> provinceStates;
	public static volatile SingularAttribute<Country, String> name;
	public static volatile SingularAttribute<Country, Integer> hashCode;
	public static volatile SingularAttribute<Country, String> code;

}

