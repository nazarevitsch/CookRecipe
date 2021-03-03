package com.bida.testtask.service;

import com.bida.testtask.domain.Recipe;
import com.bida.testtask.repository.RecipeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Service
public class RecipeService {

    @Autowired
    private RecipeDAO recipeDAO;

    public Recipe save(Recipe recipe) {
        recipe.setCreationDate(new Date(new java.util.Date().getTime()));
        recipe.setCreationTime(new Time(recipe.getCreationDate().getTime()));
        return recipeDAO.save(recipe);
    }

    public Recipe getRecipeById(Long id){
        return recipeDAO.getById(id);
    }

    public List<Recipe> getAllRecipes(){
        return recipeDAO.findAll();
    }

    public void setImageLinkById(Long id, String link){
        recipeDAO.setImageLinkById(id, link);
    }
}
