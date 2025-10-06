package br.com.santander.buscadora_agencia.adapter.input.rest.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para CadastrarAgenciaRequest
 */
class CadastrarAgenciaRequestTest {

    @Test
    void deveCriarRequestComConstrutorCompleto() {
        // Act
        CadastrarAgenciaRequest request = new CadastrarAgenciaRequest(10, -5);

        // Assert
        assertEquals(10, request.getPosX());
        assertEquals(-5, request.getPosY());
    }

    @Test
    void deveCriarRequestComConstrutorVazio() {
        // Act
        CadastrarAgenciaRequest request = new CadastrarAgenciaRequest();

        // Assert
        assertNotNull(request);
    }

    @Test
    void deveDefinirValoresComSetters() {
        // Arrange
        CadastrarAgenciaRequest request = new CadastrarAgenciaRequest();

        // Act
        request.setPosX(20);
        request.setPosY(30);

        // Assert
        assertEquals(20, request.getPosX());
        assertEquals(30, request.getPosY());
    }
}
