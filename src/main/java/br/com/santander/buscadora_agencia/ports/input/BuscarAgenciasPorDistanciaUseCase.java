package br.com.santander.buscadora_agencia.ports.input;

import java.util.Map;

/**
 * Porta de entrada (input port) para o caso de uso de buscar agências por distância
 */
public interface BuscarAgenciasPorDistanciaUseCase {

    /**
     * Busca todas as agências e retorna ordenadas por distância (mais próxima primeiro)
     * em relação a uma posição fornecida
     *
     * @param posX coordenada X da posição do usuário
     * @param posY coordenada Y da posição do usuário
     * @return mapa com nome da agência e distância formatada, ordenado por distância
     */
    Map<String, String> buscarPorDistancia(Integer posX, Integer posY);
}
