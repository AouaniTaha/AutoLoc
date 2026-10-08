package tn.esprit.autoloc.autolocapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.autolocapi.domain.Equipement;

public interface IEquipementRepository extends JpaRepository<Equipement, Long> {
}
