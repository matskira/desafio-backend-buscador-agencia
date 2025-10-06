package br.com.santander.buscadora_agencia.application.usecase;

import br.com.santander.buscadora_agencia.domain.model.Agencia;
import br.com.santander.buscadora_agencia.ports.output.AgenciaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para o caso de uso de cadastrar agência
 */
@ExtendWith(MockitoExtension.class)
class CadastrarAgenciaUseCaseImplTest {

    @Mock
    private AgenciaRepository agenciaRepository;

    @InjectMocks
    private CadastrarAgenciaUseCaseImpl cadastrarAgenciaUseCase;

    @Test
    void deveCadastrarAgenciaComSucesso() {
        // Arrange
        Integer posX = 10;
        Integer posY = -5;
        Agencia agenciaEsperada = new Agencia(1L, posX, posY);

        when(agenciaRepository.salvar(any(Agencia.class))).thenReturn(agenciaEsperada);

        // Act
        Agencia resultado = cadastrarAgenciaUseCase.cadastrar(posX, posY);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(posX, resultado.getPosX());
        assertEquals(posY, resultado.getPosY());
        verify(agenciaRepository, times(1)).salvar(any(Agencia.class));
    }

    @Test
    void deveCadastrarAgenciaComCoordenadaZero() {
        // Arrange
        Integer posX = 0;
        Integer posY = 0;
        Agencia agenciaEsperada = new Agencia(2L, posX, posY);

        when(agenciaRepository.salvar(any(Agencia.class))).thenReturn(agenciaEsperada);

        // Act
        Agencia resultado = cadastrarAgenciaUseCase.cadastrar(posX, posY);

        // Assert
        assertNotNull(resultado);
        assertEquals(0, resultado.getPosX());
        assertEquals(0, resultado.getPosY());
        verify(agenciaRepository, times(1)).salvar(any(Agencia.class));
    }

    @Test
    void deveCadastrarAgenciaComCoordenadaNegativa() {
        // Arrange
        Integer posX = -10;
        Integer posY = -20;
        Agencia agenciaEsperada = new Agencia(3L, posX, posY);

        when(agenciaRepository.salvar(any(Agencia.class))).thenReturn(agenciaEsperada);

        // Act
        Agencia resultado = cadastrarAgenciaUseCase.cadastrar(posX, posY);

        // Assert
        assertNotNull(resultado);
        assertEquals(-10, resultado.getPosX());
        assertEquals(-20, resultado.getPosY());
        verify(agenciaRepository, times(1)).salvar(any(Agencia.class));
    }
}
