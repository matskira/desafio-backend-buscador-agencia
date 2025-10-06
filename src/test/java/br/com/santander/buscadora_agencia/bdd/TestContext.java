package br.com.santander.buscadora_agencia.bdd;

import io.restassured.response.Response;
import org.springframework.stereotype.Component;

/**
 * Contexto compartilhado entre os steps do Cucumber
 */
@Component
public class TestContext {

    private Response response;
    private long startTime;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
