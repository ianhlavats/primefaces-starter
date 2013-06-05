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

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.mycompany.model.Content;
import com.mycompany.service.ContentService;
import com.mycompany.util.Queries;

/**
 * Content service implementation class.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
@Named("contentService")
@Stateless
public class ContentServiceImpl extends AbstractService implements ContentService {

	@Override
	public Content findContentByName(String name) {
		Content found = null;
		try {
			TypedQuery<Content> query = em.createNamedQuery(Queries.CONTENT_FIND_BY_NAME,
					Content.class);
			query.setParameter(1, name);
			found = query.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No content found: {}", name);
		}
		return found;
	}

	@Override
	public Content saveContent(Content content) {
		Content saved = content;
		if (content.getVersion() == null) {
			em.persist(content);
		} else {
			saved = em.merge(content);
		}
		return saved;
	}

}
