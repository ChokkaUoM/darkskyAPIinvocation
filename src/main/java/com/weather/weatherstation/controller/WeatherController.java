package com.weather.weatherstation.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.weather.weatherstation.model.WeatherData;
import com.weather.weatherstation.service.WeatherService;

@RestController
@RequestMapping(value = "/data")
public class WeatherController {
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	private WeatherService weatherService;
	
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public WeatherData addNewUsers(@RequestBody WeatherData weatherData) {
		LOG.info("Saving weatherData");
		return weatherService.saveWeatherData(weatherData);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<WeatherData> getAll() {
		LOG.info("Getting all weather data.");
		return weatherService.getAllWeatherReports();
	}
	
	

}
