package com.example.demo.taco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
public class HomeController implements WebMvcConfigurer{

	
	 @GetMapping("/") 
	 public String home() { 
		 return "Home"; 
	 }
	 
	
	// the below method can be added to the bootstrap TacoCloudApplication as well, but keep it clean, is better practice to use here
	// this should be used when view controller is not making any operations on the model class, and is only forwarding teh view.
	/*
	 * @Override public void addViewControllers(ViewControllerRegistry registry) {
	 * registry.addViewController("/").setViewName("TacoHome"); }
	 */
}
