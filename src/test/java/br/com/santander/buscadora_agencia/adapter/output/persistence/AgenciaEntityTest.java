package br.com.santander.buscadora_agencia.adapter.output.persistence;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para AgenciaEntity
 */
class AgenciaEntityTest {

    @Test
    void deveCriarEntityComConstrutorCompleto() {
        // Act
        AgenciaEntity entity = new AgenciaEntity(1L, 10, -5);

        // Assert
        assertEquals(1L, entity.getId());
        assertEquals(10, entity.getPosX());
        assertEquals(-5, entity.getPosY());
    }

    @Test
    void deveCriarEntityComConstrutorVazio() {
        // Act
        AgenciaEntity entity = new AgenciaEntity();

        // Assert
        assertNotNull(entity);
    }

    @Test
    void deveDefinirValoresComSetters() {
        // Arrange
        AgenciaEntity entity = new AgenciaEntity();

        // Act
        entity.setId(2L);
        entity.setPosX(20);
        entity.setPosY(30);

        // Assert
        assertEquals(2L, entity.getId());
        assertEquals(20, entity.getPosX());
        assertEquals(30, entity.getPosY());
    }
}
