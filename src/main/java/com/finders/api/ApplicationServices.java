package com.finders.api;

import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.finders.model.Report;

@Path("/services")
public class ApplicationServices {

	@EJB
	Service service;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/reports/within")
	public Response reports(@WebParam(name="lat")Long lat, @WebParam(name="lng")Long lng) 
	{

		return Response.ok().build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/reports")
	public List<Report> reports() 
	{
		return service.listReports();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/file")
	public Response file(Report report) 
	{
		service.file(report);

		return Response.ok().build();
	}
}