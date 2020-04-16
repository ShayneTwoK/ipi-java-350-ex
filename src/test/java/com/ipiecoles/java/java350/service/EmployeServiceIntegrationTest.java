package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class EmployeServiceIntegrationTest {

    @Autowired
    EmployeService employeService;

    @Autowired
    private EmployeRepository employeRepository;

    @BeforeEach
    @AfterEach
    public void setup(){
        employeRepository.deleteAll();
    }

    @Test
    void should_retourner_calcul_performance_commercial_objectifCa_Cas_5() throws EmployeException {
        // Given
        //CAS
        String matricule = "C00666";
        Long caTraite = 9999L;
        Long objectifCa = 5555L;
        Integer performance = 10;

        employeRepository.save(new Employe(
                "Khaldi",
                "Shaïnee",
                matricule,
                LocalDate.now(),
                Entreprise.SALAIRE_BASE,
                performance,
                1.0));

        Integer performanceAttendu = 15;

        // When
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

        // Then
        Employe employe = employeRepository.findByMatricule(matricule);
        assertEquals(performanceAttendu, employe.getPerformance());
    }

}