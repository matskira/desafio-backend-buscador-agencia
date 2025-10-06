package br.com.santander.buscadora_agencia.domain.service;

import java.util.Locale;

/**
 * Serviço de domínio responsável por calcular distância euclidiana entre dois pontos
 */
public class CalculadoraDistanciaService {

    /**
     * Calcula a distância euclidiana entre dois pontos usando a fórmula:
     * distância = √((x2-x1)² + (y2-y1)²)
     *
     * @param x1 coordenada X do primeiro ponto
     * @param y1 coordenada Y do primeiro ponto
     * @param x2 coordenada X do segundo ponto
     * @param y2 coordenada Y do segundo ponto
     * @return distância euclidiana entre os pontos
     */
    public double calcularDistancia(int x1, int y1, int x2, int y2) {
        double diferencaX = x2 - x1;
        double diferencaY = y2 - y1;

        return Math.sqrt(Math.pow(diferencaX, 2) + Math.pow(diferencaY, 2));
    }

    /**
     * Formata a distância com duas casas decimais usando ponto como separador
     *
     * @param distancia valor da distância a ser formatado
     * @return distância formatada como String
     */
    public String formatarDistancia(double distancia) {
        return String.format(Locale.US, "%.2f", distancia);
    }
}
