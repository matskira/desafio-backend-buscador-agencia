## Cenários do Endpoint POST /desafio/cadastrar

### Cenário 1: Cadastrar agência com coordenadas válidas positivas

```gherkin
Given que o endpoint "/desafio/cadastrar" está disponível
When envio uma requisição POST com body {"posX": 10, "posY": 5}
Then o status code deve ser 201
And a agência deve ser persistida no banco de dados
And a resposta deve conter o identificador da agência cadastrada
```


### Cenário 2: Cadastrar agência com coordenadas negativas

```gherkin
Given que o endpoint "/desafio/cadastrar" está disponível
When envio uma requisição POST com body {"posX": -10, "posY": -5}
Then o status code deve ser 201
And a agência deve ser persistida com coordenadas negativas
```


### Cenário 3: Cadastrar múltiplas agências com coordenadas distintas

```gherkin
Given que o endpoint "/desafio/cadastrar" está disponível
When cadastro sequencialmente 3 agências com coordenadas diferentes
Then todas devem retornar status code 201
And todas devem ser persistidas no banco de dados
```


### Cenário 4: Tentar cadastrar agência sem informar posX

```gherkin
Given que o endpoint "/desafio/cadastrar" está disponível
When envio uma requisição POST com body {"posY": 5}
Then o status code deve ser 400
And a resposta deve conter mensagem de erro indicando campo obrigatório ausente
```


### Cenário 5: Tentar cadastrar agência sem informar posY

```gherkin
Given que o endpoint "/desafio/cadastrar" está disponível
When envio uma requisição POST com body {"posX": 10}
Then o status code deve ser 400
And a resposta deve conter mensagem de erro indicando campo obrigatório ausente
```


### Cenário 6: Tentar cadastrar agência com body vazio

```gherkin
Given que o endpoint "/desafio/cadastrar" está disponível
When envio uma requisição POST com body {}
Then o status code deve ser 400
And a resposta deve indicar ausência de campos obrigatórios
```


### Cenário 7: Tentar cadastrar agência com tipos de dados inválidos

```gherkin
Given que o endpoint "/desafio/cadastrar" está disponível
When envio uma requisição POST com body {"posX": "texto", "posY": "texto"}
Then o status code deve ser 400
And a resposta deve indicar erro de validação de tipo de dados
```


### Cenário 8: Cadastrar agência com coordenadas zero

```gherkin
Given que o endpoint "/desafio/cadastrar" está disponível
When envio uma requisição POST com body {"posX": 0, "posY": 0}
Then o status code deve ser 201
And a agência deve ser cadastrada na origem do plano cartesiano
```


### Cenário 9: Cadastrar agência com coordenadas decimais

```gherkin
Given que o endpoint "/desafio/cadastrar" está disponível
When envio uma requisição POST com body {"posX": 10.5, "posY": -7.3}
Then o status code deve ser 201
And a agência deve ser persistida com valores decimais
```


### Cenário 10: Tentar cadastrar agência com método HTTP incorreto

```gherkin
Given que o endpoint "/desafio/cadastrar" está disponível
When envio uma requisição GET para "/desafio/cadastrar"
Then o status code deve ser 405
And a resposta deve indicar método não permitido
```


## Cenários do Endpoint GET /desafio/distancia

### Cenário 11: Consultar distância com uma agência cadastrada

```gherkin
Given que existe uma agência cadastrada em posX=10 e posY=4
When envio uma requisição GET para "/desafio/distancia?posX=0&posY=0"
Then o status code deve ser 200
And a resposta deve conter a distância calculada para a agência
And o resultado deve estar ordenado pela distância mais próxima
```


### Cenário 12: Consultar distância com múltiplas agências cadastradas

```gherkin
Given que existem 3 agências cadastradas nas posições (10,4), (-2,2), (-5,-2)
When envio uma requisição GET para "/desafio/distancia?posX=0&posY=0"
Then o status code deve ser 200
And a resposta deve conter todas as agências com suas respectivas distâncias
And os resultados devem estar ordenados da menor para a maior distância
```


### Cenário 13: Consultar distância sem agências cadastradas

```gherkin
Given que não existem agências cadastradas no sistema
When envio uma requisição GET para "/desafio/distancia?posX=5&posY=5"
Then o status code deve ser 200
And a resposta deve retornar uma lista vazia ou mensagem apropriada
```


### Cenário 14: Consultar distância sem informar parâmetro posX

```gherkin
Given que existem agências cadastradas no sistema
When envio uma requisição GET para "/desafio/distancia?posY=5"
Then o status code deve ser 400
And a resposta deve indicar parâmetro obrigatório ausente
```


### Cenário 15: Consultar distância sem informar parâmetro posY

```gherkin
Given que existem agências cadastradas no sistema
When envio uma requisição GET para "/desafio/distancia?posX=10"
Then o status code deve ser 400
And a resposta deve indicar parâmetro obrigatório ausente
```


### Cenário 16: Consultar distância com coordenadas negativas do usuário

```gherkin
Given que existem agências cadastradas no sistema
When envio uma requisição GET para "/desafio/distancia?posX=-10&posY=-5"
Then o status code deve ser 200
And a resposta deve calcular corretamente as distâncias para coordenadas negativas
```


### Cenário 17: Validar precisão do cálculo de distância euclidiana

```gherkin
Given que existe uma agência cadastrada em posX=3 e posY=4
When envio uma requisição GET para "/desafio/distancia?posX=0&posY=0"
Then o status code deve ser 200
And a distância retornada deve ser 5.0
```


### Cenário 18: Consultar distância com parâmetros de tipo inválido

```gherkin
Given que existem agências cadastradas no sistema
When envio uma requisição GET para "/desafio/distancia?posX=texto&posY=texto"
Then o status code deve ser 400
And a resposta deve indicar erro de validação de tipo de dados
```


### Cenário 19: Validar formato do JSON de resposta de distância

```gherkin
Given que existem 2 agências cadastradas no sistema
When envio uma requisição GET para "/desafio/distancia?posX=0&posY=0"
Then o status code deve ser 200
And a resposta deve estar no formato JSON válido
And cada agência deve ter formato "AGENCIA_N": "distancia = X.X"
```


### Cenário 20: Testar performance com grande volume de agências

```gherkin
Given que existem 1000 agências cadastradas no sistema
When envio uma requisição GET para "/desafio/distancia?posX=0&posY=0"
Then o status code deve ser 200
And o tempo de resposta deve ser inferior a 2 segundos
And todas as agências devem estar ordenadas por distância
```


