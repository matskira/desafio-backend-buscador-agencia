package br.com.santander.buscadora_agencia.bdd.steps;

import br.com.santander.buscadora_agencia.bdd.TestContext;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Steps para os cenários de cadastro de agências
 */
public class CadastrarAgenciaSteps {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TestContext testContext;

    private List<Response> responses = new ArrayList<>();
    private String endpoint;

    @Dado("que o endpoint {string} está disponível")
    public void queOEndpointEstaDisponivel(String endpoint) {
        this.endpoint = endpoint;
    }

    @Quando("envio uma requisição POST com body {}")
    public void envioUmaRequisicaoPostComBody(String body) {
        Response response = given()
                .contentType("application/json")
                .body(body)
                .when()
                .post(endpoint);
        testContext.setResponse(response);
    }

    @Quando("cadastro sequencialmente {int} agências com coordenadas diferentes")
    public void cadastroSequencialmenteAgenciasComCoordenadasDiferentes(int quantidade) {
        responses.clear();

        responses.add(given()
                .contentType("application/json")
                .body("{\"posX\": 10, \"posY\": 5}")
                .when()
                .post(endpoint));

        responses.add(given()
                .contentType("application/json")
                .body("{\"posX\": -5, \"posY\": -10}")
                .when()
                .post(endpoint));

        responses.add(given()
                .contentType("application/json")
                .body("{\"posX\": 0, \"posY\": 15}")
                .when()
                .post(endpoint));
    }

    @Quando("envio uma requisição GET para cadastrar")
    public void envioUmaRequisicaoGetParaCadastrar() {
        Response response = given()
                .when()
                .get("/desafio/cadastrar");
        testContext.setResponse(response);
    }


    @E("a agência deve ser persistida no banco de dados")
    public void aAgenciaDeveSerPersistidaNoBancoDeDados() {
        Long id = testContext.getResponse().jsonPath().getLong("id");
        assertNotNull(id, "ID da agência não deve ser nulo");

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM agencias WHERE id = ?",
                Integer.class,
                id);

        assertEquals(1, count, "Agência deve estar persistida no banco de dados");
    }

    @E("a resposta deve conter o identificador da agência cadastrada")
    public void aRespostaDeveConterOIdentificadorDaAgenciaCadastrada() {
        testContext.getResponse().then()
                .body("id", notNullValue())
                .body("nome", notNullValue())
                .body("posX", notNullValue())
                .body("posY", notNullValue());
    }

    @E("a agência deve ser persistida com coordenadas negativas")
    public void aAgenciaDeveSerPersistidaComCoordenadasNegativas() {
        testContext.getResponse().then()
                .body("posX", lessThan(0))
                .body("posY", lessThan(0));
    }

    @Então("todas devem retornar status code {int}")
    public void todasDevemRetornarStatusCode(int statusCode) {
        for (Response r : responses) {
            assertEquals(statusCode, r.getStatusCode());
        }
    }

    @E("todas devem ser persistidas no banco de dados")
    public void todasDevemSerPersitidasNoBancoDeDados() {
        for (Response r : responses) {
            Long id = r.jsonPath().getLong("id");
            Integer count = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM agencias WHERE id = ?",
                    Integer.class,
                    id);
            assertEquals(1, count);
        }
    }

    @E("a resposta deve conter mensagem de erro indicando campo obrigatório ausente")
    public void aRespostaDeveConterMensagemDeErroIndicandoCampoObrigatorioAusente() {
        String responseBody = testContext.getResponse().getBody().asString();
        assertTrue(responseBody.contains("Bad Request") ||
                   responseBody.contains("obrigatório") ||
                   responseBody.contains("required") ||
                   responseBody.contains("must not be null"),
                "Resposta deve indicar campo obrigatório ausente");
    }

    @E("a resposta deve indicar ausência de campos obrigatórios")
    public void aRespostaDeveIndicarAusenciaDeCamposObrigatorios() {
        aRespostaDeveConterMensagemDeErroIndicandoCampoObrigatorioAusente();
    }


    @E("a agência deve ser cadastrada na origem do plano cartesiano")
    public void aAgenciaDeveSerCadastradaNaOrigemDoPlanoCartesiano() {
        testContext.getResponse().then()
                .body("posX", equalTo(0))
                .body("posY", equalTo(0));
    }

    @E("a resposta deve indicar método não permitido")
    public void aRespostaDeveIndicarMetodoNaoPermitido() {
        String responseBody = testContext.getResponse().getBody().asString();
        assertTrue(responseBody.contains("Method Not Allowed") ||
                   responseBody.contains("405"),
                "Resposta deve indicar método não permitido");
    }
}
