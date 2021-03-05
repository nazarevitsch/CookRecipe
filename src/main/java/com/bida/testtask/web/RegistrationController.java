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
        model.addAttribute("error", null);
        return "registration";
    }

    @PostMapping("/registration")
    public String registerNewUser(@ModelAttribute UserRegistrationDTO userDTO, Model model){
        User existedUser = userService.findUserByEmail(userDTO.getEmail());
        if (existedUser != null){
            model.addAttribute("user", new UserRegistrationDTO());
            model.addAttribute("error", "This user is already existed!");
            return "registration";
        }
        if (userService.save(userDTO) == null){
            model.addAttribute("user", new UserRegistrationDTO());
            model.addAttribute("error", "Email or Password are unacceptable!");
            return "registration";
        }
        return "login";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }
}
