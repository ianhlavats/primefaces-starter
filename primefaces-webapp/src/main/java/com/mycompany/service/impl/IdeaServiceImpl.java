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
package com.mycompany.service.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.TypedQuery;

import org.primefaces.model.mindmap.DefaultMindmapNode;
import org.primefaces.model.mindmap.MindmapNode;

import com.mycompany.model.Idea;
import com.mycompany.service.IdeaService;

/**
 * Idea service implementation class.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
@Named("ideaService")
@Stateless
public class IdeaServiceImpl extends AbstractService implements IdeaService {

	/**
	 * {@inheritDoc}
	 */
	public void buildMindMap(Idea parentIdea, MindmapNode parentNode) {
		Idea idea = parentIdea;
		if (!em.contains(idea)) {
			idea = em.find(Idea.class, idea.getId());
		}
		if (idea.getChildren().isEmpty()) {
			return;
		}
		for (Idea childIdea : idea.getChildren()) {
			String label = childIdea.getName();
			MindmapNode childNode = new DefaultMindmapNode(label, childIdea, "6e9ebf", true);
			parentNode.addNode(childNode);
			buildMindMap(childIdea, childNode);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Idea findIdea(Integer id) {
		return em.find(Idea.class, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Idea> findIdeas() {
		String jql = "select idea from Idea idea where idea.parent is null";
		TypedQuery<Idea> query = em.createQuery(jql, Idea.class);
		List<Idea> ideas = query.getResultList();
		return ideas;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Idea saveIdea(Idea idea) {
		Idea saved = idea;
		if (idea.getVersion() == null) {
			em.persist(idea);
		} else {
			saved = em.merge(idea);
		}
		return saved;
	}

}
