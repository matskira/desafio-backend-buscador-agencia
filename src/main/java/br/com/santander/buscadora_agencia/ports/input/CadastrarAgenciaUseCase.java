package br.com.santander.buscadora_agencia.ports.input;

import br.com.santander.buscadora_agencia.domain.model.Agencia;

/**
 * Porta de entrada (input port) para o caso de uso de cadastrar agência
 */
public interface CadastrarAgenciaUseCase {

    /**
     * Cadastra uma nova agência com coordenadas X e Y
     *
     * @param posX coordenada X da agência
     * @param posY coordenada Y da agência
     * @return agência cadastrada
     */
    Agencia cadastrar(Integer posX, Integer posY);
}
