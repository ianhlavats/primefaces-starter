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
package com.mycompany.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;

import org.primefaces.event.SelectEvent;

import com.mycompany.model.RelationshipType;
import com.mycompany.model.User;
import com.mycompany.model.UserRelationship;
import com.mycompany.service.UserService;

/**
 * Controller class for contacts related functionality.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
@ManagedBean
@ViewScoped
public class ContactsController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1709154434943491658L;

	private List<User> colleagues;

	@ManagedProperty(value = "#{userService}")
	private UserService userService;

	private List<User> family;

	private List<User> friends;

	private UserRelationship relationship;

	private RelationshipType relationshipType;

	public List<User> findUsersByName(String suggest) {
		if (suggest == null) {
			return null;
		}
		List<User> users = userService.findUsersByName(suggest);
		// remove current user from list
		users.remove(getLoggedInUser());
		logger.info("Found {}  users for '{}'", users.size(), suggest);
		return users;
	}

	public List<User> getColleagues() {
		if (colleagues == null) {
			colleagues = userService.findUsersByType(RelationshipType.COLLEAGUE, getLoggedInUser());
		}
		return colleagues;
	}

	public List<User> getFamily() {
		if (family == null) {
			family = userService.findUsersByType(RelationshipType.FAMILY, getLoggedInUser());
		}
		return family;
	}

	public List<User> getFriends() {
		if (friends == null) {
			friends = userService.findUsersByType(RelationshipType.FRIEND, getLoggedInUser());
		}
		return friends;
	}

	public UserRelationship getRelationship() {
		return relationship;
	}

	public RelationshipType getRelationshipType() {
		return relationshipType;
	}

	public RelationshipType[] getRelationshipTypes() {
		return RelationshipType.values();
	}

	public RelationshipType[] getUserTypes() {
		return RelationshipType.values();
	}

	public void init(ComponentSystemEvent event) {
		if (relationship == null) {
			relationship = new UserRelationship();
			relationship.setFromUser(getLoggedInUser());
		}
	}

	public void newRelationship(ActionEvent event) {
		relationship = new UserRelationship();
	}

	public String saveRelationship() {
		if (relationship.getToUser().equals(getLoggedInUser())) {
			// throw new
			// IllegalArgumentException("Unable to create relationship to self");
			addErrorMessage("Unable to create relationship to self");
			return null;
		}
		userService.saveRelationship(relationship);
		relationship = null;
		family = null;
		friends = null;
		colleagues = null;
		return null;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setRelationship(UserRelationship relationship) {
		this.relationship = relationship;
	}

	public void userSelected(SelectEvent event) {
		// load an existing relationship so it can be edited
		relationship = userService.findRelationshipToUser(getLoggedInUser(),
				relationship.getToUser());
	}

}
