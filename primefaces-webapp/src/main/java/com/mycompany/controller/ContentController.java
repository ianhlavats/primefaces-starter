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

import java.util.Date;

import javax.enterprise.context.Conversation;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

import com.mycompany.model.Content;
import com.mycompany.service.ContentService;

/**
 * Controller class for content editing.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
@ManagedBean
@ViewScoped
public class ContentController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5256865616437754501L;

	private Content content;

	@ManagedProperty(value = "#{contentService}")
	private ContentService contentService;

	@Inject
	private Conversation conversation;

	private boolean editing;

	public void contentChanged(ValueChangeEvent event) {
		saveContentAndStopEditing();
	}

	public Content getContent() {
		return content;
	}

	public void init(ComponentSystemEvent event) {
		String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
		if (content == null || !viewId.equals(content.getViewId())) {
			content = contentService.findContentByName(viewId);
		}
		if (content == null) {
			content = new Content();
			content.setViewId(viewId);
			content.setCreatedBy(getLoggedInUser());
			content.setCreatedDate(new Date());
		}
		if (conversation.isTransient()) {
			conversation.begin();
		}
	}

	public boolean isEditing() {
		return editing;
	}

	public void saveContent(ActionEvent event) {
		saveContentAndStopEditing();
	}

	private void saveContentAndStopEditing() {
		contentService.saveContent(content);
		content = null;
		editing = false;
		conversation.end();
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}

	public void setEditing(boolean editing) {
		this.editing = editing;
	}

	public void toggleEditing(ActionEvent event) {
		editing = !editing;
	}

}
