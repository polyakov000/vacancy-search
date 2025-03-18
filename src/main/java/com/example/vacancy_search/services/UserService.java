package com.example.vacancy_search.services;

import com.example.vacancy_search.domain.User;
import com.example.vacancy_search.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    public void save(User user){
        userRepo.save(user);
    }
    public User findByUsername(String username){
       return userRepo.findByUsername(username);
    }

}
