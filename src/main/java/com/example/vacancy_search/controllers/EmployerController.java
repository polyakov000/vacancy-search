package com.example.vacancy_search.controllers;

import com.example.vacancy_search.domain.Employer;
import com.example.vacancy_search.services.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employer")
public class EmployerController {
    @Autowired
    EmployerService employerService;
    @GetMapping("/{id}")
    public String employer(@PathVariable Long id, Model model){
        Employer employer = employerService.findById(id);
        model.addAttribute("employer",employer);
        return "employer";
    }
}
