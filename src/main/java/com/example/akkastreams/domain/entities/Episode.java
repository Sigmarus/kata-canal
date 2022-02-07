package com.example.akkastreams.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "episodes")
public class Episode {

    @Id
    @Column(name = "tconst")
    String id;

    @ManyToOne
    @JoinColumn(name = "parent_tconst")
    Title title;

    @Column(name = "season_number")
    Integer seasonNumber;

    @Column(name = "episode_number")
    Integer episodeNumber;
}
