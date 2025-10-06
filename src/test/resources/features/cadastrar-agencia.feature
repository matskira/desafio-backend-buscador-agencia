# language: pt
Funcionalidade: Cadastrar Agência
  Como usuário do sistema
  Eu quero cadastrar agências bancárias com suas coordenadas
  Para poder buscar agências por distância posteriormente

  Contexto:
    Dado que o endpoint "/desafio/cadastrar" está disponível

  Cenário: Cadastrar agência com coordenadas válidas positivas
    Quando envio uma requisição POST com body {"posX": 10, "posY": 5}
    Então o status code deve ser 201
    E a agência deve ser persistida no banco de dados
    E a resposta deve conter o identificador da agência cadastrada

  Cenário: Cadastrar agência com coordenadas negativas
    Quando envio uma requisição POST com body {"posX": -10, "posY": -5}
    Então o status code deve ser 201
    E a agência deve ser persistida com coordenadas negativas

  Cenário: Cadastrar múltiplas agências com coordenadas distintas
    Quando cadastro sequencialmente 3 agências com coordenadas diferentes
    Então todas devem retornar status code 201
    E todas devem ser persistidas no banco de dados

  Cenário: Tentar cadastrar agência sem informar posX
    Quando envio uma requisição POST com body {"posY": 5}
    Então o status code deve ser 400
    E a resposta deve conter mensagem de erro indicando campo obrigatório ausente

  Cenário: Tentar cadastrar agência sem informar posY
    Quando envio uma requisição POST com body {"posX": 10}
    Então o status code deve ser 400
    E a resposta deve conter mensagem de erro indicando campo obrigatório ausente

  Cenário: Tentar cadastrar agência com body vazio
    Quando envio uma requisição POST com body {}
    Então o status code deve ser 400
    E a resposta deve indicar ausência de campos obrigatórios

  Cenário: Tentar cadastrar agência com tipos de dados inválidos
    Quando envio uma requisição POST com body {"posX": "texto", "posY": "texto"}
    Então o status code deve ser 400
    E a resposta deve indicar erro de validação de tipo de dados

  Cenário: Cadastrar agência com coordenadas zero
    Quando envio uma requisição POST com body {"posX": 0, "posY": 0}
    Então o status code deve ser 201
    E a agência deve ser cadastrada na origem do plano cartesiano

  Cenário: Tentar cadastrar agência com método HTTP incorreto
    Quando envio uma requisição GET para cadastrar
    Então o status code deve ser 405
    E a resposta deve indicar método não permitido
