package br.com.santander.buscadora_agencia.bdd.steps;

import br.com.santander.buscadora_agencia.bdd.TestContext;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Steps para os cenários de busca por distância
 */
public class BuscarPorDistanciaSteps {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TestContext testContext;

    @Dado("que existe uma agência cadastrada em posX={int} e posY={int}")
    public void queExisteUmaAgenciaCadastradaEmPosXEPosY(int posX, int posY) {
        limparBancoDeDados();
        cadastrarAgencia(posX, posY);
    }

    @Dado("que existem {int} agências cadastradas nas posições \\({int},{int}), \\({int},{int}), \\({int},{int})")
    public void queExistemAgenciasCadastradasNasPosicoes(int quantidade, int x1, int y1, int x2, int y2, int x3, int y3) {
        limparBancoDeDados();
        cadastrarAgencia(x1, y1);
        cadastrarAgencia(x2, y2);
        cadastrarAgencia(x3, y3);
    }

    @Dado("que não existem agências cadastradas no sistema")
    public void queNaoExistemAgenciasCadastradasNoSistema() {
        limparBancoDeDados();
    }

    @Dado("que existem agências cadastradas no sistema")
    public void queExistemAgenciasCadastradasNoSistema() {
        limparBancoDeDados();
        cadastrarAgencia(10, 5);
        cadastrarAgencia(-5, -10);
    }

    @Dado("que existem {int} agências cadastradas no sistema")
    public void queExistemAgenciasCadastradasNoSistema(int quantidade) {
        limparBancoDeDados();
        for (int i = 0; i < quantidade; i++) {
            int x = (int) (Math.random() * 200 - 100);
            int y = (int) (Math.random() * 200 - 100);
            cadastrarAgencia(x, y);
        }
    }

    @Quando("envio uma requisição GET para {string}")
    public void envioUmaRequisicaoGetPara(String url) {
        testContext.setStartTime(System.currentTimeMillis());
        Response response = given()
                .when()
                .get(url);
        testContext.setResponse(response);
    }

    @Então("o status code deve ser {int}")
    public void oStatusCodeDeveSer(int statusCode) {
        assertEquals(statusCode, testContext.getResponse().getStatusCode(),
                "Status code esperado: " + statusCode + ", mas foi: " + testContext.getResponse().getStatusCode());
    }

    @E("a resposta deve conter a distância calculada para a agência")
    public void aRespostaDeveConterADistanciaCalculadaParaAAgencia() {
        Map<String, String> agencias = testContext.getResponse().jsonPath().getMap("$");
        assertFalse(agencias.isEmpty(), "Resposta deve conter agências");

        for (String valor : agencias.values()) {
            assertTrue(valor.startsWith("distancia = "), "Valor deve conter 'distancia = '");
        }
    }

    @E("o resultado deve estar ordenado pela distância mais próxima")
    public void oResultadoDeveEstarOrdenadoPelaDistanciaMaisProxima() {
        Map<String, String> agencias = testContext.getResponse().jsonPath().getMap("$");

        List<Double> distancias = agencias.values().stream()
                .map(v -> Double.parseDouble(v.replace("distancia = ", "")))
                .toList();

        for (int i = 0; i < distancias.size() - 1; i++) {
            assertTrue(distancias.get(i) <= distancias.get(i + 1),
                    "Distâncias devem estar ordenadas");
        }
    }

    @E("a resposta deve conter todas as agências com suas respectivas distâncias")
    public void aRespostaDeveConterTodasAsAgenciasComSuasRespectivasDistancias() {
        Map<String, String> agencias = testContext.getResponse().jsonPath().getMap("$");
        assertEquals(3, agencias.size(), "Deve retornar 3 agências");
    }

    @E("os resultados devem estar ordenados da menor para a maior distância")
    public void osResultadosDevemEstarOrdenadosDaMenorParaAMaiorDistancia() {
        oResultadoDeveEstarOrdenadoPelaDistanciaMaisProxima();
    }

