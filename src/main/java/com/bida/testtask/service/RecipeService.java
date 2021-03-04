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

    @Autowired
    private UserService userService;

    public Recipe save(Recipe recipe, String userEmail) {
        recipe.setCreationDate(new Date(new java.util.Date().getTime()));
        recipe.setCreationTime(new Time(recipe.getCreationDate().getTime()));
        recipe.setUserId(userService.findUserByEmail(userEmail).getId());
        return recipeDAO.save(recipe);
    }

    public List<Recipe> getAllRecipeByUserEmail(String email){
        return recipeDAO.findAllByUserId(userService.findUserByEmail(email).getId());
    }

    public Recipe getRecipeById(Long id){
        return recipeDAO.getById(id);
    }

    public List<Recipe> getAllRecipes(){
        return recipeDAO.findAll();
    }

    public void setImageLinkById(Long id, String link, String email){
        recipeDAO.setImageLinkByIdAndUserId(id, link, userService.findUserByEmail(email).getId());
    }

    public void updateRecipeNameAndDescriptionByUserEmail(Recipe recipe, String email){
        recipeDAO.updateRecipeNameAndDescriptionByIdAndUserId(recipe.getId(), recipe.getName(),
                recipe.getDescription(), userService.findUserByEmail(email).getId());
    }
}
