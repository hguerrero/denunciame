package com.finders.api;

import java.util.List;

import javax.ejb.Local;

import com.finders.model.Report;

@Local
public interface Service {

	public List<Report> listReports();

	public void file(Report report);

}