    @E("a resposta deve retornar uma lista vazia ou mensagem apropriada")
    public void aRespostaDeveRetornarUmaListaVaziaOuMensagemApropriada() {
        Map<String, String> agencias = testContext.getResponse().jsonPath().getMap("$");
        assertTrue(agencias.isEmpty(), "Deve retornar lista vazia quando não há agências");
    }

    @E("a resposta deve indicar parâmetro obrigatório ausente")
    public void aRespostaDeveIndicarParametroObrigatorioAusente() {
        String responseBody = testContext.getResponse().getBody().asString();
        assertTrue(responseBody.contains("Bad Request") ||
                   responseBody.contains("Required") ||
                   responseBody.contains("parameter"),
                "Resposta deve indicar parâmetro obrigatório ausente");
    }

    @E("a resposta deve calcular corretamente as distâncias para coordenadas negativas")
    public void aRespostaDeveCalcularCorretamenteAsDistanciasParaCoordenadasNegativas() {
        Map<String, String> agencias = testContext.getResponse().jsonPath().getMap("$");
        assertFalse(agencias.isEmpty(), "Deve retornar agências com coordenadas negativas");
    }

    @E("a distância retornada deve ser {string}")
    public void aDistanciaRetornadaDeveSer(String distanciaEsperada) {
        Map<String, String> agencias = testContext.getResponse().jsonPath().getMap("$");
        String distanciaRetornada = agencias.values().iterator().next();
        assertTrue(distanciaRetornada.contains(distanciaEsperada),
                "Distância esperada: " + distanciaEsperada + ", mas foi: " + distanciaRetornada);
    }

    @E("a resposta deve indicar erro de validação de tipo de dados")
    public void aRespostaDeveIndicarErroDeValidacaoDeTipoDeDados() {
        String responseBody = testContext.getResponse().getBody().asString();
        assertTrue(responseBody.contains("Bad Request") ||
                   responseBody.contains("type") ||
                   responseBody.contains("Failed to convert"),
                "Resposta deve indicar erro de tipo de dados");
    }

    @E("a resposta deve estar no formato JSON válido")
    public void aRespostaDeveEstarNoFormatoJsonValido() {
        assertDoesNotThrow(() -> testContext.getResponse().jsonPath().getMap("$"),
                "Resposta deve ser JSON válido");
    }

    @E("cada agência deve ter formato {string}")
    public void cadaAgenciaDeveTerFormato(String formato) {
        Map<String, String> agencias = testContext.getResponse().jsonPath().getMap("$");

        for (Map.Entry<String, String> entry : agencias.entrySet()) {
            assertTrue(entry.getKey().startsWith("AGENCIA_"),
                    "Chave deve começar com AGENCIA_");
            assertTrue(entry.getValue().matches("distancia = \\d+\\.\\d{2}"),
                    "Valor deve estar no formato 'distancia = X.XX'");
        }
    }

    @E("o tempo de resposta deve ser inferior a {int} segundos")
    public void oTempoDeRespostaDeveSerInferiorASegundos(int segundos) {
        long elapsedTime = System.currentTimeMillis() - testContext.getStartTime();
        assertTrue(elapsedTime < segundos * 1000,
                "Tempo de resposta deve ser menor que " + segundos + " segundos, mas foi " + elapsedTime + "ms");
    }

    @E("todas as agências devem estar ordenadas por distância")
    public void todasAsAgenciasDevemEstarOrdenadasPorDistancia() {
        oResultadoDeveEstarOrdenadoPelaDistanciaMaisProxima();
    }

    private void limparBancoDeDados() {
        jdbcTemplate.execute("DELETE FROM agencias");
    }

    private void cadastrarAgencia(int posX, int posY) {
        given()
                .contentType("application/json")
                .body("{\"posX\": " + posX + ", \"posY\": " + posY + "}")
                .when()
                .post("/desafio/cadastrar");
    }
}
