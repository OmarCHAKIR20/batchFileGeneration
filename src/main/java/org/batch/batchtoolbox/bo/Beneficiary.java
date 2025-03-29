package org.batch.batchtoolbox.bo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "BENEFICIARY")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Beneficiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "STATE")
    private String state;

    @Column(name = "NOM")
    private String lastName;

    @Column(name = "PRENOM")
    private String firstName;

    @Column(name = "DATEDEPOT")
    private Date depositDate;

    @Column(name = "DATENAISSANCE")
    private Date birthDate;

    @Column(name = "GENRE")
    private String genre;

    @Column(name = "ADRESSE_BENEFICIAIRE")
    private String beneficiaryAddress;

    @Column(name = "VILLE_BENEFICIAIRE")
    private String beneficiaryCityCode;

    @Column(name = "FILIERE")
    private String sector;

    @Column(name = "DUREE_ETUDE")
    private String studiesPeriod;

    @Column(name = "NOMECOLE")
    private String schoolName;
}