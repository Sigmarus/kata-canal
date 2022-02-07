package com.example.akkastreams.domain.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="name_basics")
public class Person {

    @Id
    @Column(name = "nconst")
    String id;

    @Column(name = "primary_name")
    String primaryName;

    @Column(name = "birth_year")
    int birthYear;

    @Column(name = "death_year")
    int deathYear;

    @Column(name = "primary_profession")
    String primaryProfessions;

    @ManyToMany(mappedBy = "cast", fetch = FetchType.LAZY)
    @JoinTable(name = "known_for",
        joinColumns = @JoinColumn(name = "nconst"),
        inverseJoinColumns = @JoinColumn(name = "tconst"))
    List<Title> knownFor;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    List<Crew> crewIn;
}
