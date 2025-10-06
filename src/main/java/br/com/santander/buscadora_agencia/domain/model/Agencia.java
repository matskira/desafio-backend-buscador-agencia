package br.com.santander.buscadora_agencia.domain.model;

import java.util.Objects;

/**
 * Entidade de domínio que representa uma Agência bancária com coordenadas X e Y
 */
public class Agencia {

    private final Long id;
    private final Integer posX;
    private final Integer posY;
    private final String nome;

    public Agencia(Long id, Integer posX, Integer posY) {
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        this.nome = id != null ? "AGENCIA_" + id : "AGENCIA_PENDENTE";
    }

    public Long getId() {
        return id;
    }

    public Integer getPosX() {
        return posX;
    }

    public Integer getPosY() {
        return posY;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agencia agencia = (Agencia) o;
        return Objects.equals(id, agencia.id) &&
               Objects.equals(posX, agencia.posX) &&
               Objects.equals(posY, agencia.posY);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, posX, posY);
    }

    @Override
    public String toString() {
        return "Agencia{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", posX=" + posX +
                ", posY=" + posY +
                '}';
    }
}
