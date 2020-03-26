package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeServiceTest {

    @InjectMocks
    EmployeService employeService;
    @Mock
    EmployeRepository employeRepository;

    @Test
    void embaucheEmploye() throws EmployeException {
        //Given
        // Quand findLastMatricule appelée, elle renvoie null
        Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);

        // Quand le marticule employé existe retourne null
        Mockito.when(employeRepository.findByMatricule("C00001")).thenReturn(null);

        //When
        employeService.embaucheEmploye("DOE", "john", Poste.COMMERCIAL, NiveauEtude.BAC, 1.0);

        //Then

    }
}