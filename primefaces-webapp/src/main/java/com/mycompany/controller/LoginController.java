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

import java.io.IOException;

import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import com.mycompany.model.ActiveUsers;
import com.mycompany.model.Credentials;
import com.mycompany.model.User;
import com.mycompany.service.UserService;

/**
 * Controller class for desktop and mobile login functionality.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * @uml.dependency supplier="com.mycompany.controller.AbstractController"
 */
@ManagedBean
@ViewScoped
public class LoginController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6710063228036238737L;

	@ManagedProperty(value = "#{activeUsers}")
	private ActiveUsers activeUsers;

	@ManagedProperty(value = "#{credentials}")
	private Credentials credentials;

	@ManagedProperty(value = "#{userService}")
	private UserService userService;

	public void checkPermission(ComponentSystemEvent event) throws IOException {
		if (!isLoggedIn()) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext external = ctx.getExternalContext();
			if (!external.isResponseCommitted()) {
				NavigationHandler handler = ctx.getApplication().getNavigationHandler();
				handler.handleNavigation(ctx, null, "login");
			}
		}
	}

	public boolean isLoggedIn() {
		return getLoggedInUser() != null;
	}

	public String login() {
		String outcome = null;
		try {
			String username = credentials.getUsername();
			String password = credentials.getPassword();
			User user = userService.findUser(username, password);
			if (user == null) {
				addErrorMessage("Invalid login");
				userSession.setUser(null);
			} else {
				userSession.setUser(user);
				if (!activeUsers.contains(user)) {
					activeUsers.add(user);
				}
				outcome = "members";
			}
		} catch (Exception e) {
			logger.error("Unable to login:", e);
		}
		return outcome;
	}

	public String loginMobile() {
		login();
		if (isLoggedIn()) {
			return "pm:members";
		}
		return null;
	}

	public String logout() {
		activeUsers.remove(getLoggedInUser());
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "login";
	}

	public String logoutMobile() {
		activeUsers.remove(getLoggedInUser());
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "pm:main?reverse=true";
	}

	public void setActiveUsers(ActiveUsers activeUsers) {
		this.activeUsers = activeUsers;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
