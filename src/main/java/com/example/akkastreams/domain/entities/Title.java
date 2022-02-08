package com.example.akkastreams.domain.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="title_basics")
@Data
public class Title {

    @Id
    @Column(name = "tconst")
    String id;

    @Column(name = "title_type")
    String type;

    @Column(name = "primary_title")
    String primaryTitle;

    @Column(name = "original_title")
    String originalTitle;

    @Column(name = "is_adult")
    boolean isAdult = false;

    @Column(name = "start_year")
    int startYear;

    @Column(name = "end_year")
    Integer endYear;

    @Column(name = "runtime_minutes")
    int runtimeMinutes;

    @Column(name = "average_ratings")
    Integer averageRatings;

    @Column(name = "num_votes")
    Integer numVotes;

    @ManyToMany(fetch = FetchType.LAZY)
    List<Person> cast;

    @OneToMany(mappedBy = "title", fetch = FetchType.LAZY)
    List<Crew> crew;

    @OneToMany(mappedBy = "title", fetch = FetchType.LAZY)
    List<Episode> episodes;
}
