package com.example.vacancy_search.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long ID;
    String position;
    String description;
    Double workExperience;
    @Setter
    @Lob
    byte[] file;
    @Setter
    String fileName;
    @OneToOne(mappedBy = "resume")
    Application application;
    @ManyToOne
    @JoinColumn(name = "candidate_id")
    Candidate candidate;
}

