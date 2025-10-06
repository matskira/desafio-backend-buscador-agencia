# API Buscadora de Agências - Desafio Santander

API REST desenvolvida para o Cliente Santander que realiza cadastramento de agências bancárias e busca por distância euclidiana.

## 🏗️ Arquitetura

O projeto implementa **Clean Architecture** combinada com **Hexagonal Architecture (Ports and Adapters)**:

- **Domain** - Núcleo da aplicação, sem dependências externas
- **Application** - Casos de uso (orquestração da lógica de negócio)
- **Ports** - Interfaces que definem contratos (input/output)
- **Adapters** - Implementações concretas (REST, JPA, etc.)

### Estrutura de Pacotes

```
src/main/java/br/com/santander/buscadora_agencia/
├── domain/                     # Camada de Domínio
│   ├── model/                  # Entidades de domínio
│   └── service/                # Serviços de domínio
├── application/                # Camada de Aplicação
│   └── usecase/               # Casos de uso
├── ports/                      # Portas (interfaces)
│   ├── input/                 # Portas de entrada
│   └── output/                # Portas de saída
├── adapter/                    # Adaptadores
│   ├── input/rest/            # Controllers REST
│   └── output/persistence/    # Repositórios JPA
└── config/                     # Configurações
```

## 🚀 Tecnologias

- **Java 17**
- **Spring Boot 3.4.10**
- **Spring Data JPA**
- **H2 Database** (in-memory)
- **SpringDoc OpenAPI 3** (Swagger)
- **JUnit 5** + **Mockito** (testes unitários)
- **Cucumber** + **REST Assured** (testes BDD/E2E)
- **JaCoCo** (cobertura de testes)
- **Maven**

## 📋 Requisitos

- Java 17
- Maven 3.6+ (ou usar o Maven Wrapper incluído)

## ⚙️ Executar a Aplicação

### Usando Maven Wrapper (recomendado)

```bash
# Windows
mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

### Usando Maven instalado globalmente

```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## 📚 Documentação da API

### Swagger UI
Acesse a documentação interativa em: **http://localhost:8080/swagger-ui.html**

### OpenAPI JSON
Especificação OpenAPI 3 disponível em: **http://localhost:8080/v3/api-docs**

## 🔗 Endpoints

### 1. Cadastrar Agência

**POST** `/desafio/cadastrar`

Cadastra uma nova agência com coordenadas X e Y.

**Request Body:**
```json
{
  "posX": 10,
  "posY": -5
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "nome": "AGENCIA_1",
  "posX": 10,
  "posY": -5
}
```

### 2. Buscar Agências por Distância

**GET** `/desafio/distancia?posX={x}&posY={y}`

Retorna todas as agências ordenadas por distância euclidiana (mais próxima primeiro).

**Parâmetros:**
- `posX` (obrigatório): Coordenada X da posição do usuário
- `posY` (obrigatório): Coordenada Y da posição do usuário

**Response (200 OK):**
```json
{
  "AGENCIA_3": "distancia = 1.00",
  "AGENCIA_1": "distancia = 10.00",
  "AGENCIA_2": "distancia = 13.89",
  "AGENCIA_4": "distancia = 16.55"
}
```

### Exemplo de uso com cURL

```bash
# Cadastrar agências
curl -X POST http://localhost:8080/desafio/cadastrar \
  -H "Content-Type: application/json" \
  -d '{"posX": 10, "posY": -5}'

curl -X POST http://localhost:8080/desafio/cadastrar \
  -H "Content-Type: application/json" \
  -d '{"posX": 10, "posY": 4}'

# Buscar por distância
curl "http://localhost:8080/desafio/distancia?posX=10&posY=5"
```

## 🧪 Testes

### Executar todos os testes

```bash
./mvnw test
```

### Executar testes com relatório de cobertura

```bash
./mvnw clean test jacoco:report
```

O relatório HTML estará disponível em: `target/site/jacoco/index.html`

### Cobertura de Testes

- **Cobertura total: 95%** ✅
- **Meta mínima: 90%** ✅

### Tipos de Testes Implementados

- **Testes Unitários**: Entidades, serviços, casos de uso (com Mockito)
- **Testes de Integração**: Repositórios JPA com H2 (`@DataJpaTest`)
- **Testes de Controller**: MockMvc (`@WebMvcTest`)
- **Testes BDD/E2E**: Cucumber + REST Assured (19 cenários)

### Executar Testes BDD (Cucumber)

Os testes BDD (Behavior-Driven Development) estão escritos em **Gherkin** (português) e executam cenários end-to-end completos:

```bash
# Executar apenas os testes BDD
./mvnw test -Dtest=CucumberTest

# Executar com relatório detalhado
./mvnw clean test -Dtest=CucumberTest
```

**Relatórios Cucumber gerados:**
- **HTML**: `target/cucumber-reports/cucumber.html` (abra no navegador)
- **JSON**: `target/cucumber-reports/cucumber.json`

**Cenários cobertos (19 cenários):**

**Cadastro de Agências:**
- ✅ Cadastrar agência com coordenadas válidas positivas
- ✅ Cadastrar agência com coordenadas negativas
- ✅ Cadastrar múltiplas agências com coordenadas distintas
- ✅ Validação de campos obrigatórios (posX, posY)
- ✅ Validação de tipos de dados
- ✅ Validação de métodos HTTP permitidos

**Busca por Distância:**
- ✅ Consultar distância com uma agência cadastrada
- ✅ Consultar distância com múltiplas agências
- ✅ Consultar distância sem agências cadastradas
- ✅ Validação de parâmetros obrigatórios
- ✅ Cálculo correto com coordenadas negativas
- ✅ Precisão do cálculo euclidiano (ex: 3,4 → 5.00)
- ✅ Validação do formato JSON de resposta
- ✅ Teste de performance (100 agências < 2s)
- ✅ Ordenação correta por distância

## 🗄️ Banco de Dados

### H2 In-Memory

O projeto usa H2 Database em memória para facilitar desenvolvimento e testes.

**Console H2** (opcional): `http://localhost:8080/h2-console`

**Configurações:**
- URL: `jdbc:h2:mem:agenciasdb`
- Driver: `org.h2.Driver`
- Username: `sa`
- Password: _(vazio)_

## 📐 Cálculo de Distância

A API utiliza a **fórmula euclidiana** para calcular distância entre dois pontos:

```
distância = √((x₂-x₁)² + (y₂-y₁)²)
```

Implementado em: [`CalculadoraDistanciaService`](src/main/java/br/com/santander/buscadora_agencia/domain/service/CalculadoraDistanciaService.java)

## 🛠️ Build e Deploy

### Build completo

```bash
./mvnw clean package
```

### Build sem testes

```bash
./mvnw clean package -DskipTests
```

O JAR executável estará em: `target/srv-buscadora-agencia-0.0.1-SNAPSHOT.jar`

### Executar JAR

```bash
java -jar target/srv-buscadora-agencia-0.0.1-SNAPSHOT.jar
```

## 👥 Autor

Desenvolvido para o **Cliente Santander** como parte do Desafio OSA!

---
