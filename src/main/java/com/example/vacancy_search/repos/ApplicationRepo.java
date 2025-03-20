package com.example.vacancy_search.repos;

import com.example.vacancy_search.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepo extends JpaRepository<Application,Long> {
}
