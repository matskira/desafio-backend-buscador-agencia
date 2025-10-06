package br.com.santander.buscadora_agencia.adapter.input.rest;

import br.com.santander.buscadora_agencia.adapter.input.rest.dto.AgenciaResponse;
import br.com.santander.buscadora_agencia.adapter.input.rest.dto.CadastrarAgenciaRequest;
import br.com.santander.buscadora_agencia.domain.model.Agencia;
import br.com.santander.buscadora_agencia.ports.input.BuscarAgenciasPorDistanciaUseCase;
import br.com.santander.buscadora_agencia.ports.input.CadastrarAgenciaUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller REST para endpoints do desafio de agências
 */
@RestController
@RequestMapping("/desafio")
@Tag(name = "Desafio Santander", description = "API para cadastro de agências e busca por distância")
public class DesafioController {

    private final CadastrarAgenciaUseCase cadastrarAgenciaUseCase;
    private final BuscarAgenciasPorDistanciaUseCase buscarAgenciasPorDistanciaUseCase;

    public DesafioController(
            CadastrarAgenciaUseCase cadastrarAgenciaUseCase,
            BuscarAgenciasPorDistanciaUseCase buscarAgenciasPorDistanciaUseCase) {
        this.cadastrarAgenciaUseCase = cadastrarAgenciaUseCase;
        this.buscarAgenciasPorDistanciaUseCase = buscarAgenciasPorDistanciaUseCase;
    }

    @PostMapping("/cadastrar")
    @Operation(
            summary = "Cadastrar nova agência",
            description = "Cadastra uma nova agência com coordenadas X e Y no banco de dados em memória"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Agência cadastrada com sucesso",
                    content = @Content(schema = @Schema(implementation = AgenciaResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida"
            )
    })
    public ResponseEntity<AgenciaResponse> cadastrarAgencia(
            @Valid @RequestBody CadastrarAgenciaRequest request) {

        Agencia agenciaCadastrada = cadastrarAgenciaUseCase.cadastrar(
                request.getPosX(),
                request.getPosY()
        );

        AgenciaResponse response = new AgenciaResponse(
                agenciaCadastrada.getId(),
                agenciaCadastrada.getNome(),
                agenciaCadastrada.getPosX(),
                agenciaCadastrada.getPosY()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/distancia")
    @Operation(
            summary = "Buscar agências por distância",
            description = "Retorna todas as agências ordenadas por distância (mais próxima primeiro) em relação à posição fornecida"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de agências ordenadas por distância",
                    content = @Content(schema = @Schema(implementation = Map.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parâmetros inválidos"
            )
    })
    public ResponseEntity<Map<String, String>> buscarPorDistancia(
            @Parameter(description = "Coordenada X da posição do usuário", example = "10", required = true)
            @RequestParam Integer posX,

            @Parameter(description = "Coordenada Y da posição do usuário", example = "5", required = true)
            @RequestParam Integer posY) {

        Map<String, String> agenciasComDistancia = buscarAgenciasPorDistanciaUseCase.buscarPorDistancia(posX, posY);

        return ResponseEntity.ok(agenciasComDistancia);
    }
}
