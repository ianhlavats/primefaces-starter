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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.mycompany.util.Queries;

/**
 * JPA entity for country data.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = Queries.COUNTRY_FIND_BY_CODE,
							query = "select c from Country c where c.code = ?1"),
		@NamedQuery(name = Queries.COUNTRY_FIND_ALL,
					query = "select c from Country c order by c.name") })
public class Country extends AbstractEntity {

	private static final String[] excludeFields = new String[] { "provinceStates" };

	/**
	 * 
	 */
	private static final long serialVersionUID = -4925972235656394450L;

	private String code;

	private String name;

	private Set<ProvinceState> provinceStates = new HashSet<ProvinceState>();

	@Override
	public boolean equals(Object object) {
		return EqualsBuilder.reflectionEquals(this, object, excludeFields);
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	@OneToMany(	mappedBy = "country",
				fetch = FetchType.EAGER)
	public Set<ProvinceState> getProvinceStates() {
		return provinceStates;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, excludeFields);
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProvinceStates(Set<ProvinceState> states) {
		this.provinceStates = states;
	}

}
