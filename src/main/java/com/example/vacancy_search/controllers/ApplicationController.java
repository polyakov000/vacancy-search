package com.example.vacancy_search.controllers;

import com.example.vacancy_search.config.SecurityUtils;
import com.example.vacancy_search.domain.Candidate;
import com.example.vacancy_search.domain.Resume;
import com.example.vacancy_search.services.ApplicationService;
import com.example.vacancy_search.services.ResumeService;
import com.example.vacancy_search.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/application")
public class ApplicationController {
    @Autowired
    ApplicationService applicationService;
    @Autowired
    ResumeService resumeService;
    @Autowired
    UserService userService;

    @GetMapping("/resume/my")
    public List<Resume> getMyResumes(){
        Candidate candidate = (Candidate) userService.findByUsername(SecurityUtils.getCurrentUsername());
        return resumeService.findAllByCandidate(candidate);
    }
}
