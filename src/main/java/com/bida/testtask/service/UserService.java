package com.bida.testtask.service;

import com.bida.testtask.domain.User;
import com.bida.testtask.repository.UserRepository;
import com.bida.testtask.service.dto.MyUserDetails;
import com.bida.testtask.service.dto.UserRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User save(UserRegistrationDTO userDTO){
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getName());
        if (checkAcceptableUserData(user)) {
            userRepository.save(user);
            return user;
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = findUserByEmail(s);
        if (user == null){
            throw new UsernameNotFoundException(s);
        }
        return new MyUserDetails(user);
    }

    public boolean checkAcceptableUserData(User user){
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        Matcher emailMatcher = emailPattern.matcher(user.getEmail());
        Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$");
        Matcher passwordMatcher = passwordPattern.matcher(user.getPassword());
        return passwordMatcher.matches() && emailMatcher.matches();
    }
}
