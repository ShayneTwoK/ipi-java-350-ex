package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeTest extends Assertions {

    @Test
    public void testAnneeAncinneteNow(){
        //Given Envoie de la class Employe
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now());

        //When  recupéreration du nombre d'année d'ancienneté
        Integer anneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then : test du nombre d'année d'ancienneté
        assertThat(anneeAnciennete).isEqualTo(0);
    }

    @Test
    public void should_retourner_annee_anciennete_employe_moins_2(){
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(2L));

        //When
        Integer anneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        assertEquals(2, anneeAnciennete.intValue());
    }

    @Test
    public void should_retourner_annee_anciennete_employe_null(){
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(null);

        //When
        Integer anneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        assertEquals(0, anneeAnciennete.intValue());
    }

    @Test
    public void should_retourner_annee_anciennete_employe_plus_3(){
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().plusYears(3L));

        //When
        Integer anneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        assertEquals(0, anneeAnciennete.intValue());
    }

}