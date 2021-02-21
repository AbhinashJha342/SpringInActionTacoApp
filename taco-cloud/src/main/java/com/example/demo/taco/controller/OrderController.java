package com.example.demo.taco.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.example.demo.taco.data.impl.JdbcOrderRepositoryImpl;
import com.example.demo.taco.model.Order;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@SessionAttributes("order")
@RequestMapping("/orders")
public class OrderController {
	
	private JdbcOrderRepositoryImpl jdbcOrderRepo;
	
	public OrderController(JdbcOrderRepositoryImpl jdbcOrderRepo) {
		this.jdbcOrderRepo = jdbcOrderRepo;
	}

	@GetMapping("/current")
	public String orderForm(Model model) {
		//model.addAttribute("order", new Order());
		return "orderForm";
	}
	
	@PostMapping
	public String processOrder(@Valid Order order, Errors error, SessionStatus sessionStatus) {
		if(error.hasErrors()) {
			return "orderForm";
		}
		jdbcOrderRepo.saveOrder(order);
		sessionStatus.setComplete();
		// log.info("Order submitted: " + order);
	  return "OrderConfirmation";
	}
}
