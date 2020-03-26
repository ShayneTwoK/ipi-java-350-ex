package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeRepositoryTest {

    @Test
    void should_retourner_derniere_matricule_employe() {
        //Given
        Employe employer1 = new Employe("Khaldi", "Shaïnee", "Z1997", LocalDate.now(), 2000d, 1, 1.0);
        //When
        string lastMatricule = employeRepositoy.findLastMatricule();
        //Then
        assertThat();
    }

}