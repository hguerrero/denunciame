package com.finders.api;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.finders.model.Report;

@Stateless
public class ServiceBean implements Service
{
	@Inject
	private Logger log;
	
	public List<Report> listReports() 
	{
		List<Report> reports = new ArrayList<Report>();
		
		reports.add(new Report());
		
		log.info("total reports: " + reports.size());
		
		return reports;
	}

	public void file(Report report) {
		log.info("Report filed!");
	}


}