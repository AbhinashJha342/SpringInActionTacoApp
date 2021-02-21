package com.example.demo.taco.model.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.taco.data.TacoIngrientRepository;
import com.example.demo.taco.model.Ingredient;


@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

private TacoIngrientRepository ingredientRepo;
private final List<Ingredient> ingredients = new ArrayList<>();

@Autowired
public IngredientByIdConverter(TacoIngrientRepository ingredientRepo) {
    this.ingredientRepo = ingredientRepo;

    ingredientRepo.findAll().forEach(ingredients::add);
}

@Override
public Ingredient convert(String ingredientId) {
    return ingredients
            .stream().filter( i -> i.getId().equals(ingredientId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Ingredient with ID '" + ingredientId + "' not found!"));
}

}
