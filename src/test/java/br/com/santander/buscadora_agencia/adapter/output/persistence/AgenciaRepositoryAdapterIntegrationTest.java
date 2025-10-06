package br.com.santander.buscadora_agencia.adapter.output.persistence;

import br.com.santander.buscadora_agencia.domain.model.Agencia;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes de integração para AgenciaRepositoryAdapter com H2
 */
@DataJpaTest
@Import(AgenciaRepositoryAdapter.class)
class AgenciaRepositoryAdapterIntegrationTest {

    @Autowired
    private AgenciaRepositoryAdapter agenciaRepositoryAdapter;

    @Test
    void deveSalvarAgenciaNoH2() {
        // Arrange
        Agencia agencia = new Agencia(null, 10, -5);

        // Act
        Agencia agenciaSalva = agenciaRepositoryAdapter.salvar(agencia);

        // Assert
        assertNotNull(agenciaSalva);
        assertNotNull(agenciaSalva.getId());
        assertEquals(10, agenciaSalva.getPosX());
        assertEquals(-5, agenciaSalva.getPosY());
        assertEquals("AGENCIA_" + agenciaSalva.getId(), agenciaSalva.getNome());
    }

    @Test
    void deveBuscarTodasAsAgencias() {
        // Arrange
        agenciaRepositoryAdapter.salvar(new Agencia(null, 10, -5));
        agenciaRepositoryAdapter.salvar(new Agencia(null, 20, 30));
        agenciaRepositoryAdapter.salvar(new Agencia(null, -10, -20));

        // Act
        List<Agencia> agencias = agenciaRepositoryAdapter.buscarTodas();

        // Assert
        assertNotNull(agencias);
        assertEquals(3, agencias.size());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverAgencias() {
        // Act
        List<Agencia> agencias = agenciaRepositoryAdapter.buscarTodas();

        // Assert
        assertNotNull(agencias);
        assertTrue(agencias.isEmpty());
    }

    @Test
    void deveGerarIdAutomaticamente() {
        // Arrange
        Agencia agencia1 = new Agencia(null, 10, 20);
        Agencia agencia2 = new Agencia(null, 30, 40);

        // Act
        Agencia salva1 = agenciaRepositoryAdapter.salvar(agencia1);
        Agencia salva2 = agenciaRepositoryAdapter.salvar(agencia2);

        // Assert
        assertNotNull(salva1.getId());
        assertNotNull(salva2.getId());
        assertNotEquals(salva1.getId(), salva2.getId());
    }
}
