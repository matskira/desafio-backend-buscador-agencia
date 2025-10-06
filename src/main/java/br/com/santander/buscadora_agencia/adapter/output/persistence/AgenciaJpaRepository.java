package br.com.santander.buscadora_agencia.adapter.output.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório JPA Spring Data para AgenciaEntity
 */
@Repository
public interface AgenciaJpaRepository extends JpaRepository<AgenciaEntity, Long> {
}
