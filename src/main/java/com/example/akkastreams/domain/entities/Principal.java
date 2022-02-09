package com.example.akkastreams.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "principals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Principal {

    @EmbeddedId
    private PrincipalKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("titleId")
    @JoinColumn(name = "tconst")
    private Title title;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("personId")
    @JoinColumn(name = "nconst")
    private Person person;

    private Integer ordering;

    private String category;

    private String job;
}
