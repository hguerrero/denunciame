package com.arkingsoft.denunciame.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Location implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	private Double lat;
	private Double lng;
	private String location;
	
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

}
