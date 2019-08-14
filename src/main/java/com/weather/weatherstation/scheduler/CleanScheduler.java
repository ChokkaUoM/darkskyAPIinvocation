package com.weather.weatherstation.scheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.weather.weatherstation.model.WeatherData;
import com.weather.weatherstation.service.WeatherService;

@Component
public class CleanScheduler {
	
	private static final Logger LOG = LoggerFactory.getLogger(CleanScheduler.class);
	private static final String pattern = "yyyy-MM-dd";
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	
	@Autowired
	private WeatherService weatherService;
	
	//Clean data after 3 days
	@Scheduled(fixedDelay = 259200000)
	public void cleanData(){
		LOG.info("Cleaning data after 3 days");
		
		List<WeatherData> allData = weatherService.getAllWeatherReports();
		String currentDateStr = simpleDateFormat.format(new Date());
		Date currentDate;
		try {
			currentDate = simpleDateFormat.parse(currentDateStr);
			DateTime dt1 = new DateTime(currentDate);
			
			allData.stream().forEach(d->{
				Date date;
				try {
					date = simpleDateFormat.parse(d.getTimestamp());
					DateTime dt2 = new DateTime(date);
					if(Days.daysBetween(dt1, dt2).getDays() >= 3){
						weatherService.deleteWeatherData(d.getId());
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					LOG.error("Exception occurred while cleaning data", e);
				}
			
			});
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			LOG.error("Exception occurred while cleaning data", e1);
		}
		
		
	}

}
