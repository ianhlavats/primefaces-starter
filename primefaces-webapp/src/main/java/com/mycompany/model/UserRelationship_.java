package com.mycompany.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(UserRelationship.class)
public abstract class UserRelationship_ extends com.mycompany.model.AbstractEntity_ {

	public static volatile SingularAttribute<UserRelationship, RelationshipType> relationshipType;
	public static volatile SingularAttribute<UserRelationship, User> toUser;
	public static volatile SingularAttribute<UserRelationship, User> fromUser;

}

