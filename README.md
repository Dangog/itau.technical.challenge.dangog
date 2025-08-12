# Desafio Técnico Backend Itaú

## Visão Geral

Este repositório contém a solução para o desafio técnico de backend (teoricamente) do Itaú. O objetivo foi desenvolver uma API REST para gerenciar transações financeiras, com 3 endpoints focando no Cadastro, Remoção e Listagem das transações com base nas regras mencionadas.

A página para o desafio pode ser encontrada em: https://github.com/feltex/desafio-itau-backend

## Estrutura do Projeto

A aplicação foi construída com Java 17 e Spring Boot, utilizando o Jakarta Bean Validation para garantir a integridade dos dados, o Java Records para modelagem de DTOs e o Project Lombok para reduzir o código repetitivo (boilerplate), posteriormente podem ser adicionados outras dependências para a criação dos desafios na seção "extra" descritos.

## Tecnologias e Dependências

-   **Linguagem:** Java 17
-   **Framework:** Spring Boot 3.2.0
-   **Gerenciador de Dependências:** Maven
-   **Validação:** Jakarta Bean Validation (Hibernate Validator)
-   **Coleções Seguras:** `java.util.concurrent` (ConcurrentHashMap)
-   **prdutividade:** Project Lombok

A solução segue o paradigma MVC (Model-View-Controller)

## Endpoints da API

A API expõe os seguintes endpoints:

### POST `/transacao`

-   **Descrição:** Cria uma nova transação.
-   **Corpo da Requisição:**
    ```json
    {
      "valor": 123.45,
      "dataHora": "2020-08-07T12:34:56.789-03:00"
    }
    ```
-   **Respostas:**
    -   `201 Created`: Transação criada com sucesso.
    -   `400 Bad Request`: Falha na validação dos dados da requisição.

### DELETE `/transacao`

-   **Descrição:** Apaga todas as transações da base de dados in-memory.
-   **Respostas:**
    -   `204 No Content`: Operação concluída com sucesso.

### GET `/estatistica`

-   **Descrição:** Retorna as estatísticas das transações do último minuto.
-   **Respostas:**
    -   `200 OK`: Retorna um JSON com as estatísticas.
        ```json
        {
          "sum": "123.45",
          "avg": "123.45",
          "max": "123.45",
          "min": "123.45",
          "count": 1
        }
        ```

### GET `/transacao` (Não foi solicitado ao desafio)

-   **Descrição:** Retorna todas as transações salvas em memória.
  -   **Respostas:**
      -   `200 OK`: Retorna um JSON com as transações.
          ```json
          [
             {
                    "valor": 1,
                    "dataHora": "2025-08-11T22:56:00Z"
              },
              {
                    "valor": 12398.50,
                    "dataHora": "2025-08-11T22:56:00Z"
             },
              {
                    "valor": 12398193,
                    "dataHora": "2025-08-11T22:56:00Z"
             }
          ]
          ```

## Como Rodar a Aplicação

Este projeto utiliza o **Maven Wrapper** (`mvnw`), que garante que todos os desenvolvedores usem a mesma versão do Maven sem a necessidade de uma instalação manual. Os comandos abaixo devem funcionar em qualquer ambiente com o Java v17 instalado.

O projeto foi configurado com dois perfis (*profiles*) de execução do Spring, que alteram o nível dos logs:

-   **`prd` (Padrão):** Roda com logs no nível `INFO`, ideal para um ambiente de produção (usando o arquivo `application-prd.properties`).
-   **`dev` (Desenvolvimento):** Roda com logs no nível `DEBUG`, mostrando informações detalhadas, úteis para o desenvolvimento (usando o arquivo `application-dev.properties`).

Siga os passos abaixo para executar:

1.  **Clonar o Repositório:**
    ```bash
    git clone [https://github.com/Dangog/itau.technical.challenge.dangog.git](https://github.com/Dangog/itau.technical.challenge.dangog.git)
    ```

