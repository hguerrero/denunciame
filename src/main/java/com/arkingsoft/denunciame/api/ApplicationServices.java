package com.arkingsoft.denunciame.api;

import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.arkingsoft.denunciame.model.Incident;
import com.arkingsoft.denunciame.model.Type;

@Path("/services")
public class ApplicationServices {

	@EJB
	Service service;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/incident/{id}")
	public Incident incidents(@PathParam(value="id")Long id) 
	{
		return service.listIncident(id);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/types")
	public List<Type> types() 
	{
		return service.listTypes();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/type/{id:[0-9]*}")
	public Type listType(@PathParam(value="id")Long id) 
	{
		return service.listType(id);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/type/{text:[A-Za-z]*}")
	public List<Type> listTypes(@PathParam(value="text")String text) 
	{
		return service.listTypes(text);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/incidents/within")
	public Response incidents(@WebParam(name="lat")Long lat, @WebParam(name="lng")Long lng) 
	{

		return Response.ok().build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/incidents")
	public List<Incident> incidents() 
	{
		return service.listIncidents();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/report")
	public Response report(Incident incident) 
	{
		incident = service.report(incident);

		return Response.ok(incident, MediaType.APPLICATION_JSON).build();
	}
}