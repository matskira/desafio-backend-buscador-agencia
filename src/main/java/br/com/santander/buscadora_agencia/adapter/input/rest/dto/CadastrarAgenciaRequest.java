package br.com.santander.buscadora_agencia.adapter.input.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de request para cadastro de agência
 */
@Schema(description = "Requisição para cadastrar uma nova agência com coordenadas X e Y")
public class CadastrarAgenciaRequest {

    @Schema(description = "Coordenada X da agência", example = "10", required = true)
    @NotNull(message = "Coordenada X é obrigatória")
    private Integer posX;

    @Schema(description = "Coordenada Y da agência", example = "-5", required = true)
    @NotNull(message = "Coordenada Y é obrigatória")
    private Integer posY;

    public CadastrarAgenciaRequest() {
    }

    public CadastrarAgenciaRequest(Integer posX, Integer posY) {
        this.posX = posX;
        this.posY = posY;
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
