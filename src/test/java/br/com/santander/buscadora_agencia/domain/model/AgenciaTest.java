package br.com.santander.buscadora_agencia.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a entidade de domínio Agencia
 */
class AgenciaTest {

    @Test
    void deveCriarAgenciaComSucesso() {
        // Arrange & Act
        Agencia agencia = new Agencia(1L, 10, -5);

        // Assert
        assertNotNull(agencia);
        assertEquals(1L, agencia.getId());
        assertEquals(10, agencia.getPosX());
        assertEquals(-5, agencia.getPosY());
    }

    @Test
    void devePermitirCriarAgenciaComCoordenadaZero() {
        // Arrange & Act
        Agencia agencia = new Agencia(2L, 0, 0);

        // Assert
        assertNotNull(agencia);
        assertEquals(0, agencia.getPosX());
        assertEquals(0, agencia.getPosY());
    }

    @Test
    void devePermitirCriarAgenciaComCoordenadaNegativa() {
        // Arrange & Act
        Agencia agencia = new Agencia(3L, -10, -20);

        // Assert
        assertEquals(-10, agencia.getPosX());
        assertEquals(-20, agencia.getPosY());
    }

    @Test
    void deveGerarNomeAgenciaAutomaticamente() {
        // Arrange & Act
        Agencia agencia = new Agencia(5L, 10, 20);

        // Assert
        assertEquals("AGENCIA_5", agencia.getNome());
    }

    @Test
    void deveImplementarEqualsCorretamente() {
        // Arrange
        Agencia agencia1 = new Agencia(1L, 10, 20);
        Agencia agencia2 = new Agencia(1L, 10, 20);
        Agencia agencia3 = new Agencia(2L, 10, 20);

        // Assert
        assertEquals(agencia1, agencia2);
        assertNotEquals(agencia1, agencia3);
    }

    @Test
    void deveImplementarHashCodeCorretamente() {
        // Arrange
        Agencia agencia1 = new Agencia(1L, 10, 20);
        Agencia agencia2 = new Agencia(1L, 10, 20);

        // Assert
        assertEquals(agencia1.hashCode(), agencia2.hashCode());
    }
}
