# Spring Boot WebFlux vs. Blocking: Comparativo de Desempenho

Este repositório contém um projeto desenvolvido para avaliar as diferenças de desempenho entre uma aplicação Spring Boot usando uma abordagem reativa com WebFlux (`workflow-processor`) e uma abordagem tradicional bloqueante (`workflow`). O projeto visa demonstrar os benefícios e as compensações de cada abordagem sob carga.

## Estrutura do Projeto

O projeto é composto pelos seguintes módulos:

*   **`workflow-processor`:** Este módulo representa um serviço construído com Spring Boot WebFlux, aproveitando a programação reativa para lidar com requisições de forma assíncrona.
*   **`workflow`:** Este módulo representa um serviço construído com Spring Boot tradicional, usando um modelo de I/O bloqueante.
*   **`profile`:** Um microsserviço de suporte que simula a busca de informações de perfil. É usado tanto pelo `workflow-processor` quanto pelo `workflow`.
*   **`address`:** Um microsserviço de suporte que simula a busca de informações de endereço. É usado tanto pelo `workflow-processor` quanto pelo `workflow`.
*   **`occupation`:** Um microsserviço de suporte que simula a busca de informações de ocupação. É usado tanto pelo `workflow-processor` quanto pelo `workflow`.
*   **`rules`:** Um microsserviço de suporte que simula a aplicação de regras de negócios. É usado tanto pelo `workflow-processor` quanto pelo `workflow`.

**Interação:**

Os microsserviços `profile`, `address`, `occupation` e `rules` atuam como dependências tanto para o `workflow-processor` quanto para o `workflow`. Essa configuração tem como objetivo simular um cenário do mundo real onde vários microsserviços interagem para atender a uma única requisição. Ao aproveitar esses serviços, podemos ver como cada implementação de `workflow` interage com sistemas externos.

**Concorrência:**
Tanto no `workflow-processor` quanto no `workflow`, são feitas requisições em paralelo para os microsserviços dependentes (`profile`, `address`, `occupation`, `rules`). Isso foi implementado para verificar o comportamento das aplicações em cenários que exigem o uso de concorrência e para avaliar como cada abordagem lida com requisições simultâneas.

**Banco de Dados em Memória:**
Os projetos `profile`, `address`, `rules` e `occupation` integram-se com um banco de dados em memória H2 através do Spring Data. O uso do H2 tem como única intenção fornecer uma massa de dados de forma rápida e fácil configuração, evitando a necessidade de um banco de dados externo para executar o projeto.

**Integração com Redis:**
No projeto `workflow`, foi implementada uma integração simples com Redis. O objetivo dessa integração é apenas verificar o funcionamento do Redis, demonstrando sua capacidade de ser usado com um servico que nao e reativo.

## Objetivo do Projeto

O principal objetivo deste projeto é comparar o desempenho do `workflow-processor` (reativo) e do `workflow` (bloqueante) sob carga. Nosso objetivo é entender:

*   **Latência:** Quão rapidamente cada aplicação pode responder às requisições.
*   **Throughput:** Quantas requisições cada aplicação pode lidar por segundo.
*   **Utilização de Recursos:** Quão eficientemente cada aplicação usa os recursos do sistema.
*   **Taxas de Erro:** Quão confiável cada aplicação é sob carga.

## Tecnologias Utilizadas

*   **Java 21:** A linguagem de programação principal.
*   **Spring Boot:** O framework utilizado para construir as aplicações.
*   **Spring WebFlux:** O framework web reativo usado no `workflow-processor`.
*   **Spring Data:** Framework para facilitar a integracao com o banco de dados.
*   **H2 Database:** Banco de dados em memoria usado pelos microsservicos.
*   **Redis**: Banco de dados usado no projeto workflow.
*   **GraalVM:** Usado como a JVM, potencialmente oferecendo melhorias de desempenho.
*   **Apache JMeter:** Ferramenta de teste de carga.

## Testes de Carga

Para avaliar o desempenho de cada serviço, criamos um script de teste de carga usando o Apache JMeter. O script simula uma carga de trabalho específica e mede várias métricas de desempenho.

### Script JMeter

O script do JMeter pode ser encontrado no diretório `jmeter`.

### Cenário de Teste

O cenário de teste normalmente envolve:

*   **Número de Usuários:** Um número configurável de usuários simultâneos.
*   **Período de Ramp-up:** O tempo necessário para atingir o número máximo de usuários.
*   **Requisições:** Uma sequência de requisições HTTP para cada API.

### Resultados Detalhados

Os resultados detalhados de um dos testes de carga estão disponíveis no arquivo `jmeter/Teste de Carga.md`. Este arquivo contém uma análise abrangente das métricas de desempenho para cada API, incluindo:

*   Tempo médio de resposta
*   Tempo mediano de resposta
*   Percentis (90º, 95º, 99º)
*   Taxas de erro
*   Throughput (Vazão)
*   Taxas de transferência de dados

## Executando o Projeto

Para executar o projeto, você precisa executar os comandos abaixo em cada pasta dos microsserviços:

```bash
mvn clean spring-boot:run
```