package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    Long caTraite = 400L;
    Long objectifCa = 5555L;

    @Test
    public void should_retourner_employe_embauche() throws EmployeException {
        // Given
        when(employeRepository.findLastMatricule()).thenReturn(null);
        when(employeRepository.findByMatricule(matricule)).thenReturn(null);

        // When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
        // Then
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

    @Test
    void should_retourner_exception_quand_employe_embaucher_existe() {
        // Given
        matricule = "C11112";
        String lastMatricule = "11111";

        when(employeRepository.findLastMatricule()).thenReturn(lastMatricule);
        when(employeRepository.findByMatricule(matricule)).thenReturn(new Employe());

        // When Then
        assertThatThrownBy(
                () -> employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel)
        )
                .isInstanceOf(EntityExistsException.class)
                .hasMessage("employe " + matricule + " deja existant dans la BDD");
    }

    @Test
    void should_retourner_exception_quand_limite_employe_atteinte() {
        // Given
        String maxLastMatricule = "99999";

        when(employeRepository.findLastMatricule()).thenReturn(maxLastMatricule);
        // When Then
        assertThatThrownBy(
                () -> employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel)
        )
                .isInstanceOf(EmployeException.class)
                .hasMessage("matricule limite a 100000");
    }

    @ParameterizedTest
    @CsvSource({
            "'C00666', 200, 1000"
    })
    public void should_retourner_exception_performance_commercial_matricule_existe_pas(String matricule, Long caTraite, Long objectifCa) throws EmployeException {

        // When Then
        assertThatThrownBy(
                () -> employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa)
        )
                .isInstanceOf(EmployeException.class)
                .hasMessage("Le matricule " + matricule + " n'existe pas !");
    }

    @ParameterizedTest
    @CsvSource({
            "'C11111', , 1000"
    })
    public void should_retourner_exception_performance_commercial_performance_caTraite_null(String matricule, Long caTraite, Long objectifCa) throws EmployeException {

        // When Then
        assertThatThrownBy(
                () -> employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa)
        )
                .isInstanceOf(EmployeException.class)
                .hasMessage("Le chiffre d'affaire traité ne peut être négatif ou null !");
    }

}