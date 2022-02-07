package com.example.akkastreams.domain.entities;

import javax.persistence.*;

@Entity
public class Crew {

    @EmbeddedId
    CrewKey id;

    @ManyToOne
    @MapsId("titleId")
    @JoinColumn(name = "tconst")
    Title title;

    @ManyToOne
    @MapsId("personId")
    @JoinColumn(name = "nconst")
    Person person;

    @Column(name = "type")
    CrewType type;
}
