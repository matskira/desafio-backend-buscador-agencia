package br.com.santander.buscadora_agencia.application.usecase;

import br.com.santander.buscadora_agencia.domain.model.Agencia;
import br.com.santander.buscadora_agencia.domain.service.CalculadoraDistanciaService;
import br.com.santander.buscadora_agencia.ports.output.AgenciaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para o caso de uso de buscar agências por distância
 */
@ExtendWith(MockitoExtension.class)
class BuscarAgenciasPorDistanciaUseCaseImplTest {

    @Mock
    private AgenciaRepository agenciaRepository;

    @Mock
    private CalculadoraDistanciaService calculadoraDistanciaService;

    @InjectMocks
    private BuscarAgenciasPorDistanciaUseCaseImpl buscarAgenciasPorDistanciaUseCase;

    @Test
    void deveBuscarAgenciasOrdenadasPorDistancia() {
        // Arrange
        Agencia agencia1 = new Agencia(1L, -2, -2);
        Agencia agencia2 = new Agencia(2L, 10, 4);
        Agencia agencia3 = new Agencia(3L, -5, -2);
        Agencia agencia4 = new Agencia(4L, 10, -7);

        List<Agencia> agencias = Arrays.asList(agencia1, agencia2, agencia3, agencia4);

        when(agenciaRepository.buscarTodas()).thenReturn(agencias);

        // Distâncias calculadas em relação a (10, 5)
        when(calculadoraDistanciaService.calcularDistancia(10, 5, -2, -2)).thenReturn(10.0);
        when(calculadoraDistanciaService.calcularDistancia(10, 5, 10, 4)).thenReturn(2.2);
        when(calculadoraDistanciaService.calcularDistancia(10, 5, -5, -2)).thenReturn(37.42);
        when(calculadoraDistanciaService.calcularDistancia(10, 5, 10, -7)).thenReturn(10.0);

        when(calculadoraDistanciaService.formatarDistancia(2.2)).thenReturn("2.2");
        when(calculadoraDistanciaService.formatarDistancia(10.0)).thenReturn("10.0");
        when(calculadoraDistanciaService.formatarDistancia(37.42)).thenReturn("37.42");

        // Act
        Map<String, String> resultado = buscarAgenciasPorDistanciaUseCase.buscarPorDistancia(10, 5);

        // Assert
        assertNotNull(resultado);
        assertEquals(4, resultado.size());

        // Verifica a ordem (mais próxima primeiro)
        List<String> chavesOrdenadas = List.copyOf(resultado.keySet());
        assertEquals("AGENCIA_2", chavesOrdenadas.get(0)); // distância 2.2
        assertTrue(chavesOrdenadas.get(1).equals("AGENCIA_1") || chavesOrdenadas.get(1).equals("AGENCIA_4")); // ambas com 10.0
        assertEquals("AGENCIA_3", chavesOrdenadas.get(3)); // distância 37.42

        // Verifica os valores
        assertEquals("distancia = 2.2", resultado.get("AGENCIA_2"));
        assertEquals("distancia = 10.0", resultado.get("AGENCIA_1"));
        assertEquals("distancia = 37.42", resultado.get("AGENCIA_3"));

        verify(agenciaRepository, times(1)).buscarTodas();
        verify(calculadoraDistanciaService, times(4)).calcularDistancia(anyInt(), anyInt(), anyInt(), anyInt());
    }

    @Test
    void deveRetornarMapaVazioQuandoNaoHouverAgencias() {
        // Arrange
        when(agenciaRepository.buscarTodas()).thenReturn(Arrays.asList());

        // Act
        Map<String, String> resultado = buscarAgenciasPorDistanciaUseCase.buscarPorDistancia(10, 5);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(agenciaRepository, times(1)).buscarTodas();
        verify(calculadoraDistanciaService, never()).calcularDistancia(anyInt(), anyInt(), anyInt(), anyInt());
    }

    @Test
    void deveBuscarAgenciaComDistanciaZero() {
        // Arrange
        Agencia agencia = new Agencia(1L, 10, 5);
        when(agenciaRepository.buscarTodas()).thenReturn(Arrays.asList(agencia));
        when(calculadoraDistanciaService.calcularDistancia(10, 5, 10, 5)).thenReturn(0.0);
        when(calculadoraDistanciaService.formatarDistancia(0.0)).thenReturn("0.0");

        // Act
        Map<String, String> resultado = buscarAgenciasPorDistanciaUseCase.buscarPorDistancia(10, 5);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("distancia = 0.0", resultado.get("AGENCIA_1"));
        verify(agenciaRepository, times(1)).buscarTodas();
    }
}
