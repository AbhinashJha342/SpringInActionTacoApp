package com.example.demo.taco.data.impl;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.taco.data.TacoRepository;
import com.example.demo.taco.model.Ingredient;
import com.example.demo.taco.model.Taco;

@Repository
public class TacoRepositoryImpl implements TacoRepository{

	private JdbcTemplate template;
	
	@Autowired
	public TacoRepositoryImpl(JdbcTemplate template) {
		this.template = template;
	}
	
	@Override
	public Taco save(Taco taco) {
		long tacoId = saveTacoInfo(taco);
		taco.setId(tacoId);
	    for (Ingredient ingredient : taco.getIngredients()) {
	      saveIngredientToTaco(ingredient, tacoId);
	    }

	    return taco;
	  }

	private Long saveTacoInfo(Taco taco) {
		taco.setCreatedAt(new Date());
		PreparedStatementCreatorFactory pscFactory = new PreparedStatementCreatorFactory("insert into Taco (name, createdAt) values (?,?)", 
				Types.VARCHAR, Types.TIMESTAMP);
		// By default, returnGeneratedKeys = false so change it to true
		pscFactory.setReturnGeneratedKeys(true);
		PreparedStatementCreator preparedStatementCreator = pscFactory.newPreparedStatementCreator(Arrays.asList(taco.getName(), new Timestamp(taco.getCreatedAt().getTime())));
		KeyHolder key = new GeneratedKeyHolder();
		template.update(preparedStatementCreator, key);
		return key.getKey().longValue();
	}
	
	private void saveIngredientToTaco(
	          Ingredient ingredient, long tacoId) {
		template.update(
	        "insert into Taco_Ingredients (taco, ingredient) " +
	        "values (?, ?)",
	        tacoId, ingredient.getId());
	  }
}
