package com.example.vacancy_search.controllers;

import com.example.vacancy_search.config.SecurityUtils;
import com.example.vacancy_search.domain.*;
import com.example.vacancy_search.services.ResumeService;
import com.example.vacancy_search.services.UserService;
import com.example.vacancy_search.services.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/vacancy")
public class VacancyController {
    @Autowired
    VacancyService vacancyService;
    @Autowired
    UserService userService;
    @Autowired
    ResumeService resumeService;
    @GetMapping("/create")
    public String createVacancyPage(Model model){
        model.addAttribute("user",userService.findByUsername(SecurityUtils.getCurrentUsername()));
        return "createVacancy";
    }
    @PostMapping("/create")
    public String createVacancy(@RequestParam String position, @RequestParam String salary, @RequestParam String description, Principal principal){
        User currentUser = userService.findByUsername(principal.getName());
        Vacancy vacancy = Vacancy.builder()
                                .employer((Employer) currentUser)
                                .position(position)
                                .salary(Integer.parseInt(salary))
                                .description(description).build();
        vacancyService.save(vacancy);
        return "redirect:find";
    }
    @Transactional
    @GetMapping("/find")
    public String findVacancy(Model model, Principal principal){
        List<Vacancy> vacancies = vacancyService.findAll();
        User currentUser = userService.findByUsername(principal.getName());
        List<Resume> resumes = resumeService.findAllByCandidate((Candidate) currentUser);
        model.addAttribute("vacancies",vacancies);
        model.addAttribute("user",currentUser);
        model.addAttribute("resumes", resumes);
        return "vacancies";
    }

}
