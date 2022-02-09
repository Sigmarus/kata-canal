package com.example.akkastreams.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="title_basics")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Title {

    @Id
    @Column(name = "tconst")
    private String id;

    @Column(name = "title_type")
    private String type;

    @Column(name = "primary_title")
    private String primaryTitle;

    @Column(name = "original_title")
    private String originalTitle;

    @Column(name = "is_adult")
    private Boolean isAdult = false;

    @Column(name = "start_year")
    private Integer startYear;

    @Column(name = "end_year")
    private Integer endYear;

    @Column(name = "runtime_minutes")
    private Integer runtimeMinutes;

    @Column(name = "average_ratings")
    private Double averageRatings;

    @Column(name = "num_votes")
    private Integer numVotes;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Person> cast;

    @OneToMany(mappedBy = "title", fetch = FetchType.LAZY)
    private List<Crew> crew;

    @OneToMany(mappedBy = "title", fetch = FetchType.LAZY)
    private List<Principal> principals;

    @OneToMany(mappedBy = "title", fetch = FetchType.LAZY)
    private List<Episode> episodes;
}
