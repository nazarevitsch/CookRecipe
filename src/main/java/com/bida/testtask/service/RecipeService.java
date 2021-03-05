package com.bida.testtask.service;

import com.bida.testtask.domain.Recipe;
import com.bida.testtask.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserService userService;

    public Recipe save(Recipe recipe, String userEmail) {
        recipe.setUserId(userService.findUserByEmail(userEmail).getId());
        return recipeRepository.save(recipe);
    }

    public List<Recipe> getAllRecipeByUserEmail(String email){
        return recipeRepository.findAllByUserIdOrderByName(userService.findUserByEmail(email).getId());
    }

    public Recipe getRecipeById(Long id){
        return recipeRepository.getById(id);
    }

    public List<Recipe> getAllRecipesSortedByName(){
        return recipeRepository.findAllByOrderByName();
    }

    public void setImageLinkById(Long id, String link, String email){
        recipeRepository.setImageLinkByIdAndUserId(id, link, userService.findUserByEmail(email).getId());
    }

    public void updateRecipeNameAndDescriptionByUserEmail(Recipe recipe, String email){
        recipeRepository.updateRecipeNameAndDescriptionByIdAndUserId(recipe.getId(), recipe.getName(),
                recipe.getDescription(), userService.findUserByEmail(email).getId());
    }
}
