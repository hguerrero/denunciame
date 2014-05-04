package com.arkingsoft.denunciame.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Incident implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Type type;
	@Embedded
	private Location location;
	private Date dateReported;
	private Date dateIncident;
	private String description;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public Date getDateReported() {
		return dateReported;
	}
	public void setDateReported(Date dateReported) {
		this.dateReported = dateReported;
	}
	public Date getDateIncident() {
		return dateIncident;
	}
	public void setDateIncident(Date dateIncident) {
		this.dateIncident = dateIncident;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
