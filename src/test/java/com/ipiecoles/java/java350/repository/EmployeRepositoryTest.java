package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeRepositoryTest {

    @Autowired
    private EmployeRepository employeRepository;

    @BeforeEach
    public void setUp(){
        employeRepository.deleteAll();
    }

    @Test
    public void should_retourner_derniere_matricule_employe(){
        //Given

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        assertNull(lastMatricule);
    }

    @Test
    public void should_retourner_plusieurs_matricules(){
        //Given
        employeRepository.save(new Employe("Doe", "John", "T12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        employeRepository.save(new Employe("Doe", "Jane", "M40325", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        employeRepository.save(new Employe("Doe", "Jim", "C06432", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        assertEquals("40325", lastMatricule);
    }

    @Test
    void should_retourner_moyenne_performance_pour_deux_commercials() {
        //Given
        Employe employeUn = new Employe();
        employeUn.setMatricule("C00001");
        employeUn.setPerformance(15);
        employeRepository.save(employeUn);

        Employe employeDeux = new Employe();
        employeDeux.setMatricule("C00002");
        employeDeux.setPerformance(56);
        employeRepository.save(employeDeux);

        //When
        Double moyenne = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");

        //Then
        assertEquals(moyenne,35.5);
    }
}