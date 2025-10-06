package br.com.santander.buscadora_agencia.adapter.output.persistence;

import br.com.santander.buscadora_agencia.domain.model.Agencia;
import br.com.santander.buscadora_agencia.ports.output.AgenciaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Adaptador que implementa AgenciaRepository usando Spring Data JPA
 */
@Component
public class AgenciaRepositoryAdapter implements AgenciaRepository {

    private final AgenciaJpaRepository agenciaJpaRepository;

    public AgenciaRepositoryAdapter(AgenciaJpaRepository agenciaJpaRepository) {
        this.agenciaJpaRepository = agenciaJpaRepository;
    }

    @Override
    public Agencia salvar(Agencia agencia) {
        AgenciaEntity entity = toEntity(agencia);
        AgenciaEntity savedEntity = agenciaJpaRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public List<Agencia> buscarTodas() {
        return agenciaJpaRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    /**
     * Converte entidade de domínio para entidade JPA
     */
    private AgenciaEntity toEntity(Agencia agencia) {
        return new AgenciaEntity(agencia.getId(), agencia.getPosX(), agencia.getPosY());
    }

    /**
     * Converte entidade JPA para entidade de domínio
     */
    private Agencia toDomain(AgenciaEntity entity) {
        return new Agencia(entity.getId(), entity.getPosX(), entity.getPosY());
    }
}
