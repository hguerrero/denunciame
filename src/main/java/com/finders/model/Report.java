package com.finders.model;

import java.io.Serializable;

public class Report implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	private Type type;
	private Location location;
	
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
	
}
