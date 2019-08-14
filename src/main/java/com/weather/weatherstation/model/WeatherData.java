package com.weather.weatherstation.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class WeatherData {
	
	@Id
	String id;
	String latitude;
	String longitude;
	String timezone;
	CurrentData currently;
	String country;
	String city;
	String timestamp;
	
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public CurrentData getCurrently() {
		return currently;
	}
	public void setCurrently(CurrentData currently) {
		this.currently = currently;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	
	

}
