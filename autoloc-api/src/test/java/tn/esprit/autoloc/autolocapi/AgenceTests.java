package tn.esprit.autoloc.autolocapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.domain.CategorieVehicule;
import tn.esprit.autoloc.autolocapi.domain.StatutVehicule;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.repository.IAgenceRepository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AgenceTests {

    @Autowired
    private AgenceRepositoryMock basicAgenceRepository;

    @Autowired
    private IAgenceRepository fullAgenceRepository;

    private void addAgence(CrudRepository<Agence, Long> repository) {
        Agence agence = new Agence();
        agence.setNom("Agence ariana");
        agence.setTelephone("71585874");
        agence.setVille("Tunis");
        agence.setAdresse("1 Rue Hedi");

        int timestamp = (int) System.currentTimeMillis();

        Vehicule vehicule1 = new Vehicule();
        vehicule1.setCategorie(CategorieVehicule.SUV);
        vehicule1.setImmatriculation("785414TU96" + timestamp);
        vehicule1.setMarque("Toyota");
        vehicule1.setModele("DMax");
        vehicule1.setStatut(StatutVehicule.MAINTENANCE);
        vehicule1.setTarifJournalier(new BigDecimal("100"));
        vehicule1.setAgence(agence);

        Vehicule vehicule2 = new Vehicule();
        vehicule2.setCategorie(CategorieVehicule.UTILITAIRE);
        vehicule2.setImmatriculation("785414TU95" + timestamp);
        vehicule2.setMarque("Toyota");
        vehicule2.setModele("Yaris");
        vehicule2.setStatut(StatutVehicule.DISPONIBLE);
        vehicule2.setTarifJournalier(new BigDecimal("80"));
        vehicule2.setAgence(agence);

        Set<Vehicule> vehicules = new HashSet<>();
        vehicules.add(vehicule1);
        vehicules.add(vehicule2);
        agence.setVehicules(vehicules);

        repository.save(agence);
    }
²
    private void loadAgence(CrudRepository<Agence, Long> repository, String repoType) {
        List<Agence> agences = (List<Agence>) repository.findAll();

        StringBuilder sb = new StringBuilder();
        sb.append("Repository Type: ").append(repoType).append("\n");
        for (Agence agence : agences) {
            sb.append("\n")
                    .append(agence.getIdAgence())
                    .append(" | ")
                    .append(agence.getNom())
                    .append("\nVehicules Count : ")
                    .append(agence.getVehicules().size());

            for (Vehicule v : agence.getVehicules()) {
                sb.append("\n=== ")
                        .append(v.getIdVehicule())
                        .append("|")
                        .append(v.getImmatriculation());
            }
        }

        org.junit.Assert.fail(sb.toString());
    }

    @Test
    public void basicAddAgence() {
        addAgence(basicAgenceRepository);
    }

    @Test
    public void fullAddAgence() {
        addAgence(fullAgenceRepository);
    }

    @Test
    public void basicLoadAgence() {
        loadAgence(basicAgenceRepository, "Basic");
    }

    @Test
    public void fullLoadAgence() {
        loadAgence(fullAgenceRepository, "Full");
    }

    @Test
    public void loadSortedAgences() {
        Sort sort = Sort.by(Sort.Direction.DESC, "idAgence");
        List<Agence> agences = fullAgenceRepository.findAll(sort);

        StringBuilder sb = new StringBuilder();
        sb.append("Agences sorted by id descending:\n");
        for (Agence agence : agences) {
            sb.append("\n")
                    .append(agence.getIdAgence())
                    .append(" | ")
                    .append(agence.getNom())
                    .append(" | ")
                    .append(agence.getVille())
                    .append(" | ")
                    .append(agence.getTelephone());
        }

        org.junit.Assert.fail(sb.toString());
    }

    @Test
    public void loadPagedAgences() {
        Sort sort = Sort.by(Sort.Direction.DESC, "idAgence");
        Pageable pageable = PageRequest.of(0, 2, sort);
        Page<Agence> agencePage = fullAgenceRepository.findAll(pageable);

        StringBuilder sb = new StringBuilder();
        sb.append("Paged Agences (page size 2):\n");
        sb.append("Total pages: ").append(agencePage.getTotalPages()).append("\n");
        sb.append("Current page: ").append(agencePage.getNumber()).append("\n");
        sb.append("Total elements: ").append(agencePage.getTotalElements()).append("\n");
        sb.append("Agences on this page:\n");

        for (Agence agence : agencePage.getContent()) {
            sb.append("\n")
                    .append(agence.getIdAgence())
                    .append(" | ")
                    .append(agence.getNom())
                    .append(" | ")
                    .append(agence.getVille())
                    .append(" | ")
                    .append(agence.getTelephone());
        }

        org.junit.Assert.fail(sb.toString());
    }
}

interface AgenceRepositoryMock extends CrudRepository<Agence, Long> {
}