package com.example.vacancy_search.services;

import com.example.vacancy_search.domain.Employer;
import com.example.vacancy_search.repos.EmployerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployerService {
    @Autowired
    EmployerRepo employerRepo;
    public void save(Employer employer){
        employerRepo.save(employer);
    }
    public Employer findByOrganisation(String organisation){
       return employerRepo.findByOrganisation(organisation);
    }
    public Employer findById(Long id){
        return employerRepo.findById(id).orElseThrow(()->new RuntimeException("Employer not found"));
    }
}
