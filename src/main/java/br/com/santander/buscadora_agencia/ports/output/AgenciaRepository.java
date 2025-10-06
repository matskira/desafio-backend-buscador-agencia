package br.com.santander.buscadora_agencia.ports.output;

import br.com.santander.buscadora_agencia.domain.model.Agencia;

import java.util.List;

/**
 * Porta de saída (output port) para persistência de agências
 * Define o contrato para operações de repositório
 */
public interface AgenciaRepository {

    /**
     * Salva uma nova agência no repositório
     *
     * @param agencia agência a ser salva
     * @return agência salva com ID gerado
     */
    Agencia salvar(Agencia agencia);

    /**
     * Busca todas as agências cadastradas
     *
     * @return lista de todas as agências
     */
    List<Agencia> buscarTodas();
}
