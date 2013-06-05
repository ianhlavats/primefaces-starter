package com.mycompany.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(AbstractEntity.class)
public abstract class AbstractEntity_ {

	public static volatile SingularAttribute<AbstractEntity, Integer> id;
	public static volatile SingularAttribute<AbstractEntity, Integer> hashCode;
	public static volatile SingularAttribute<AbstractEntity, Integer> version;

}

