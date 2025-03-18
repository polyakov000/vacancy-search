package com.example.vacancy_search.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long ID;
    String position;
    Integer salary;
    String description;
    @ManyToOne
    @JoinColumn(name = "employer_id")
    Employer employer;
    @OneToMany(mappedBy = "vacancy")
    List<Application> applications;


}
