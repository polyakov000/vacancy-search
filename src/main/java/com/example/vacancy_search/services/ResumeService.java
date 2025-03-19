package com.example.vacancy_search.services;

import com.example.vacancy_search.domain.Candidate;
import com.example.vacancy_search.domain.Resume;
import com.example.vacancy_search.repos.ResumeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResumeService {
    @Autowired
    ResumeRepo resumeRepo;
    public void save(Resume resume){
        resumeRepo.save(resume);
    }
    public Optional<Resume> findById(Long id){
        return resumeRepo.findById(id);
    }
    public List<Resume> findAllByCandidate(Candidate candidate){
        return resumeRepo.findAllByCandidate(candidate);
    }
    public void deleteById(Long id){
        resumeRepo.deleteById(id);
    }
}
