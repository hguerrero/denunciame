package com.finders.model;

import java.io.Serializable;

public class Location implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	private long lat;
	private long lng;
	
	public long getLat() {
		return lat;
	}
	public void setLat(long lat) {
		this.lat = lat;
	}
	public long getLng() {
		return lng;
	}
	public void setLng(long lng) {
		this.lng = lng;
	}

}
