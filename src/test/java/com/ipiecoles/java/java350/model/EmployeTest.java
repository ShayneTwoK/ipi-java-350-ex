package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeTest extends Assertions {

    @Test
    public void testAnneeAncienneteNow() {
        // Given Envoie de la class Employe
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now());

        // When  recupéreration du nombre d'année d'ancienneté
        Integer anneeAnciennete = employe.getNombreAnneeAnciennete();

        // Then : test du nombre d'année d'ancienneté
        assertThat(anneeAnciennete).isEqualTo(0);
    }

    @Test
    public void should_retourner_annee_anciennete_employe_moins_2() {
        // Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(2L));

        // When
        Integer anneeAnciennete = employe.getNombreAnneeAnciennete();

        // Then
        assertEquals(2, anneeAnciennete.intValue());
    }

    @Test
    public void should_retourner_annee_anciennete_employe_plus_3() {
        // Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().plusYears(3L));

        // When
        Integer anneeAnciennete = employe.getNombreAnneeAnciennete();

        // Then
        assertEquals(0, anneeAnciennete.intValue());
    }

    @Test
    public void should_retourner_annee_anciennete_employe_null() {
        // Given
        Employe employe = new Employe();
        employe.setDateEmbauche(null);

        // When
        Integer anneeAnciennete = employe.getNombreAnneeAnciennete();

        // Then
        assertEquals(0, anneeAnciennete.intValue());
    }

    @ParameterizedTest
    @CsvSource({
            "1, 'T12345', 0, 1.0, 1000.0", "1, 'T12345', 2, 0.5, 600.0", "1, 'T12345', 2, 1.0, 1200.0",
            "2, 'T12345', 0, 1.0, 2300.0", "2, 'T12345', 1, 1.0, 2400.0", "1, 'M12345', 0, 1.0, 1700.0",
            "1, 'M12345', 5, 1.0, 2200.0", "2, 'M12345', 0, 1.0, 1700.0", "2, 'M12345', 8, 1.0, 2500.0"
    })
    public void getPrimeAnnuelle(Integer performance, String matricule, Long nbYearsAnciennete, Double tempsPartiel, Double primeAnnuelle){
        //Given
        Employe employe = new Employe(
                "Khaldi",
                "Shaïnee",
                matricule,
                LocalDate.now().minusYears(nbYearsAnciennete),
                Entreprise.SALAIRE_BASE,
                performance,
                tempsPartiel);
        //When
        Double prime = employe.getPrimeAnnuelle();

        //Then
        assertEquals(primeAnnuelle, prime);
    }

    @Test
    public void should_retourner_prime_annuelle_employe_simple_avec_performance_de_base() {
        // Given
        LocalDate dateEmbauche = LocalDate.now().minusYears(4);
        Employe employe = new Employe(
                "Khaldi",
                "Shaïnee",
                "C00001",
                dateEmbauche,
                Entreprise.SALAIRE_BASE,
                Entreprise.PERFORMANCE_BASE,
                1.0
        );

        int anneeAnciennete = LocalDate.now().getYear() - dateEmbauche.getYear();

        // When
        Double primeAnciennete = Entreprise.PRIME_ANCIENNETE * anneeAnciennete;
        Double primeAnnuelle = Entreprise.primeAnnuelleBase() + primeAnciennete;
        Double prime = employe.getPrimeAnnuelle();

        // Then
        assertEquals(primeAnnuelle, prime);
    }

    @Test
    public void should_retourner_prime_annuelle_employe_simple_sans_performance_de_base() {
        // Given
        LocalDate dateEmbauche = LocalDate.now().minusYears(4);
        int performance = 0;
        Employe employe = new Employe(
                "Khaldi",
                "Shaïnee",
                "C00001",
                dateEmbauche,
                Entreprise.SALAIRE_BASE,
                performance,
                1.0
        );

        int anneeAnciennete = LocalDate.now().getYear() - dateEmbauche.getYear();

        // When
        Double primeAnciennete = Entreprise.PRIME_ANCIENNETE * anneeAnciennete;
        Double primeAnnuelle = Entreprise.primeAnnuelleBase() * (performance + Entreprise.INDICE_PRIME_BASE) + primeAnciennete;

        Double primeAttendu = employe.getPrimeAnnuelle();

        // Then
        assertEquals(primeAnnuelle, primeAttendu);
    }

    @Test
    public void should_augmenter_salaire_avec_un_pourcentage_null() {
        //Given
        Employe employe = new Employe("Doe", "John", "M00001", LocalDate.now(), 1000d, 1, 1.5);
        employe.augmenterSalaire(null);

        //When
        Double nouveauSalaire = employe.getSalaire();

        //Then
        assertThat(nouveauSalaire).isEqualTo(1000d);
    }

    @Test
    public void should_augmenter_salaire_avec_un_pourcentage_zero() {
        //Given
        Employe employe = new Employe("Doe", "John", "X00001", LocalDate.now(), 1000d, 1, 1.5);
        employe.augmenterSalaire(0.0);

        //When
        Double nouveauSalaire = employe.getSalaire();

        //Then
        assertThat(nouveauSalaire).isEqualTo(1000d);
    }

    @ParameterizedTest
    @CsvSource({"0.1"})
    public void should_augmenter_salaire_avec_un_pourcentage(Double pourcentage) {
        //Given
        // Peut on mock un employe ?
        Employe employe = new Employe("Doe", "John", "C00001", LocalDate.now(), 1000d, 1, 1.5);
        // Peut on mock un parameterizedTest ?
        employe.augmenterSalaire(pourcentage);

        //When
        Double nouveauSalaire = employe.getSalaire();
        Double salaireAttendu = 1000d * (1 + pourcentage);
        //Then
        assertThat(nouveauSalaire).isEqualTo(salaireAttendu);
    }

    @ParameterizedTest
    @CsvSource({"0.5"})
    public void should_augmenter_salaire_avec_un_pourcentage_et_salaire_zero(Double pourcentage) {
        //Given
        // Peut on mock un employe ?
        Employe employe = new Employe("Doe", "John", "C00001", LocalDate.now(), 0.0, 1, 1.5);
        // Peut on mock un parameterizedTest ?
        employe.augmenterSalaire(pourcentage);

        //When
        Double nouveauSalaire = employe.getSalaire();
        Double salaireAttendu = 0.0 * (1 + pourcentage);
        //Then
        assertThat(nouveauSalaire).isEqualTo(salaireAttendu);
    }
}