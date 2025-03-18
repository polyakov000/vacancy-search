package com.example.vacancy_search.controllers;

import com.example.vacancy_search.domain.Vacancy;
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
    @GetMapping("/create")
    public String createVacancyPage(){
        return "createVacancy";
    }
    @PostMapping("/create")
    public String createVacancy(@RequestParam String position, @RequestParam String salary, @RequestParam String description){
        Vacancy vacancy = Vacancy.builder()
                                .position(position)
                                .salary(Integer.parseInt(salary))
                                .description(description).build();
        vacancyService.save(vacancy);
        return "createVacancy";
    }
    @GetMapping("/find")
    public String findVacancy(Model model){
        List<Vacancy> vacancies = vacancyService.findAll();
        model.addAttribute("vacancies",vacancies);
        return "vacancies";
    }

}
