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
package com.mycompany.websocket;

import static org.atmosphere.cpr.BroadcasterLifeCyclePolicy.EMPTY_DESTROY;

import org.atmosphere.cpr.AtmosphereRequest;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.BroadcasterLifeCyclePolicyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for GlassFish WebSocket support with PrimeFaces.
 */
public class DefaultPushRule extends org.primefaces.push.DefaultPushRule {

	public boolean apply(AtmosphereResource resource) {
		AtmosphereRequest req = resource.getRequest();
		String pathInfo = req.getPathInfo();
		if (pathInfo == null) {
			String servletPath = req.getServletPath();
			String contextPath = req.getContextPath();
			pathInfo = req.getRequestURI()
							.replaceFirst(contextPath, "")
							.replaceFirst(servletPath, "");
		}
		if (pathInfo == null || pathInfo == "") {
			resource.setBroadcaster(BroadcasterFactory.getDefault().lookup("/*"));
			return true;
		}
		final Broadcaster b = BroadcasterFactory.getDefault().lookup(pathInfo, true);
		b.setBroadcasterLifeCyclePolicy(EMPTY_DESTROY);
		b.addBroadcasterLifeCyclePolicyListener(new BroadcasterLifeCyclePolicyListener() {

			private final Logger logger = LoggerFactory.getLogger(BroadcasterLifeCyclePolicyListener.class);

			public void onEmpty() {
				logger.trace("onEmpty {}", b.getID());
			}

			public void onIdle() {
				logger.trace("onIdle {}", b.getID());
			}

			public void onDestroy() {
				logger.trace("onDestroy {}", b.getID());
			}
		});
		resource.setBroadcaster(b);

		return true;
	}
}
