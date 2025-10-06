package br.com.santander.buscadora_agencia.adapter.input.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO de response para agência
 */
@Schema(description = "Resposta contendo os dados de uma agência")
public class AgenciaResponse {

    @Schema(description = "ID da agência", example = "1")
    private Long id;

    @Schema(description = "Nome da agência", example = "AGENCIA_1")
    private String nome;

    @Schema(description = "Coordenada X da agência", example = "10")
    private Integer posX;

    @Schema(description = "Coordenada Y da agência", example = "-5")
    private Integer posY;

    public AgenciaResponse() {
    }

    public AgenciaResponse(Long id, String nome, Integer posX, Integer posY) {
        this.id = id;
        this.nome = nome;
        this.posX = posX;
        this.posY = posY;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getPosX() {
        return posX;
    }

    public void setPosX(Integer posX) {
        this.posX = posX;
    }

    public Integer getPosY() {
        return posY;
    }

    public void setPosY(Integer posY) {
        this.posY = posY;
    }
}
