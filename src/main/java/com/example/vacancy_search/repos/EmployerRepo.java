package com.example.vacancy_search.repos;

import com.example.vacancy_search.domain.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployerRepo extends JpaRepository<Employer, Long> {
    Employer findByOrganisation(String organisation);

    Employer findByUsername(String username);
}
