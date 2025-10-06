package br.com.santander.buscadora_agencia.adapter.input.rest;

import br.com.santander.buscadora_agencia.domain.model.Agencia;
import br.com.santander.buscadora_agencia.ports.input.BuscarAgenciasPorDistanciaUseCase;
import br.com.santander.buscadora_agencia.ports.input.CadastrarAgenciaUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes de integração do DesafioController com MockMvc
 */
@WebMvcTest(DesafioController.class)
class DesafioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CadastrarAgenciaUseCase cadastrarAgenciaUseCase;

    @MockitoBean
    private BuscarAgenciasPorDistanciaUseCase buscarAgenciasPorDistanciaUseCase;

    @Test
    void deveCadastrarAgenciaComSucesso() throws Exception {
        // Arrange
        String requestBody = "{\"posX\": 10, \"posY\": -5}";
        Agencia agenciaEsperada = new Agencia(1L, 10, -5);

        when(cadastrarAgenciaUseCase.cadastrar(10, -5)).thenReturn(agenciaEsperada);

        // Act & Assert
        mockMvc.perform(post("/desafio/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("AGENCIA_1"))
                .andExpect(jsonPath("$.posX").value(10))
                .andExpect(jsonPath("$.posY").value(-5));

        verify(cadastrarAgenciaUseCase, times(1)).cadastrar(10, -5);
    }

    @Test
    void deveRetornarBadRequestQuandoCoordenadaNula() throws Exception {
        // Arrange
        String requestBody = "{\"posX\": 10}";

        // Act & Assert
        mockMvc.perform(post("/desafio/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());

        verify(cadastrarAgenciaUseCase, never()).cadastrar(anyInt(), anyInt());
    }

    @Test
    void deveBuscarAgenciasPorDistanciaComSucesso() throws Exception {
        // Arrange
        Map<String, String> resultado = new LinkedHashMap<>();
        resultado.put("AGENCIA_2", "distancia = 2.2");
        resultado.put("AGENCIA_1", "distancia = 10.0");
        resultado.put("AGENCIA_3", "distancia = 37.42");

        when(buscarAgenciasPorDistanciaUseCase.buscarPorDistancia(10, 5)).thenReturn(resultado);

        // Act & Assert
        mockMvc.perform(get("/desafio/distancia")
                        .param("posX", "10")
                        .param("posY", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.AGENCIA_2").value("distancia = 2.2"))
                .andExpect(jsonPath("$.AGENCIA_1").value("distancia = 10.0"))
                .andExpect(jsonPath("$.AGENCIA_3").value("distancia = 37.42"));

        verify(buscarAgenciasPorDistanciaUseCase, times(1)).buscarPorDistancia(10, 5);
    }

    @Test
    void deveRetornarMapaVazioQuandoNaoHouverAgencias() throws Exception {
        // Arrange
        when(buscarAgenciasPorDistanciaUseCase.buscarPorDistancia(10, 5))
                .thenReturn(new LinkedHashMap<>());

        // Act & Assert
        mockMvc.perform(get("/desafio/distancia")
                        .param("posX", "10")
                        .param("posY", "5"))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

        verify(buscarAgenciasPorDistanciaUseCase, times(1)).buscarPorDistancia(10, 5);
    }

    @Test
    void deveRetornarBadRequestQuandoParametrosFaltando() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/desafio/distancia")
                        .param("posX", "10"))
                .andExpect(status().isBadRequest());

        verify(buscarAgenciasPorDistanciaUseCase, never()).buscarPorDistancia(anyInt(), anyInt());
    }
}
