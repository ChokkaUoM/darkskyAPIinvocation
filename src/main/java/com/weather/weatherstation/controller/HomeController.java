package com.weather.weatherstation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.weather.weatherstation.service.WeatherService;

@Controller
public class HomeController {
	
	@Value("${spring.application.name}")
    String appName;
	
	@Autowired
	private WeatherService weatherService;
 
    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        model.addAttribute("reports", weatherService.getAllWeatherReports());
        return "home";
    }

}
