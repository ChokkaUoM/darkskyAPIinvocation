package com.weather.weatherstation.service;

import java.util.List;

import com.weather.weatherstation.model.WeatherData;

public interface WeatherService {
	
	public List<WeatherData> getAllWeatherReports(); 
	public WeatherData saveWeatherData(WeatherData weatherData);
	public WeatherData findWeatherData(String id);
	public void deleteWeatherData(String id);

}
