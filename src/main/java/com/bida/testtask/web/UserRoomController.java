package com.bida.testtask.web;

import com.bida.testtask.service.RecipeService;
import com.bida.testtask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserRoomController {

    @Autowired
    private UserService userService;

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/user_room")
    public String getUserRoomPage(Model model, Principal principal){
        model.addAttribute("user", userService.findUserByEmail(principal.getName()));
        model.addAttribute("recipes", recipeService.getAllRecipeByUserEmail(principal.getName()));
        return "user_room";
    }
}
