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

import java.security.Principal;

@Controller
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private WorkWithAWS workWithAWS;

    @GetMapping("/recipes")
    public String getRecipesPage(Model model){
        model.addAttribute("recipes", recipeService.getAllRecipesSortedByName());
        return "recipes";
    }

    @GetMapping("/recipe")
    public String getRecipePage(@RequestParam Long id, Model model){
        model.addAttribute("recipe", recipeService.getRecipeById(id));
        return "recipe";
    }

    @GetMapping("/add_recipe")
    public String getRecipeAddPage(Model model){
        return "add_recipe";
    }

    @PostMapping("/add_recipe_image")
    public ResponseEntity addRecipeImage(@RequestParam("image") MultipartFile file, @RequestParam("id") Long id, Principal principal) {
        try {
            String link = workWithAWS.uploadFile(file.getName(), file.getInputStream());
            recipeService.setImageLinkById(id, link, principal.getName());
        } catch (Exception e){
            System.out.println("ERROR");
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/add_recipe")
    @ResponseBody
    public String addRecipe(@RequestBody Recipe recipe, Principal principal){
        return String.valueOf(recipeService.save(recipe, principal.getName()).getId());
    }

    @GetMapping("/fork_recipe")
    public String getForkRecipePage(@RequestParam Long id, Model model){
        model.addAttribute("recipe", recipeService.getRecipeById(id));
        return "fork_recipe";
    }

    @GetMapping("/update_recipe")
    public String getUpdateRecipePage(@RequestParam Long id, Model model){
        model.addAttribute("recipe", recipeService.getRecipeById(id));
        return "update_recipe";
    }

    @PostMapping("/update_recipe")
    public ResponseEntity getForkRecipe(@RequestBody Recipe recipe, Principal principal){
        System.out.println(recipe);
        recipeService.updateRecipeNameAndDescriptionByUserEmail(recipe, principal.getName());
        return new ResponseEntity(HttpStatus.OK);
    }
}
