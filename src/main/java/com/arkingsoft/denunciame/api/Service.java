package com.arkingsoft.denunciame.api;

import java.util.List;

import javax.ejb.Local;

import com.arkingsoft.denunciame.model.Incident;
import com.arkingsoft.denunciame.model.Type;

@Local
public interface Service {

	public List<Incident> listIncidents();

	public Incident report(Incident incident);

	public Incident listIncident(Long id);

	public List<Type> listTypes();

	public Type listType(Long id);

	public List<Type> listTypes(String text);

}
