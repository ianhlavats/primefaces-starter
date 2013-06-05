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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.mycompany.model.Country;
import com.mycompany.model.Gender;
import com.mycompany.model.User;
import com.mycompany.service.CountryService;
import com.mycompany.service.UserService;

/**
 * Controller class for new user signup.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * @uml.dependency supplier="com.mycompany.model.Country"
 */
@ManagedBean
@ViewScoped
public class SignupController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3479887222411577225L;

	@ManagedProperty(value = "#{countryService}")
	private CountryService countryService;

	private User user;

	@ManagedProperty(value = "#{userService}")
	private UserService userService;

	public SignupController() {
		user = new User();
	}

	public void createUser(ActionEvent event) {
		user = new User();
	}

	public List<Country> getCountries() {
		return countryService.getCountries();
	}

	public Gender[] getGenders() {
		return Gender.values();
	}

	/**
	 * Return a date 100 years ago.
	 * 
	 * @return A Date object.
	 */
	public Date getMinDate() {
		Calendar cal = Calendar.getInstance();
		cal.roll(Calendar.YEAR, -100);
		return cal.getTime();
	}

	public User getUser() {
		return user;
	}

	/**
	 * This method checks to see if the username already exists.
	 * 
	 * @return A boolean value.
	 */
	private boolean isValidUser() {
		boolean valid = false;
		String username = user.getUsername();
		if (username != null) {
			if (userService.findUserByUsername(username) == null) {
				valid = true;
			} else {
				FacesMessage msg = new FacesMessage();
				msg.setDetail("The username you have entered is already in use.");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
		return valid;
	}

	public void setCountryService(CountryService countryService) {
		this.countryService = countryService;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String signup() {
		if (isValidUser()) {
			userService.saveUser(user);
			return "login";
		}
		return null;
	}

	public String signupMobile() {
		String outcome = signup();
		if (outcome != null) {
			return "pm:main";
		}
		return null;
	}
}
