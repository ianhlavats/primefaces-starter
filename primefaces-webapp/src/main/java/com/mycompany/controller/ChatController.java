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

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;

import com.mycompany.model.ActiveUsers;
import com.mycompany.model.User;

/**
 * Controller class for chat functionality.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
@ManagedBean
@ViewScoped
public class ChatController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2706002474535643832L;

	private String globalMessage;

	private static final String CHANNEL = "/chat/";

	@Inject
	private ActiveUsers activeUsers;

	private final PushContext pushContext = PushContextFactory.getDefault().getPushContext();

	public String getGlobalMessage() {
		return globalMessage;
	}

	public void beginChat() {
		User user = getLoggedInUser();
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("socketWidget.cfg.request.trackMessageLength = true;");
		requestContext.execute("socketWidget.connect('/" + user.getUsername() + "')");
		pushContext.push(CHANNEL + "*", user.getUsername() + " joined the channel.");
	}

	public void endChat() {
		// remove user and update ui
		User user = getLoggedInUser();
		RequestContext.getCurrentInstance().update("chatForm:users");
		// disconnect websocket
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("socketWidget.disconnect('/" + user.getUsername() + "')");
		// push leave information
		pushContext.push(CHANNEL + "*", user.getUsername() + " left the channel.");
	}

	public void sendGlobal() {
		String username = getLoggedInUser().getUsername();
		pushContext.push(CHANNEL + "*", username + ": " + globalMessage);
	}

	public void setGlobalMessage(String globalMessage) {
		this.globalMessage = globalMessage;
	}

}
