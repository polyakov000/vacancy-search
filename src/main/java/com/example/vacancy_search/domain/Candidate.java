package com.example.vacancy_search.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "candidates")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Candidate extends User {
    @OneToMany(mappedBy = "candidate")
    List<Application> applications;

}