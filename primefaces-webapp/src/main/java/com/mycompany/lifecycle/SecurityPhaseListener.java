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
package com.mycompany.lifecycle;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import com.mycompany.controller.LoginController;

/**
 * PhaseListener class that handles authorization for protected views.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
public class SecurityPhaseListener implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1013239137374622294L;

	@Override
	public void afterPhase(PhaseEvent event) {

		// eagerly create session due to JAVASERVERFACES-2215
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSession(true);

		// check if view is protected
		UIViewRoot view = context.getViewRoot();
		if (view != null) {
			String viewId = view.getViewId();
			boolean authenticationRequired = viewId.contains("/protected/");
			boolean loggedIn = isLoggedIn();
			if (authenticationRequired && !loggedIn) {
				// send user to login page if not logged in
				context.getApplication()
						.getNavigationHandler()
						.handleNavigation(context, null, "login");
			}
		}
	}

	@Override
	public void beforePhase(PhaseEvent event) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

	private boolean isLoggedIn() {
		boolean loggedIn = false;
		FacesContext ctx = FacesContext.getCurrentInstance();
		LoginController controller = (LoginController) ctx.getApplication().evaluateExpressionGet(
				ctx, "#{loginController}", Object.class);
		loggedIn = controller.isLoggedIn();
		return loggedIn;
	}

}
