package tn.esprit.autoloc.autolocapi.domain;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "agence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgence;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 50)
    private String ville;

    @Column(nullable = false, length = 200)
    private String adresse;

    @Column(nullable = false, length = 20)
    private String telephone;

    @OneToMany(mappedBy = "agence",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER)
    private Set<Vehicule> vehicules;

}