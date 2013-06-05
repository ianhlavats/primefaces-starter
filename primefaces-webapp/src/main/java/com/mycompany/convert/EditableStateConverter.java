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
package com.mycompany.convert;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mycompany.model.Country;
import com.mycompany.model.ProvinceState;
import com.mycompany.service.CountryService;
import com.mycompany.util.FacesUtils;

/**
 * Converter object for editable {@link ProvinceState} objects.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
@FacesConverter(forClass = ProvinceState.class,
				value = "mycompany.EditableStateConverter")
public class EditableStateConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null) {
			return null;
		}
		ProvinceState state = null;
		UIParameter param = (UIParameter) component.findComponent("countryParam");
		if (param != null) {
			// lookup existing state by country and name
			Country country = (Country) param.getValue();
			CountryService service = (CountryService) FacesUtils.getManagedBean("countryService");
			state = service.findStateByName(country, value);
			if (state == null) {
				// create new state if it does not exist
				state = service.createProvinceState(country, value);
			}
		}
		return state;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value instanceof ProvinceState) {
			return ((ProvinceState) value).getName();
		} else if (value instanceof String) {
			return ((String) value);
		}
		return null;
	}
}
