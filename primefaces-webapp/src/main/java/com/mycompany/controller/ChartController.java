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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.persistence.EntityManager;

import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

import com.mycompany.model.Gender;
import com.mycompany.model.User;
import com.mycompany.service.ChartService;
import com.mycompany.service.UserService;

/**
 * Controller class for charts.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
@ManagedBean
@ViewScoped
public class ChartController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1999623701433215717L;

	private CartesianChartModel barChartModel;

	@ManagedProperty(value = "#{chartService}")
	private ChartService chartService;

	@ManagedProperty(value = "#{userService}")
	private UserService userService;

	private PieChartModel pieChartModel;

	private ChartSeries buildChartSeries(List<Object[]> results, String label) {
		ChartSeries series = new ChartSeries();
		series.setLabel(label);
		// calculate monthly totals
		SimpleDateFormat sdf = new SimpleDateFormat("MMM/yy");
		List<String> months = new ArrayList<String>();
		Map<String, Long> monthTotals = new HashMap<String, Long>();
		for (Object[] result : results) {
			Date date = (Date) result[0];
			Long count = (Long) result[1];
			String month = sdf.format(date);
			if (!months.contains(month)) {
				months.add(month);
			}
			Long total = monthTotals.get(month);
			if (total == null) {
				total = 0L;
			}
			monthTotals.put(month, count + total);
		}
		// add the data to the chart series
		for (String month : months) {
			series.set(month, monthTotals.get(month).intValue());
		}
		return series;
	}

	private void createBarChartModel() {
		List<Object[]> maleResults = chartService.findEventAttendanceByGender(Gender.MALE);
		List<Object[]> femaleResults = chartService.findEventAttendanceByGender(Gender.FEMALE);
		if (maleResults.isEmpty() && femaleResults.isEmpty()) {
			return;
		}
		barChartModel = new CartesianChartModel();
		ChartSeries males = buildChartSeries(maleResults, "Male");
		ChartSeries females = buildChartSeries(femaleResults, "Female");
		barChartModel.addSeries(males);
		barChartModel.addSeries(females);
	}

	private void createPieModel() {
		pieChartModel = new PieChartModel();
		List<User> users = userService.findUsers();
		for (User user : users) {
			Integer count = user.getClickCount();
			if (count == null) {
				count = 0;
			}
			String username = user.getUsername();
			pieChartModel.getData().put(username, count);
		}
	}

	public CartesianChartModel getBarChartModel() {
		return barChartModel;
	}

	/**
	 * This method builds a {@link PieChartModel} of active user click counts.
	 * 
	 * @return A PieChartModel.
	 */
	public PieChartModel getLivePieModel() {
		return pieChartModel;
	}

	public PieChartModel getPieChartModel() {
		return pieChartModel;
	}

	/**
	 * Initializes the pie chart and bar chart models from data in our database.
	 * We can't do this in the constructor because the {@link EntityManager} is
	 * injected after the object is constructed, so this method handles the
	 * initialization during the pre-render view event.
	 * 
	 * @param event
	 *            The view event.
	 */
	public void init(ComponentSystemEvent event) {
		createBarChartModel();
		createPieModel();
	}

	public void itemSelect(ItemSelectEvent event) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected",
				"Item Index: " + event.getItemIndex() + ", Series Index:" + event.getSeriesIndex());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void setChartService(ChartService chartService) {
		this.chartService = chartService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
