package com.example.vacancy_search.repos;

import com.example.vacancy_search.domain.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacancyRepo extends JpaRepository<Vacancy,Long> {
    Vacancy findByPosition(String position);
    Vacancy findBySalary(Integer salary);
}
