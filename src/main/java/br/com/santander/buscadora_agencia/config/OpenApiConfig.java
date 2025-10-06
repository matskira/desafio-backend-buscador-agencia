package br.com.santander.buscadora_agencia.config;

import br.com.santander.buscadora_agencia.domain.service.CalculadoraDistanciaService;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do SpringDoc OpenAPI 3 (Swagger) e beans de domínio
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Buscadora de Agências - Desafio Santander")
                        .version("1.0.0")
                        .description("API REST para cadastro de agências bancárias e busca por distância euclidiana. " +
                                "Desenvolvida para o Cliente Santander usando Clean Architecture + Hexagonal Architecture.")
                        .contact(new Contact()
                                .name("Equipe Santander")
                                .email("contato@santander.com.br")));
    }

    /**
     * Bean do serviço de domínio para cálculo de distância
     * Registrado aqui para evitar anotações Spring na camada de domínio (Clean Architecture)
     */
    @Bean
    public CalculadoraDistanciaService calculadoraDistanciaService() {
        return new CalculadoraDistanciaService();
    }
}
