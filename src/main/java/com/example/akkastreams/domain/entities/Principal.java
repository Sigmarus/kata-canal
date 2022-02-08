package com.example.akkastreams.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "principals")
public class Principal {

    @EmbeddedId
    PrincipalKey id;

    @ManyToOne
    @MapsId("titleId")
    @JoinColumn(name = "tconst")
    Title title;

    @ManyToOne
    @MapsId("personId")
    @JoinColumn(name = "nconst")
    Person person;

    Integer ordering;

    String category;

    String job;
}
