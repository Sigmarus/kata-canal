package com.example.akkastreams.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PrincipalKey implements Serializable {

    @Column(name = "tconst")
    String titleId;

    @Column(name = "nconst")
    String personId;
}
