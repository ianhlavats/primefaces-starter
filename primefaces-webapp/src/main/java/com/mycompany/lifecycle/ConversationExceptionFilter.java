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

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

import javax.enterprise.context.NonexistentConversationException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * This filter handles the {@link NonexistentConversationException} propagating
 * from CDI for requests containing an invalid conversation ID (cid parameter).
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
@WebFilter("/*")
public class ConversationExceptionFilter implements Filter {

	private class ConversationAwareRequestWrapper extends HttpServletRequestWrapper {

		public ConversationAwareRequestWrapper(HttpServletRequest request) {
			super(request);
		}

		@Override
		public String getParameter(String name) {
			if ("cid".equals(name)) {
				return null;
			}
			return super.getParameter(name);
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			Map<String, String[]> map = super.getParameterMap();
			map.remove("cid");
			return map;
		}

		@Override
		public Enumeration<String> getParameterNames() {
			return Collections.enumeration(getParameterMap().keySet());
		}

	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		try {
			chain.doFilter(request, response);
		} catch (ServletException e) {
			if (e.getCause() instanceof NonexistentConversationException) {
				HttpServletRequestWrapper wrapper = new ConversationAwareRequestWrapper(request);
				String context = request.getContextPath();
				String viewId = request.getRequestURI();
				String path = viewId.replaceFirst(context, "");
				request.getRequestDispatcher(path).forward(wrapper, res);
			} else {
				throw e;
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
