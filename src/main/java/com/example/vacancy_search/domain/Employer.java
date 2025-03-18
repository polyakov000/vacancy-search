package com.example.vacancy_search.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "employers")
@SuperBuilder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Employer extends User{
    String organisation;
    String description;
    @OneToMany(mappedBy = "employer")
    List<Vacancy> vacancies;

}
