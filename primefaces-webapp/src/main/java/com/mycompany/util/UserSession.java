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
package com.mycompany.util;

import java.io.Serializable;

import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mycompany.model.ActiveUsers;
import com.mycompany.model.User;

/**
 * Session-scoped CDI managed bean to store a {@link User} object.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
@Named("userSession")
@SessionScoped
public class UserSession implements Serializable {

	@Inject
	private ActiveUsers activeUsers;

	/**
	 * 
	 */
	private static final long serialVersionUID = 8195784747974150341L;

	private User user;

	/**
	 * Returns the current {@link User}.
	 * 
	 * @return A User object.
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the {@link User}.
	 * 
	 * @param user
	 *            The User object.
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * CDI calls this method before the bean is destroyed. Since this class is
	 * session-scoped, it will get called if the user session expires, allowing
	 * us to remove the user from the active user list.
	 */
	@PreDestroy
	public void release() {
		activeUsers.remove(user);
	}

}
