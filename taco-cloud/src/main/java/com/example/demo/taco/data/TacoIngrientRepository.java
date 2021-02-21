package com.example.demo.taco.data;

import com.example.demo.taco.model.Ingredient;

public interface TacoIngrientRepository {

	Iterable<Ingredient> findAll();
	Ingredient findOne(String id);
	Ingredient save(Ingredient ingridient);
}
