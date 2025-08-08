# Desafio Técnico Backend Itaú

## Visão Geral

Este repositório contém a solução para o desafio técnico de backend do (teoricamente) do Itaú. O objetivo foi desenvolver uma API REST para gerenciar transações financeiras, com 3 endpoints focando no Cadastro, Remoção e Listagem das transações com base nas regras mencionadas.

A página para o desafio pode ser encontrada em: https://github.com/feltex/desafio-itau-backend

## Estrutura do Projeto

A aplicação foi construída com Java 17 e Spring Boot, utilizando o Jakarta Bean Validation para garantir a integridade dos dados, o Java Records para modelagem de DTOs e o Project Lombok para reduzir o código repetitivo (boilerplate), posteriormente podem ser adicionados outras dependências para a criação dos desafios na seção "extra" descritos.

## Tecnologias e Dependências

-   **Linguagem:** Java 17
-   **Framework:** Spring Boot 3.2.0
-   **Gerenciador de Dependências:** Maven
-   **Validação:** Jakarta Bean Validation (Hibernate Validator)
-   **Coleções Seguras:** `java.util.concurrent` (ConcurrentHashMap)
-   **Produtividade:** Project Lombok

A solução segue o paradigma MVC (Model-View-Controller) e está organizada nas seguintes camadas:

-   `src/main/java/itau/challenge/com/itau/technical/controller`: Contém a classe `TransactionController`, que expõe os endpoints da API.
-   `src/main/java/itau/challenge/com/itau/technical/service`: Camada de lógica de negócio da aplicação.
-   `src/main/java/itau/challenge/com/itau/technical/dto`: Contém a classe `TransactionDTO`, um Data Transfer Object implementado como um Java Record para garantir imutabilidade e concisão.
-   `src/main/java/itau/challenge/com/itau/technical/repository`: Camada de "persistência" de dados. Para este desafio, foi usado um `ConcurrentHashMap` para simular um banco de dados in-memory seguro para threads.
-   `src/main/java/itau/challenge/com/itau/technical/model`: Classe de domínio `Transaction`, utilizada para representar a entidade de transação na lógica de negócio.

## Endpoints da API

A API expõe os seguintes endpoints:

### POST `/transactions`

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

### DELETE `/transactions`

-   **Descrição:** Apaga todas as transações da base de dados in-memory.
-   **Respostas:**
    -   `204 No Content`: Operação concluída com sucesso.

### GET `/statistics`

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

## Como Rodar a Aplicação

Para executar a API, siga os passos abaixo:

1.  **Pré-requisitos:** Certifique-se de ter o Java v17 e o gerenciador de pacotes Maven instalados em sua máquina.
2.  **Clonar o Repositório:**
    ```bash
    git clone [https://github.com/](https://github.com/)<seu-usuario>/<nome-do-repositorio>.git
    ```
3.  **Compilar e Rodar:**
    Navegue até a pasta raiz do projeto e execute o comando:
    ```bash
    mvn spring-boot:run
    ```
    A aplicação estará disponível em `http://localhost:8080`.

*Posteriormente será disponibilizado nessa ou em outra branch a subida dessa projeto com containers usando docker.

## FAQ e principais decisões:

-   **1 - Porque foi utilizado ConcurrentHashMap ao invés de outra estrutura de dados?:** R: Segurança de Concorrência, para o armazenamento in-memory, foi usado o `ConcurrentHashMap`. Essa coleção, otimizada para ambientes multi-threaded, garante a integridade dos dados sem comprometer a performance, além de poder adicioanr um conjunto "ID:Objeto" para gerenciamento por ID's únicos das transações.
-   **2 - Porque a utilização do 'BigDecimal' ao invés de 'double' ou outros tipos de variáveis?** Valores Monetários, para evitar imprecisão em cálculos de ponto flutuante, o `BigDecimal` foi usado para o campo `valor`, garantindo cálculos financeiros com maior precisão após a virgula.
