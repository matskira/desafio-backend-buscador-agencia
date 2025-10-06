package br.com.santander.buscadora_agencia.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para o serviço de cálculo de distância euclidiana
 */
class CalculadoraDistanciaServiceTest {

    private CalculadoraDistanciaService calculadoraDistanciaService;

    @BeforeEach
    void setUp() {
        calculadoraDistanciaService = new CalculadoraDistanciaService();
    }

    @Test
    void deveCalcularDistanciaEuclidianaCorretamente() {
        // Arrange
        int x1 = 0, y1 = 0;
        int x2 = 3, y2 = 4;

        // Act
        double distancia = calculadoraDistanciaService.calcularDistancia(x1, y1, x2, y2);

        // Assert
        assertEquals(5.0, distancia, 0.001);
    }

    @Test
    void deveCalcularDistanciaZeroParaMesmoPonto() {
        // Arrange
        int x1 = 10, y1 = 20;
        int x2 = 10, y2 = 20;

        // Act
        double distancia = calculadoraDistanciaService.calcularDistancia(x1, y1, x2, y2);

        // Assert
        assertEquals(0.0, distancia, 0.001);
    }

    @Test
    void deveCalcularDistanciaComCoordenadaNegativa() {
        // Arrange
        int x1 = -2, y1 = -2;
        int x2 = 10, y2 = 5;

        // Act
        double distancia = calculadoraDistanciaService.calcularDistancia(x1, y1, x2, y2);

        // Assert
        // √((10-(-2))² + (5-(-2))²) = √(144 + 49) = √193 ≈ 13.89
        assertEquals(13.89, distancia, 0.01);
    }

    @Test
    void deveCalcularDistanciaHorizontal() {
        // Arrange
        int x1 = 0, y1 = 0;
        int x2 = 10, y2 = 0;

        // Act
        double distancia = calculadoraDistanciaService.calcularDistancia(x1, y1, x2, y2);

        // Assert
        assertEquals(10.0, distancia, 0.001);
    }

    @Test
    void deveCalcularDistanciaVertical() {
        // Arrange
        int x1 = 0, y1 = 0;
        int x2 = 0, y2 = 10;

        // Act
        double distancia = calculadoraDistanciaService.calcularDistancia(x1, y1, x2, y2);

        // Assert
        assertEquals(10.0, distancia, 0.001);
    }

    @Test
    void deveFormatarDistanciaComDuasCasasDecimais() {
        // Arrange
        double distancia = 2.236067977;

        // Act
        String distanciaFormatada = calculadoraDistanciaService.formatarDistancia(distancia);

        // Assert
        assertEquals("2.24", distanciaFormatada);
    }

    @Test
    void deveFormatarDistanciaInteira() {
        // Arrange
        double distancia = 10.0;

        // Act
        String distanciaFormatada = calculadoraDistanciaService.formatarDistancia(distancia);

        // Assert
        assertEquals("10.00", distanciaFormatada);
    }
}
