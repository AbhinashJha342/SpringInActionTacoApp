package com.example.demo.taco.data.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.taco.data.TacoIngrientRepository;
import com.example.demo.taco.model.Ingredient;

@Repository
public class TacoIngrientRepositoryImpl implements TacoIngrientRepository{

	private JdbcTemplate jdbcTemplate;
	
	public TacoIngrientRepositoryImpl(JdbcTemplate jdbc) {
		this.jdbcTemplate = jdbc;
	}
	
	@Override
	public Iterable<Ingredient> findAll() {
		return jdbcTemplate.query("select * from Ingredient", this::mapIngredients);
	}

	@Override
	public Ingredient findOne(String id) {
		return this.jdbcTemplate.queryForObject("select id, name and type from Ingredient where id=?", this::mapIngredients, id);
	}
	
	private Ingredient mapIngredients(ResultSet rs, int rowNum) throws SQLException {
		return new Ingredient(rs.getString("id"),
				rs.getString("name"),
				Ingredient.Type.valueOf(rs.getString("type"))
				);
	}

	@Override
	public Ingredient save(Ingredient ingridient) {
		jdbcTemplate.update("insert into Ingredients(id, name, type) values (?,?,?)", 
				ingridient.getId(),
				ingridient.getName(),
				ingridient.getType().toString());
		return ingridient;
	}

}
