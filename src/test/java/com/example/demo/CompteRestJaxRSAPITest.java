package com.example.demo;

import com.example.demo.entities.Compte;
import com.example.demo.entities.TypeCompte;
import com.example.demo.repositories.CompteRepository;
import com.example.demo.ws.CompteRestJaxRSAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CompteRestJaxRSAPITest {

    @Autowired
    private CompteRestJaxRSAPI compteRestJaxRSAPI;

    @Autowired
    private CompteRepository compteRepository;

    @BeforeEach
    void setUp() {
        compteRepository.deleteAll();
    }

    @Test
    void testAddCompte() {
        Compte compte = new Compte(null, 4500.0, new Date(), TypeCompte.COURANT);
        Compte saved = compteRestJaxRSAPI.addCompte(compte);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals(4500.0, saved.getSolde());
        assertEquals(TypeCompte.COURANT, saved.getType());
    }

    @Test
    void testGetComptes() {
        compteRestJaxRSAPI.addCompte(new Compte(null, 1000.0, new Date(), TypeCompte.EPARGNE));
        compteRestJaxRSAPI.addCompte(new Compte(null, 2000.0, new Date(), TypeCompte.COURANT));

        List<Compte> comptes = compteRestJaxRSAPI.getComptes();
        assertEquals(2, comptes.size());
    }

    @Test
    void testGetCompteById() {
        Compte saved = compteRestJaxRSAPI.addCompte(new Compte(null, 7500.0, new Date(), TypeCompte.EPARGNE));
        Compte found = compteRestJaxRSAPI.getCompte(saved.getId());

        assertNotNull(found);
        assertEquals(saved.getId(), found.getId());
        assertEquals(7500.0, found.getSolde());
    }

    @Test
    void testUpdateCompte() {
        Compte saved = compteRestJaxRSAPI.addCompte(new Compte(null, 3000.0, new Date(), TypeCompte.COURANT));
        saved.setSolde(9999.0);
        saved.setType(TypeCompte.EPARGNE);

        Compte updated = compteRestJaxRSAPI.updateCompte(saved.getId(), saved);
        assertNotNull(updated);
        assertEquals(9999.0, updated.getSolde());
        assertEquals(TypeCompte.EPARGNE, updated.getType());
    }

    @Test
    void testDeleteCompte() {
        Compte saved = compteRestJaxRSAPI.addCompte(new Compte(null, 500.0, new Date(), TypeCompte.COURANT));
        Long id = saved.getId();

        compteRestJaxRSAPI.deleteCompte(id);
        Compte deleted = compteRestJaxRSAPI.getCompte(id);
        assertNull(deleted);
    }
}
