package com.bida.testtask.web;

import com.bida.testtask.aws.WorkWithAWS;
import com.bida.testtask.domain.Recipe;
import com.bida.testtask.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private WorkWithAWS workWithAWS;

    @GetMapping("/recipes")
    public String getRecipesPage(Model model){
        model.addAttribute("recipes", recipeService.getAllRecipes());
        return "recipes";
    }

    @GetMapping("/recipe")
    public String getRecipePage(@RequestParam Long id, Model model){
        System.out.println(id);
        System.out.println(recipeService.getRecipeById(id));
        model.addAttribute("recipe", recipeService.getRecipeById(id));
        return "recipe";
    }

    @GetMapping("/add_recipe")
    public String getRecipeAddPage(Model model){
        model.addAttribute("recipes", recipeService.getAllRecipes());
        return "add_recipe";
    }

    @PostMapping("/add_recipe_image")
    public ResponseEntity addRecipeImage(@RequestParam("image") MultipartFile file, @RequestParam("id") Long id) {
        System.out.println(id);
        try {
            String link = workWithAWS.uploadFile(file.getName(), file.getInputStream());
            System.out.println(link);
            recipeService.setImageLinkById(id, link);
        } catch (Exception e){
            System.out.println("ERROR");
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/add_recipe")
    @ResponseBody
    public String addRecipe(@RequestBody Recipe recipe){
        System.out.println(recipe);
        return String.valueOf(recipeService.save(recipe).getId());
    }

    @GetMapping("/fork_recipe")
    public String getForkRecipe(@RequestParam Long id, Model model){
        System.out.println(id);
        model.addAttribute("recipe", recipeService.getRecipeById(id));
        return "fork_recipe";
    }
}
