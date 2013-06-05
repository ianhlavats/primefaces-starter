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

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.mindmap.DefaultMindmapNode;
import org.primefaces.model.mindmap.MindmapNode;

import com.mycompany.model.Idea;
import com.mycompany.service.IdeaService;

/**
 * Controller class for mind map feature.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
@ManagedBean
@ViewScoped
public class MindMapController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4810848975079411459L;

	@ManagedProperty(value = "#{ideaService}")
	private IdeaService ideaService;

	private Idea newIdea;

	private MindmapNode root;

	private Idea selectedIdea;

	private MindmapNode selectedNode;

	public Idea getNewIdea() {
		return newIdea;
	}

	public MindmapNode getRoot() {
		return root;
	}

	public MindmapNode getSelectedNode() {
		return selectedNode;
	}

	public void init(ComponentSystemEvent event) {
		if (newIdea == null) {
			newIdea = new Idea();
			loadIdeas();
		}
	}

	private void loadIdeas() {
		List<Idea> ideas = ideaService.findIdeas();
		if (!ideas.isEmpty()) {
			Idea rootIdea = ideas.get(0);
			String label = rootIdea.getName();
			root = new DefaultMindmapNode(label, rootIdea, "FFCC00", true);
			ideaService.buildMindMap(rootIdea, root);
		}
	}

	public void onNodeDblselect(SelectEvent event) {
		this.selectedNode = (MindmapNode) event.getObject();
		this.selectedIdea = (Idea) this.selectedNode.getData();
	}

	public void onNodeSelect(SelectEvent event) {
	}

	public void saveIdea(ActionEvent event) {
		if (selectedIdea != null) {
			newIdea.setParent(selectedIdea);
		}
		newIdea.setAuthor(getLoggedInUser());
		ideaService.saveIdea(newIdea);
		root = null;
		newIdea = null;
	}

	public void setIdeaService(IdeaService ideaService) {
		this.ideaService = ideaService;
	}

	public void setNewIdea(Idea newIdea) {
		this.newIdea = newIdea;
	}

	public void setSelectedNode(MindmapNode selectedNode) {
		this.selectedNode = selectedNode;
	}
}
