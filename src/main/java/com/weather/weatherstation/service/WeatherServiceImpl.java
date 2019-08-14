package com.weather.weatherstation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weather.weatherstation.dao.WeatherDao;
import com.weather.weatherstation.model.WeatherData;

@Service
public class WeatherServiceImpl implements WeatherService {
	
	@Autowired
	private WeatherDao weatherDao;

	@Override
	public List<WeatherData> getAllWeatherReports(){
		return weatherDao.getAllWeatherReports();
	}
	
	@Override
	public WeatherData saveWeatherData(WeatherData weatherData){
		return weatherDao.saveWeatherData(weatherData);
	}
	
	@Override
	public WeatherData findWeatherData(String id){
		return weatherDao.findWeatherData(id);
	}
	
	@Override
	public void deleteWeatherData(String id){
		weatherDao.deleteWeatherData(id);
	}
}
