package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeServiceTest {

    @InjectMocks
    EmployeService employeService;
    @Mock
    EmployeRepository employeRepository;

    String nom = "Khaldi";
    String prenom = "Shainee";
    Poste poste = Poste.COMMERCIAL;
    NiveauEtude niveauEtude = NiveauEtude.BAC;
    Double tempsPartiel = 1.0;
    String matricule = "C00001";


    void embaucheEmploye0Employe(){}
    void testExceptionNormal(){}
    void testExceptionJava8(){}
    void testEmbaucheEmployeLimiteMatricule(){}

    @Test
    public void should_retourner_employe() throws EmployeException {
        // Given
        // Quand findLastMatricule appelée, elle renvoie null
        when(employeRepository.findLastMatricule()).thenReturn(null);

        // Quand le marticule employé existe retourne null
        when(employeRepository.findByMatricule(matricule)).thenReturn(null);

        // When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        // Then
        // Employe employe = employeRepository.findMatricule("M45679");
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        verify(employeRepository).save(employeArgumentCaptor.capture());
        Employe employe = employeArgumentCaptor.getValue();

        assertAll(
                () -> assertEquals(nom, employe.getNom()),
                () -> assertEquals(prenom, employe.getPrenom()),
                () -> assertEquals(matricule, employe.getMatricule()),
                () -> assertEquals(LocalDate.now(), employe.getDateEmbauche()),
                () -> assertEquals(tempsPartiel, employe.getTempsPartiel())
        );
    }
}