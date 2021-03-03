package com.bida.testtask.web;

import com.bida.testtask.domain.User;
import com.bida.testtask.service.UserService;
import com.bida.testtask.service.dto.UserRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String getRegistrationPage(Model model){
        model.addAttribute("user", new UserRegistrationDTO());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerNewUser(@ModelAttribute UserRegistrationDTO userDTO,
                                  BindingResult result){
        System.out.println(userDTO);
        User existedUser = userService.findUserByEmail(userDTO.getEmail());
        System.out.println(existedUser);
        if (existedUser != null){
            result.rejectValue("username", null, "There is already an account registered with that username");
        }
        userService.save(userDTO);
        return "index";
    }
}
