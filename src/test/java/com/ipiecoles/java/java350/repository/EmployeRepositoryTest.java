package com.ipiecoles.java.java350.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class EmployeRepositoryTest {

    @Autowired
    private EmployeRepository employeRepository;

    @BeforeEach
    public void setup(){
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
}