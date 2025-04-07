package com.example.vacancy_search.controllers;

import com.example.vacancy_search.config.SecurityUtils;
import com.example.vacancy_search.domain.*;
import com.example.vacancy_search.repos.EmployerRepo;
import com.example.vacancy_search.repos.UserRepo;
import com.example.vacancy_search.services.EmployerService;
import com.example.vacancy_search.services.ResumeService;
import com.example.vacancy_search.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class ProfileController {
    @Autowired
    UserService userService;
    @Autowired
    EmployerService employerService;
    @Autowired
    ResumeService resumeService;
    @Transactional
    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        List<Resume> resumes = resumeService.findAllByCandidate((Candidate) user);
        model.addAttribute("resumes",resumes);
        model.addAttribute("user", user);

        if (user.getRole() == Roles.EMPLOYER) {
            Employer employer = employerService.findByUsername(username);
            model.addAttribute("employer", employer);
        }

        return "profile";
    }
    @Transactional
    @GetMapping("/notifications")
    public String notications(Model model)
    {
        List<Resume> resumes = resumeService.findAllByCandidate((Candidate) userService.findByUsername(SecurityUtils.getCurrentUsername()));
        model.addAttribute("resumes",resumes);
        model.addAttribute("user",userService.findByUsername(SecurityUtils.getCurrentUsername()));
        return "notifications";
    }
}
