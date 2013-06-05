/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013 Ian Hlavats.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * 
 */
package com.mycompany.service.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.mycompany.model.RelationshipType;
import com.mycompany.model.User;
import com.mycompany.model.UserRelationship;
import com.mycompany.service.UserService;
import com.mycompany.util.Queries;

/**
 * User service implementation class.
 * 
 * @uml.dependency supplier="javax.persistence.EntityManager"
 */
@Named("userService")
@Stateless
public class UserServiceImpl extends AbstractService implements UserService {

	/**
	 * {@inheritDoc}
	 */
	public UserRelationship findRelationshipToUser(User user1, User user2) {
		UserRelationship found = null;
		try {
			String jql = "select r from UserRelationship r where r.fromUser = ?1 and r.toUser = ?2";
			TypedQuery<UserRelationship> query = em.createQuery(jql, UserRelationship.class);
			query.setParameter(1, user1);
			query.setParameter(2, user2);
			found = query.getSingleResult();
		} catch (NoResultException e) {
		}
		return found;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User findUser(String username, String password) {
		TypedQuery<User> query = em.createNamedQuery(Queries.USER_FIND_BY_USERNAME_PASSWORD,
				User.class);
		query.setParameter(1, username);
		query.setParameter(2, password);
		List<User> users = query.getResultList();
		if (!users.isEmpty()) {
			return users.get(0);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User findUserById(Integer id) {
		return em.find(User.class, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User findUserByUsername(String username) {
		String jql = "select u from User u where u.username = ?1";
		TypedQuery<User> query = em.createQuery(jql, User.class);
		query.setParameter(1, username);
		List<User> users = query.getResultList();
		if (!users.isEmpty()) {
			return users.get(0);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> findUsers() {
		String jql = "select u from User u order by u.username";
		List<User> users = em.createQuery(jql, User.class).getResultList();
		return users;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> findUsersByName(String suggest) {
		TypedQuery<User> query = em.createNamedQuery(Queries.USER_FIND_BY_PARTIAL_NAME, User.class);
		query.setParameter("name", "%" + suggest.trim() + "%");
		List<User> users = query.getResultList();
		return users;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> findUsersByType(RelationshipType type, User user) {
		TypedQuery<User> query = em.createNamedQuery(Queries.USER_FIND_BY_RELATIONSHIP_TYPE,
				User.class);
		query.setParameter(1, type);
		query.setParameter(2, user);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRelationship saveRelationship(UserRelationship relationship) {
		UserRelationship saved = relationship;
		if (relationship.getVersion() == null) {
			em.persist(relationship);
		} else {
			saved = em.merge(relationship);
		}
		return saved;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User saveUser(User user) {
		User saved = user;
		if (user.getVersion() == null) {
			em.persist(user);
		} else {
			saved = em.merge(user);
		}
		return saved;
	}
}
