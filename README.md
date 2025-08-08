# itau.technical.challenge.dangog


Desafio Técnico Backend Itaú
Visão Geral
Este repositório contém a solução para o desafio técnico de backend do Itaú. O objetivo foi desenvolver uma API REST que lida com transações financeiras, seguindo as melhores práticas de desenvolvimento, como código limpo, uso de DTOs, validação de dados e segurança de concorrência.

A aplicação foi construída com Java 17 e Spring Boot, e utiliza o Jakarta Bean Validation para garantir a integridade dos dados, o Java Records para modelagem de DTOs e o Project Lombok para reduzir o código boilerplate.

Estrutura do Projeto
A solução foi organizada com base no paradigma MVC (Model-View-Controller), com as seguintes camadas:

src/main/java/itau/challenge/com/itau/technical/controller: Contém a classe TransactionController, responsável por expor os endpoints da API.

src/main/java/itau/challenge/com/itau/technical/service: Contém a lógica de negócio principal da aplicação.

src/main/java/itau/challenge/com/itau/technical/dto: Contém a classe TransactionDTO, um Data Transfer Object implementado como um Java Record para garantir imutabilidade e concisão.

src/main/java/itau/challenge/com/itau/technical/repository: Camada de "persistência" de dados in-memory. Foi usado um ConcurrentHashMap para simular um banco de dados in-memory seguro para threads.

src/main/java/itau/challenge/com/itau/technical/model: Classe de domínio Transaction, utilizada para representar a entidade de transação na lógica de negócio.

Tecnologias e Dependências
Linguagem: Java 17

Framework: Spring Boot 3.2.0

Gerenciador de Dependências: Maven

Validação: Jakarta Bean Validation (Hibernate Validator)

Coleções Seguras: java.util.concurrent (ConcurrentHashMap)

Produtividade: Project Lombok

Endpoints da API
A API expõe os seguintes endpoints:

POST /transactions

Descrição: Cria uma nova transação.

Corpo da Requisição:

JSON

{
  "valor": 123.45,
  "dataHora": "2020-08-07T12:34:56.789-03:00"
}
Respostas:

201 Created: Transação criada com sucesso.

400 Bad Request: Falha na validação dos dados da requisição.

DELETE /transactions

Descrição: Apaga todas as transações da base de dados in-memory.

Respostas:

204 No Content: Operação concluída com sucesso.

GET /statistics

Descrição: Retorna as estatísticas das transações do último minuto.

Respostas:

200 OK: Retorna um JSON com as estatísticas.

JSON

{
  "sum": "123.45",
  "avg": "123.45",
  "max": "123.45",
  "min": "123.45",
  "count": 1
}
Como Rodar a Aplicação
Para executar a API, siga os passos abaixo:

Pré-requisitos: Certifique-se de ter o Java 17 e o Maven instalados em sua máquina.

Clonar o Repositório:

Bash

git clone https://github.com/<seu-usuario>/<nome-do-repositorio>.git
Compilar e Rodar:
Navegue até a pasta raiz do projeto e execute o comando:

Bash

mvn spring-boot:run
A aplicação estará disponível em http://localhost:8080.

Decisões de Design e Melhores Práticas
Imutabilidade: O TransactionDTO foi implementado como um Java Record. Isso garante que os DTOs sejam imutáveis, tornando-os mais seguros em ambientes concorrentes e evitando mutações de estado inesperadas.

Segurança de Concorrência: Para o armazenamento in-memory, foi usado o ConcurrentHashMap. Essa coleção, otimizada para ambientes multi-threaded, garante a integridade dos dados sem comprometer a performance.

Validação de Dados: O Jakarta Bean Validation foi utilizado para validar os dados de entrada da API. As anotações @NotNull, @Min garantem que a requisição seja válida antes de ser processada.

Código Conciso: O Project Lombok foi usado para automatizar a geração de getters, setters e construtores na classe de modelo, reduzindo o código boilerplate.

BigDecimal para Valores Monetários: Para evitar imprecisão em cálculos de ponto flutuante, o BigDecimal foi usado para o campo valor, garantindo cálculos financeiros exatos.
