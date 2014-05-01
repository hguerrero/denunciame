package com.finders.model;

import java.io.Serializable;

public class Type implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String description;
	private int status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

}
