package com.example.vacancy_search.services;

import com.example.vacancy_search.domain.Vacancy;
import com.example.vacancy_search.repos.VacancyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacancyService {
    @Autowired
    VacancyRepo vacancyRepo;
    public void save(Vacancy vacancy){
        vacancyRepo.save(vacancy);
    }
    public Vacancy findBySalary(Integer salary){
        return vacancyRepo.findBySalary(salary);
    }
    public Vacancy findByOrganisation(String position){
        return vacancyRepo.findByPosition(position);
    }
    public List<Vacancy> findAll(){
        return vacancyRepo.findAll();
    }
}
