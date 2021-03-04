package com.bida.testtask.service;

import com.bida.testtask.domain.User;
import com.bida.testtask.repository.UserDAO;
import com.bida.testtask.service.dto.MyUserDetails;
import com.bida.testtask.service.dto.UserRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    public User findUserByEmail(String email){
        return userDAO.findByEmail(email);
    }

    public void save(UserRegistrationDTO userDTO){
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getName());
        userDAO.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = findUserByEmail(s);
        if (user == null){
            throw new UsernameNotFoundException(s);
        }
        return new MyUserDetails(user);
    }
}
