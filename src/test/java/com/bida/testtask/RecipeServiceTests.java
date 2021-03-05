package com.bida.testtask;

import com.bida.testtask.domain.Recipe;
import com.bida.testtask.domain.User;
import com.bida.testtask.service.RecipeService;
import com.bida.testtask.service.UserService;
import com.bida.testtask.service.dto.UserRegistrationDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@Transactional
class RecipeServiceTests {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private UserService userService;

    @Test
    void addRecipe() {
        Recipe recipe = new Recipe();
        recipe.setName("Test 1");
        recipe.setDescription("Test 1");
        User testUser = createTestUser();
        Recipe resultRecipe = recipeService.save(recipe, testUser.getEmail());

        Assertions.assertEquals(recipe.getName(), resultRecipe.getName());
        Assertions.assertEquals(recipe.getDescription(), resultRecipe.getDescription());
        Assertions.assertEquals(recipe.getUserId(), testUser.getId());
    }

    @Test
    void addRecipeByUserId() {
        List<Recipe> recipes = new ArrayList<>();
        User testUser = createTestUser();
        for (int i = 0; i < 5; i++) {
            Recipe recipe = new Recipe();
            recipe.setName("Test " + i);
            recipe.setDescription("Test " + i);
            recipeService.save(recipe, testUser.getEmail());
            recipes.add(recipe);
        }
        List<Recipe> resultRecipes = recipeService.getAllRecipeByUserEmail(testUser.getEmail());
        for (Recipe r : recipes) {
            Assertions.assertTrue(resultRecipes.contains(r));
        }
    }

    @Test
    void addRecipeAndFindIt() {
        Recipe recipe = new Recipe();
        recipe.setName("Test 1");
        recipe.setDescription("Test 1");
        User testUser = createTestUser();
        Recipe savedRecipe = recipeService.save(recipe, testUser.getEmail());
        Recipe resultRecipe = recipeService.getRecipeById(savedRecipe.getId());

        Assertions.assertEquals(savedRecipe.getName(), resultRecipe.getName());
        Assertions.assertEquals(savedRecipe.getDescription(), resultRecipe.getDescription());
        Assertions.assertEquals(savedRecipe.getUserId(), testUser.getId());
    }

    @Test
    void addRecipeAndUpdateItsImageLink() {
        String link = "https://parents-shop.s3.eu-central-1.amazonaws.com/image";
        Recipe recipe = new Recipe();
        recipe.setName("Test 1");
        recipe.setDescription("Test 1");
        User testUser = createTestUser();

        Recipe savedRecipe = recipeService.save(recipe, testUser.getEmail());
        recipeService.setImageLinkById(savedRecipe.getId(), link, testUser.getEmail());

        System.out.println(savedRecipe);
        Recipe resultRecipe = recipeService.getRecipeById(savedRecipe.getId());
        System.out.println(resultRecipe);


        Assertions.assertEquals(savedRecipe.getName(), resultRecipe.getName());
        Assertions.assertEquals(savedRecipe.getDescription(), resultRecipe.getDescription());
        Assertions.assertEquals(savedRecipe.getUserId(), testUser.getId());
        Assertions.assertEquals(link, resultRecipe.getImageLink());
    }

    @Test
    void addRecipeAndUpdateItsImageDescriptionAndName() {
        Recipe recipe = new Recipe();
        recipe.setName("Test 1");
        recipe.setDescription("Test 1");
        User testUser = createTestUser();

        Recipe savedRecipe = recipeService.save(recipe, testUser.getEmail());

        savedRecipe.setName("Test 2");
        savedRecipe.setDescription("Test 2");
        recipeService.updateRecipeNameAndDescriptionByUserEmail(savedRecipe, testUser.getEmail());

        Recipe resultRecipe = recipeService.getRecipeById(savedRecipe.getId());

        Assertions.assertEquals(savedRecipe.getName(), resultRecipe.getName());
        Assertions.assertEquals(savedRecipe.getDescription(), resultRecipe.getDescription());
        Assertions.assertEquals(savedRecipe.getUserId(), testUser.getId());
    }

    @Test
    void checkIfRecipesFromDBAreSorted() {
        List<Recipe> recipes = recipeService.getAllRecipesSortedByName();
        Assertions.assertTrue(isListSorted(recipes));
    }

    User createTestUser() {
        UserRegistrationDTO testUser = new UserRegistrationDTO();
        testUser.setEmail("test@gmail.com");
        testUser.setPassword("test1234567890");
        testUser.setName("Test");
        return userService.save(testUser);
    }

    boolean isListSorted(List<Recipe> recipes) {
        for (int i = 0; i < recipes.size() - 1; ++i) {
            if (recipes.get(i).compareTo(recipes.get(i + 1)) > 0)
                return false;
        }
        return true;
    }
}