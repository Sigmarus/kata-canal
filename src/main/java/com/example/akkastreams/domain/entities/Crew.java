package com.example.akkastreams.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Crew {

    @EmbeddedId
    private CrewKey id;

    @ManyToOne
    @MapsId("titleId")
    @JoinColumn(name = "tconst")
    private Title title;

    @ManyToOne
    @MapsId("personId")
    @JoinColumn(name = "nconst")
    private Person person;

    @Column(name = "type")
    private CrewType type;
}
