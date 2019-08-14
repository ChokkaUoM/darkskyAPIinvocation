package com.weather.weatherstation.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.weather.weatherstation.model.WeatherData;

@Repository
public class WeatherDaoImpl implements WeatherDao {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public List<WeatherData> getAllWeatherReports(){
		
		return mongoTemplate.findAll(WeatherData.class);
	}
	
	@Override
	public WeatherData saveWeatherData(WeatherData weatherData){
		mongoTemplate.save(weatherData);
		
		return weatherData;
	}
	
	@Override
	public WeatherData findWeatherData(String id){
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		return mongoTemplate.findOne(query, WeatherData.class);
	}
	
	@Override
	public void deleteWeatherData(String id){
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		WeatherData data = mongoTemplate.findOne(query, WeatherData.class);
		mongoTemplate.remove(data);
	}
		

}
