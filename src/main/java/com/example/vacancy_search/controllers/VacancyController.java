package com.example.vacancy_search.controllers;

import com.example.vacancy_search.config.SecurityUtils;
import com.example.vacancy_search.domain.Employer;
import com.example.vacancy_search.domain.User;
import com.example.vacancy_search.domain.Vacancy;
import com.example.vacancy_search.services.UserService;
import com.example.vacancy_search.services.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/vacancy")
public class VacancyController {
    @Autowired
    VacancyService vacancyService;
    @Autowired
    UserService userService;
    @GetMapping("/create")
    public String createVacancyPage(Model model){
        model.addAttribute("user",userService.findByUsername(SecurityUtils.getCurrentUsername()));
        return "createVacancy";
    }
    @PostMapping("/create")
    public String createVacancy(@RequestParam String position, @RequestParam String salary, @RequestParam String description){
        User currentUser = userService.findByUsername(SecurityUtils.getCurrentUsername());
        Vacancy vacancy = Vacancy.builder()
                                .employer((Employer) currentUser)
                                .position(position)
                                .salary(Integer.parseInt(salary))
                                .description(description).build();
        vacancyService.save(vacancy);
        return "createVacancy";
    }
    @GetMapping("/find")
    public String findVacancy(Model model){
        List<Vacancy> vacancies = vacancyService.findAll();
        User currentUser = userService.findByUsername(SecurityUtils.getCurrentUsername());
        model.addAttribute("vacancies",vacancies);
        model.addAttribute("user",currentUser);
        return "vacancies";
    }

}
