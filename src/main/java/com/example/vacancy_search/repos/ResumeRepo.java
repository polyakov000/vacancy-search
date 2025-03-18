package com.example.vacancy_search.repos;

import com.example.vacancy_search.domain.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResumeRepo extends JpaRepository<Resume, Long> {

    Optional<Resume> findById(Long id);
}
