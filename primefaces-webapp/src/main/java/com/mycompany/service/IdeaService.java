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
package com.mycompany.service;

import java.util.List;

import org.primefaces.model.mindmap.MindmapNode;

import com.mycompany.model.Idea;

/**
 * Interface for an idea service.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
public interface IdeaService {

	/**
	 * Populates a mind map data structure from an {@link Idea} using the
	 * {@link MindmapNode} interface.
	 * 
	 * @param idea
	 *            The {@link Idea} object.
	 * @param root
	 *            The root of the mind map tree.
	 */
	void buildMindMap(Idea idea, MindmapNode root);

	/**
	 * Finds an {@link Idea} by ID.
	 * 
	 * @param id
	 *            The Idea object's ID.
	 * @return An Idea object.
	 */
	Idea findIdea(Integer id);

	/**
	 * Finds a list of {@link Idea} objects.
	 * 
	 * @return A List of Idea objects.
	 */
	List<Idea> findIdeas();

	/**
	 * Saves an {@link Idea} object.
	 * 
	 * @param newIdea
	 *            The Idea to save.
	 * @return The saved Idea.
	 */
	Idea saveIdea(Idea newIdea);
}
