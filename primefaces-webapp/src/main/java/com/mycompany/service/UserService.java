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
package com.mycompany.service;

import java.util.List;

import com.mycompany.model.RelationshipType;
import com.mycompany.model.User;
import com.mycompany.model.UserRelationship;

/**
 * Interface for user service.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
public interface UserService {

	/**
	 * Finds a {@link UserRelationship} object representing a relationship
	 * between two {@link User} objects.
	 * 
	 * @param user1
	 *            The first user.
	 * @param user2
	 *            The second user.
	 * @return A UserRelationship object.
	 */
	UserRelationship findRelationshipToUser(User user1, User user2);

	/**
	 * Finds a {@link User} with the given username and password. Used for
	 * authentication.
	 * 
	 * @param username
	 *            The username.
	 * @param password
	 *            The password.
	 * @return A User object if found.
	 */
	User findUser(String username, String password);

	/**
	 * Finds a {@link User} by ID.
	 * 
	 * @param id
	 *            The user's ID.
	 * @return A User object.
	 */
	User findUserById(Integer id);

	/**
	 * Finds a {@link User} by username.
	 * 
	 * @param username
	 *            The username.
	 * @return A User object.
	 */
	User findUserByUsername(String username);

	/**
	 * Finds a List of all {@link User} objects.
	 * 
	 * @return A List of User objects.
	 */
	List<User> findUsers();

	/**
	 * Finds a List of {@link User} objects that have a first name or last name
	 * that partially matches the suggestion string.
	 * 
	 * @param suggest
	 *            The partial string.
	 * @return A List of User objects.
	 */
	List<User> findUsersByName(String suggest);

	/**
	 * Finds a List of {@link User} objects that are related to the {@link User}
	 * according to the specified {@link RelationshipType}.
	 * 
	 * @param type
	 *            The {@link RelationshipType} object.
	 * @param user
	 *            The {@link User} object.
	 * @return A List of {@link User} objects.
	 */
	List<User> findUsersByType(RelationshipType type, User user);

	/**
	 * Saves a {@link UserRelationship} object.
	 * 
	 * @param relationship
	 *            The UserRelationship to save.
	 * @return The saved object.
	 */
	UserRelationship saveRelationship(UserRelationship relationship);

	/**
	 * Saves a {@link User} object.
	 * 
	 * @param user
	 *            The user to save.
	 * @return The saved object.
	 */
	User saveUser(User user);

}
