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
package com.mycompany.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * JPA entity for mind map data. This class is self-referencing and has a
 * hierarchical parent-child relationship so an idea can have any number of
 * sub-ideas.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
@Entity
public class Idea extends AbstractEntity {

	private static final String[] excludeFields = new String[] { "parent", "children" };

	/**
	 * 
	 */
	private static final long serialVersionUID = -5778367117092282684L;

	private User author;

	private Set<Idea> children = new HashSet<Idea>();

	private String description;

	private String name;

	private Idea parent;

	@Override
	public boolean equals(Object object) {
		return EqualsBuilder.reflectionEquals(this, object, excludeFields);
	}

	@ManyToOne
	public User getAuthor() {
		return author;
	}

	@OneToMany(	mappedBy = "parent",
				fetch = FetchType.LAZY)
	public Set<Idea> getChildren() {
		return children;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	@ManyToOne
	public Idea getParent() {
		return parent;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, excludeFields);
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public void setChildren(Set<Idea> children) {
		this.children = children;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParent(Idea parent) {
		this.parent = parent;
	}
}
