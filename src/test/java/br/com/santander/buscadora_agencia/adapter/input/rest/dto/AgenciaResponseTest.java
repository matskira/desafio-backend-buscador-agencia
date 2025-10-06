package br.com.santander.buscadora_agencia.adapter.input.rest.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para AgenciaResponse
 */
class AgenciaResponseTest {

    @Test
    void deveCriarResponseComConstrutorCompleto() {
        // Act
        AgenciaResponse response = new AgenciaResponse(1L, "AGENCIA_1", 10, -5);

        // Assert
        assertEquals(1L, response.getId());
        assertEquals("AGENCIA_1", response.getNome());
        assertEquals(10, response.getPosX());
        assertEquals(-5, response.getPosY());
    }

    @Test
    void deveCriarResponseComConstrutorVazio() {
        // Act
        AgenciaResponse response = new AgenciaResponse();

        // Assert
        assertNotNull(response);
    }

    @Test
    void deveDefinirValoresComSetters() {
        // Arrange
        AgenciaResponse response = new AgenciaResponse();

        // Act
        response.setId(2L);
        response.setNome("AGENCIA_2");
        response.setPosX(20);
        response.setPosY(30);

        // Assert
        assertEquals(2L, response.getId());
        assertEquals("AGENCIA_2", response.getNome());
        assertEquals(20, response.getPosX());
        assertEquals(30, response.getPosY());
    }
}
