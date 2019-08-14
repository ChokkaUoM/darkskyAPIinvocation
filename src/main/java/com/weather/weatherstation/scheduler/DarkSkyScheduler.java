package com.weather.weatherstation.scheduler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.weather.weatherstation.model.WeatherData;
import com.weather.weatherstation.service.WeatherService;
import com.weather.weatherstation.util.IdGenerator;

@Component
public class DarkSkyScheduler {
	
	private static final Logger LOG = LoggerFactory.getLogger(DarkSkyScheduler.class);
	private static final String pattern = "yyyy-MM-dd";
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	
	@Value("${secret.key}")
	private String secretKey;	
	
	@Value("${darksky.url}")
	private String darkSkyURL;
	
	@Value("${ca.campbell.latitude}")
	private String campbelLatitude;
	
	@Value("${ca.campbell.longitude}")
	private String campbelLongitude;
	
	@Value("${ne.omaha.latitude}")
	private String omahaLatitude;
	
	@Value("${ne.omaha.longitude}")
	private String omahaLongitude;
	
	@Value("${tx.austin.latitude}")
	private String austinLatitude;
	
	@Value("${tx.austin.longitude}")
	private String austinLongitude;
	
	@Value("${japan.niseko.latitude}")
	private String nisekaLatitude;
	
	@Value("${japan.niseko.longitude}")
	private String nisekaLongitude;
	
	@Value("${japan.nara.latitude}")
	private String naraLatitude;
	
	@Value("${japan.nara.longitude}")
	private String naraLongitude;
	
	@Value("${indonesia.jakarta.latitude}")
	private String jakartaLatitude;
	
	@Value("${indonesia.jakarta.longitude}")
	private String jakartaLongitude;
	
	@Autowired
	private WeatherService weatherService;
	
	
	//Daily execute the scheduler
	@Scheduled(fixedRate = 86400000)
    public void reportCurrentTime() {
        LOG.info("Scheduler is ruuning on -{}", new Date().toString());
        
        String currentDate = simpleDateFormat.format(new Date());
        
        checkAndStore(IdGenerator.generateId(campbelLatitude, campbelLongitude), "CA", "Cambel", currentDate);
        checkAndStore(IdGenerator.generateId(omahaLatitude, omahaLongitude), "NE", "Omaha", currentDate);
        checkAndStore(IdGenerator.generateId(austinLatitude, austinLongitude), "TX", "Austin", currentDate);
        checkAndStore(IdGenerator.generateId(nisekaLatitude, nisekaLongitude), "Japan", "Niseko", currentDate);
        checkAndStore(IdGenerator.generateId(naraLatitude, naraLongitude), "Japan", "Nara", currentDate);
        checkAndStore(IdGenerator.generateId(jakartaLatitude, jakartaLongitude), "Indonesia", "Jakarta", currentDate);
        
    }
	
	private void checkAndStore(String id, String country, String city, String currentDate){
		
		WeatherData oldWeatherData = weatherService.findWeatherData(id); 
		if( oldWeatherData != null){
			if(currentDate.equals(oldWeatherData.getTimestamp())){
				LOG.info("WeatherData already exist. No need to call darksky API");
			}else{
				LOG.info("Update - Darksky API is calling for id-{}, date-{}", id, currentDate);
				storeWeatherData(callDarkSkyAPI(campbelLatitude, campbelLongitude), id, country, city, currentDate);
			}
		}else{
			LOG.info("Insert - Darksky API is calling for id-{}, date-{}", id, currentDate);
			storeWeatherData(callDarkSkyAPI(campbelLatitude, campbelLongitude), id, country, city, currentDate);
		}
	}
	
	private void storeWeatherData(String jsonString, String id,  String country, String city, String currentDate){
		
		if(StringUtils.isEmpty(jsonString)){
        	LOG.error("Received Darksky API response is empty for country-{}, city-{}",
        			country, city);
        }else{
        	WeatherData weatherData = new Gson().fromJson(jsonString, WeatherData.class);
        
        	if(weatherData != null && weatherData.getCurrently() != null){
        		LOG.info("Received temperature-{}, pressure-{}, wind speed-{} for country-{}, city-{}", 
            			weatherData.getCurrently().getTemperature(), weatherData.getCurrently().getPressure(),
            			weatherData.getCurrently().getWindSpeed(), country, city);
        		
	
        		weatherData.setId(id);
        		weatherData.setCountry(country);
        		weatherData.setCity(city);
        		weatherData.setTimestamp(currentDate);
        		
        		weatherService.saveWeatherData(weatherData);
        		
        		
        	}else{
        		LOG.error("Error in receiving weatherData-{}", weatherData);
        	}
        	
        }
        
	}
	
	public String callDarkSkyAPI(String latitude, String longitude){
		URL url;
		try {
			url = new URL(darkSkyURL + secretKey +"/" + latitude+ "," + longitude);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			int status = con.getResponseCode();
			
			if(status  == HttpURLConnection.HTTP_OK){
				
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer content = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
				    content.append(inputLine);
				}
				in.close();
				
				LOG.info("Receiveed Response with status code-{} and content-{}", status, content);
				
				return content.toString();
				
			}else{
				LOG.error("Invalid response received for latitude-{}, longitude-{} with statusCode-{}",
						latitude, longitude, status);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
		
		
	}
	
	

}