2.  **Navegar até a pasta do projeto:**
    ```bash
    cd itau.technical.challenge.dangog
    ```

3.  **Compilar e Rodar usando o Maven Wrapper:**
    *Nota: Em terminais Linux/macOS, use `./mvnw`. No Windows (CMD/PowerShell), você pode usar `mvnw` ou `.\mvnw`.*

    * **Para rodar em modo de Produção (Logs `INFO`):**
      O perfil `prd` é o padrão.
        ```bash
        # O perfil 'prd' será ativado por padrão
        ./mvnw spring-boot:run
        ```

    * **Para rodar em modo de Desenvolvimento (Logs `DEBUG`):**
      Use o seguinte comando para ativar o perfil `dev`.
        ```bash
        ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
        ```

A aplicação estará disponível em `http://localhost:8080` em ambos os casos.

*Posteriormente será disponibilizado nessa ou em outra branch a subida dessa projeto com containers usando docker.

## Documentação da API (Swagger)

Este projeto utiliza `springdoc-openapi` para gerar uma documentação interativa da API em tempo real.

Após iniciar a aplicação, a documentação pode ser acessada através dos seguintes links:

-   **Swagger UI (Interface Gráfica):** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
-   **OpenAPI Spec (JSON cru):** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

A interface do Swagger permite visualizar todos os endpoints, seus parâmetros, corpos de requisição e respostas, além de permitir a execução de testes diretamente pelo navegador.

## Monitoramento e Saúde da Aplicação (Actuator)

O projeto utiliza o **Spring Boot Actuator** para expor informações operacionais essenciais.

### Health Check

Este endpoint é fundamental para verificar se a aplicação está em um estado saudável e pronta para receber tráfego.

-   **Endpoint:** `GET /actuator/health`
-   **Descrição:** Verifica a saúde geral da aplicação, incluindo o status de componentes como espaço em disco e outros `HealthIndicators` customizados.
-   **Respostas:**
    -   `200 OK`: A aplicação e todos os seus componentes essenciais estão saudáveis (`UP`).
    -   `503 Service Unavailable`: A aplicação ou um de seus componentes essenciais está com problemas (`DOWN`).
    - 
-   **Exemplo de Corpo da Resposta (UP):**
    ```json
    {
        "status": "UP",
        "components": {
            "diskSpace": {
                "status": "UP",
                "details": {
                    "total": 480085274624,
                    "free": 28547436544,
                    "threshold": 10485760,
                    "path": "\\itau.technical.challenge.dangog\\.",
                    "exists": true
                }
            },
            "healthCheck": {
                "status": "UP",
                "details": {
                    "service": "TransactionService is available"
                }
            },
            "ping": {
                "status": "UP"
            },
            "ssl": {
                "status": "UP",
                "details": {
                    "validChains": [],
                    "invalidChains": []
                }
            }
        }
    }
    ```

## FAQ e principais decisões:

-   **1 - Porque foi utilizado ConcurrentHashMap ao invés de outra estrutura de dados?**
    R: Segurança de Concorrência, para o armazenamento in-memory, foi usado o `ConcurrentHashMap`. Essa coleção, otimizada para ambientes multi-threaded, garante a integridade dos dados sem comprometer a performance, além de poder adicioanr um conjunto "ID:Objeto" para gerenciamento por ID's únicos das transações.

-   **2 - Porque a utilização do 'BigDecimal' ao invés de 'double' ou outros tipos de variáveis?**
    R: Valores Monetários, para evitar imprecisão em cálculos de ponto flutuante, o `BigDecimal` foi usado para o campo `valor`, garantindo cálculos financeiros com maior precisão após a virgula.
  
- **3 - Porque a utilização de diferentes níveis de logs?**
    R: Buscando simular ambientes prdutivos, logs mais detalhados podem ser vistos ao iniciar o projeto em modo "DEBUG", onde no modo "INFO", somente logs básicos (porém necessários) podem ser visualizados.