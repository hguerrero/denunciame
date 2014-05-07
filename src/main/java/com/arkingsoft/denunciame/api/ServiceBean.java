package com.arkingsoft.denunciame.api;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import com.arkingsoft.denunciame.model.Incident;
import com.arkingsoft.denunciame.model.Type;

@Stateless
public class ServiceBean implements Service
{
	@PersistenceContext(unitName = "denunciame-pu")
	private EntityManager manager;
	
	@Inject
	private Logger log;
	
	public List<Incident> listIncidents() 
	{
		CriteriaQuery<Incident> cq = manager.getCriteriaBuilder()
				.createQuery(Incident.class);
		
		Root<Incident> pet = cq.from(Incident.class);
		
		cq.select(pet);
		
		TypedQuery<Incident> q = manager.createQuery(cq);
		
		List<Incident> reports = q.getResultList();
		
		log.info("total reports: " + reports.size());
		
		return reports;
	}

	public Incident report(Incident incident) 
	{
		incident.setDateReported(new Date());
		
		manager.persist(incident);
		
		log.info("Report filed!");
		
		return incident;
	}

	@Override
	public Incident listIncident(Long id) 
	{
		Incident incident = manager.find(Incident.class, id);
		
		log.info("Incident loaded with id: " + id);
		
		return incident;
	}

	@Override
	public List<Type> listTypes() 
	{
		TypedQuery<Type> q = manager.createQuery(
				"select t from com.arkingsoft.denunciame.model.Type t where t.status = :status order by t.description asc", 
				Type.class);
		
		q.setParameter("status", 1);
		
		List<Type> types = q.getResultList();
		
		log.info("total types: " + types.size());
		
		return types;
	}

	@Override
	public Type listType(Long id) 
	{
		Type type = manager.find(Type.class, id);
		
		log.info("Type loaded with id: " + id);
		
		return type;
	}

	@Override
	public List<Type> listTypes(String text) 
	{
		TypedQuery<Type> q = manager.createQuery(
				"select t from com.arkingsoft.denunciame.model.Type t where t.status = :status and upper(t.description) like :text  order by t.description asc", 
				Type.class);
		
		q.setParameter("status", 1);
		q.setParameter("text", "%" + text.toUpperCase() + "%");
		
		List<Type> types = q.getResultList();
		
		log.info("total types: " + types.size());
		
		return types;
	}


}