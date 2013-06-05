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
import java.io.ObjectInputStream;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycompany.model.User;
import com.mycompany.util.UserSession;

/**
 * Base class for controller objects.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
/**
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
public abstract class AbstractController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4862069600034765532L;

	protected transient Logger logger = LoggerFactory.getLogger(getClass());

	@ManagedProperty(value = "#{userSession}")
	protected UserSession userSession;

	/**
	 * Adds a global error message to the response.
	 * 
	 * @param summary
	 *            The message summary.
	 */
	protected void addErrorMessage(String summary) {
		FacesMessage msg = new FacesMessage(summary);
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/**
	 * Convenience method to return the logged in user.
	 * 
	 * @return A {@link User} object.
	 */
	protected User getLoggedInUser() {
		User user = null;
		if (userSession != null) {
			user = userSession.getUser();
		}
		return user;
	}

	/**
	 * Handle deserialization from passivated session and restore transient
	 * fields.
	 * 
	 * @param ois
	 *            The ObjectInputStream object.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		ois.defaultReadObject();
		logger = LoggerFactory.getLogger(getClass());
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}
}
