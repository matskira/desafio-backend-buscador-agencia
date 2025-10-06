# language: pt
Funcionalidade: Buscar Agências por Distância
  Como usuário do sistema
  Eu quero buscar agências próximas à minha localização
  Para encontrar a agência mais conveniente

  Cenário: Consultar distância com uma agência cadastrada
    Dado que existe uma agência cadastrada em posX=10 e posY=4
    Quando envio uma requisição GET para "/desafio/distancia?posX=0&posY=0"
    Então o status code deve ser 200
    E a resposta deve conter a distância calculada para a agência
    E o resultado deve estar ordenado pela distância mais próxima

  Cenário: Consultar distância com múltiplas agências cadastradas
    Dado que existem 3 agências cadastradas nas posições (10,4), (-2,2), (-5,-2)
    Quando envio uma requisição GET para "/desafio/distancia?posX=0&posY=0"
    Então o status code deve ser 200
    E a resposta deve conter todas as agências com suas respectivas distâncias
    E os resultados devem estar ordenados da menor para a maior distância

  Cenário: Consultar distância sem agências cadastradas
    Dado que não existem agências cadastradas no sistema
    Quando envio uma requisição GET para "/desafio/distancia?posX=5&posY=5"
    Então o status code deve ser 200
    E a resposta deve retornar uma lista vazia ou mensagem apropriada

  Cenário: Consultar distância sem informar parâmetro posX
    Dado que existem agências cadastradas no sistema
    Quando envio uma requisição GET para "/desafio/distancia?posY=5"
    Então o status code deve ser 400
    E a resposta deve indicar parâmetro obrigatório ausente

  Cenário: Consultar distância sem informar parâmetro posY
    Dado que existem agências cadastradas no sistema
    Quando envio uma requisição GET para "/desafio/distancia?posX=10"
    Então o status code deve ser 400
    E a resposta deve indicar parâmetro obrigatório ausente

  Cenário: Consultar distância com coordenadas negativas do usuário
    Dado que existem agências cadastradas no sistema
    Quando envio uma requisição GET para "/desafio/distancia?posX=-10&posY=-5"
    Então o status code deve ser 200
    E a resposta deve calcular corretamente as distâncias para coordenadas negativas

  Cenário: Validar precisão do cálculo de distância euclidiana
    Dado que existe uma agência cadastrada em posX=3 e posY=4
    Quando envio uma requisição GET para "/desafio/distancia?posX=0&posY=0"
    Então o status code deve ser 200
    E a distância retornada deve ser "5.00"

  Cenário: Consultar distância com parâmetros de tipo inválido
    Dado que existem agências cadastradas no sistema
    Quando envio uma requisição GET para "/desafio/distancia?posX=texto&posY=texto"
    Então o status code deve ser 400
    E a resposta deve indicar erro de validação de tipo de dados

  Cenário: Validar formato do JSON de resposta de distância
    Dado que existem 2 agências cadastradas no sistema
    Quando envio uma requisição GET para "/desafio/distancia?posX=0&posY=0"
    Então o status code deve ser 200
    E a resposta deve estar no formato JSON válido
    E cada agência deve ter formato "distancia = X.XX"

  Cenário: Testar performance com grande volume de agências
    Dado que existem 100 agências cadastradas no sistema
    Quando envio uma requisição GET para "/desafio/distancia?posX=0&posY=0"
    Então o status code deve ser 200
    E o tempo de resposta deve ser inferior a 2 segundos
    E todas as agências devem estar ordenadas por distância
