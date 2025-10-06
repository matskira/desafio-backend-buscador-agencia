package br.com.santander.buscadora_agencia.application.usecase;

import br.com.santander.buscadora_agencia.domain.model.Agencia;
import br.com.santander.buscadora_agencia.ports.input.CadastrarAgenciaUseCase;
import br.com.santander.buscadora_agencia.ports.output.AgenciaRepository;
import org.springframework.stereotype.Service;

/**
 * Implementação do caso de uso de cadastrar agência
 */
@Service
public class CadastrarAgenciaUseCaseImpl implements CadastrarAgenciaUseCase {

    private final AgenciaRepository agenciaRepository;

    public CadastrarAgenciaUseCaseImpl(AgenciaRepository agenciaRepository) {
        this.agenciaRepository = agenciaRepository;
    }

    @Override
    public Agencia cadastrar(Integer posX, Integer posY) {
        // Validação de entrada
        if (posX == null) {
            throw new IllegalArgumentException("Coordenada X não pode ser nula");
        }
        if (posY == null) {
            throw new IllegalArgumentException("Coordenada Y não pode ser nula");
        }

        // Cria uma nova agência com ID nulo (será gerado pelo repositório)
        Agencia novaAgencia = new Agencia(null, posX, posY);

        // Salva no repositório e retorna a agência com ID gerado
        return agenciaRepository.salvar(novaAgencia);
    }
}
