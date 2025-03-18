package com.example.vacancy_search.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String text;
    @ManyToOne
    @JoinColumn(name = "candidate_id")
    Candidate candidate;
    @ManyToOne
    @JoinColumn(name = "vacancy_id")
    Vacancy vacancy;
    @OneToOne
    @JoinColumn(name = "resume_id")
    Resume resume;


}
