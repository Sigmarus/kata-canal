package com.example.akkastreams.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="name_basics")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    @Column(name = "nconst")
    private String id;

    @Column(name = "primary_name")
    private String primaryName;

    @Column(name = "birth_year")
    private Integer birthYear;

    @Column(name = "death_year")
    private Integer deathYear;

    @Column(name = "primary_profession")
    private String primaryProfessions;

    @ManyToMany(mappedBy = "cast", fetch = FetchType.LAZY)
    private List<Title> knownFor;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private List<Crew> crewIn;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private List<Principal> principalIn;
}
