package com.mycompany.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Country.class)
public abstract class Country_ extends com.mycompany.model.AbstractEntity_ {

	public static volatile SingularAttribute<Country, String> code;
	public static volatile SetAttribute<Country, ProvinceState> provinceStates;
	public static volatile SingularAttribute<Country, String> name;

}

