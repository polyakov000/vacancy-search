package com.example.vacancy_search.controllers;

import com.example.vacancy_search.domain.Employer;
import com.example.vacancy_search.domain.Roles;
import com.example.vacancy_search.domain.User;
import com.example.vacancy_search.repos.EmployerRepo;
import com.example.vacancy_search.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class ProfileController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    private EmployerRepo employerRepository;

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepo.findByUsername(username);
        model.addAttribute("user", user);

        if (user.getRole() == Roles.EMPLOYER) {
            Employer employer = employerRepository.findByUsername(username);
            model.addAttribute("employer", employer);
        }

        return "profile";
    }
}
