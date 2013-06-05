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

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import com.mycompany.model.ActiveUsers;
import com.mycompany.model.User;
import com.mycompany.service.UserService;

/**
 * Controller class for user-related functionality.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * @uml.dependency supplier="com.mycompany.util.UserSession"
 */
@ManagedBean
@ViewScoped
public class UserController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1140838618640961470L;

	@ManagedProperty(value = "#{activeUsers}")
	private ActiveUsers activeUsers;

	private List<String> themes;

	@ManagedProperty(value = "#{userService}")
	private UserService userService;

	public User findUser(String username, String password) {
		return userService.findUser(username, password);
	}

	public List<String> getThemes() {
		if (themes == null) {
			themes = new ArrayList<String>();
			themes.add("aristo");
			themes.add("cupertino");
			themes.add("redmond");
		}
		return themes;
	}

	/**
	 * Returns the currently logged in user.
	 * 
	 * @return A {@link User} object.
	 */
	public User getUser() {
		return getLoggedInUser();
	}

	public String getUserTheme() {
		String theme = null;
		User user = getUser();
		if (user != null) {
			theme = user.getTheme();
		}
		if (theme == null) {
			// use the default theme
			theme = "aristo";
		}
		return theme;
	}

	public void handleClick(ActionEvent event) {
		User user = getUser();
		if (user != null) {
			Integer count = user.getClickCount();
			if (count == null) {
				count = 0;
			}
			user.setClickCount(count + 1);
			user = userService.saveUser(user);
			userSession.setUser(user);
		}
	}

	public boolean isUserPresent(User user) {
		return activeUsers.contains(user);
	}

	public void setActiveUsers(ActiveUsers activeUsers) {
		this.activeUsers = activeUsers;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void updateUserTheme(AjaxBehaviorEvent event) {
		User user = getUser();
		if (user != null) {
			user = userService.saveUser(user);
		}
	}

}
