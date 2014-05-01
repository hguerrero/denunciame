package com.finders.rules;

import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.RateLimitStatus;

public class MockPlace implements Place {

	private transient RateLimitStatus rateLimitStatus;
	private transient int accessLevel;
	private String name;
	private String streetAddress;
	private String countryCode;
	private String id;
	private String country;
	private String placeType;
	private String url;
	private String fullName;
	private String boundingBoxType;
	private GeoLocation[][] boundingBoxCoordinates;
	private GeoLocation[][] geometryCoordinates;
	private String geometryType;
	private Place[] containedWithIn;

	private static final long serialVersionUID = -3446371015865742393L;
	
	public int compareTo(Place o) {
		return this.id.compareTo(o.getId());
	}

	public RateLimitStatus getRateLimitStatus() {
		return rateLimitStatus;
	}

	public void setRateLimitStatus(RateLimitStatus rateLimitStatus) {
		this.rateLimitStatus = rateLimitStatus;
	}

	public int getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPlaceType() {
		return placeType;
	}

	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}

	public String getURL() {
		return url;
	}

	public void setURL(String url) {
		this.url = url;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getBoundingBoxType() {
		return boundingBoxType;
	}

	public void setBoundingBoxType(String boundingBoxType) {
		this.boundingBoxType = boundingBoxType;
	}

	public GeoLocation[][] getBoundingBoxCoordinates() {
		return boundingBoxCoordinates;
	}

	public void setBoundingBoxCoordinates(GeoLocation[][] boundingBoxCoordinates) {
		this.boundingBoxCoordinates = boundingBoxCoordinates;
	}

	public GeoLocation[][] getGeometryCoordinates() {
		return geometryCoordinates;
	}

	public void setGeometryCoordinates(GeoLocation[][] geometryCoordinates) {
		this.geometryCoordinates = geometryCoordinates;
	}

	public String getGeometryType() {
		return geometryType;
	}

	public void setGeometryType(String geometryType) {
		this.geometryType = geometryType;
	}

	public Place[] getContainedWithIn() {
		return containedWithIn;
	}

	public void setContainedWithIn(Place[] containedWithIn) {
		this.containedWithIn = containedWithIn;
	}
}
