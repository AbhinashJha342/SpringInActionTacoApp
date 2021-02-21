package com.example.demo.taco.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.taco.data.TacoIngrientRepository;
import com.example.demo.taco.data.TacoRepository;
import com.example.demo.taco.model.Ingredient;
import com.example.demo.taco.model.Ingredient.Type;
import com.example.demo.taco.model.Order;
import com.example.demo.taco.model.Taco;

import lombok.extern.slf4j.Slf4j;


@Slf4j    //is a Lombok annotation to generate slf4 loggers.
@Controller  //makes this class eligible for component scanning and will automatically create bean for this in Spring Application Context.
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {
	
	private TacoIngrientRepository ingredientRepo;
	private TacoRepository tacoRepo;
	
	@Autowired
	public DesignTacoController(TacoIngrientRepository ingredientRepo, TacoRepository tacoRepo) {
		this.ingredientRepo = ingredientRepo;
		this.tacoRepo = tacoRepo;
	}
	
	@ModelAttribute(name = "order")
	public Order order() {
		return new Order();
	}
	
	@ModelAttribute(name = "taco")
	public Taco taco() {
		return new Taco();
	}
	
	
	@GetMapping
	public String showDesignForm(Model model) {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		ingredientRepo.findAll().forEach(i -> ingredients.add(i));
		Type[] types = Ingredient.Type.values();
		for (Type type : types) {
			model.addAttribute(type.toString().toLowerCase(), 
			filterByType(ingredients, type));    
			}
		//the variable against which the model class is stored, the same should be used 
		model.addAttribute("tacodesign", new Taco());
		// Set the template name to the appropriate value
        // Generally speaking, it should match a file name in the
        // templates directory, without the extension
		return "TacoHome";  
		}

	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());  
		}
	
	@PostMapping
	public String processDesign(@Valid Taco design, @ModelAttribute Order order, Errors error) {
		if(error.hasErrors()) {
			return "TacoHome";
		}
		Taco saved = tacoRepo.save(design);
		List<Taco> savedtaco = order.addDesign(saved);
		order.setTacos(savedtaco);
	  //"redirect::- the user’s browser should be redirected to the relative path /order/current.
	  return "redirect:/orders/current";
	}

	public TacoIngrientRepository getIngredientRepo() {
		return ingredientRepo;
	}

	public void setIngredientRepo(TacoIngrientRepository ingredientRepo) {
		this.ingredientRepo = ingredientRepo;
	}

	public TacoRepository getTacoRepo() {
		return tacoRepo;
	}

	public void setTacoRepo(TacoRepository tacoRepo) {
		this.tacoRepo = tacoRepo;
	}
	}