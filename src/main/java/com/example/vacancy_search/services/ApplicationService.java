package com.example.vacancy_search.services;

import com.example.vacancy_search.domain.Application;
import com.example.vacancy_search.repos.ApplicationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {
    @Autowired
    ApplicationRepo applicationRepo;
    public void save(Application application){
        applicationRepo.save(application);
    }
}
