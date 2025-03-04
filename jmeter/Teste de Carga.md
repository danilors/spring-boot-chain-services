
# Comparativo de Desempenho entre APIs Reativa e Bloqueante (Java 21 GraalVM)

Os testes de carga foram realizados utilizando o Apache JMeter, simulando 100 usuários simultâneos com uma rampa de 1 minuto. Cada uma das duas APIs, 'workflow-proccessor' e 'workflow', recebeu 500.000 requisições. Ambas as APIs foram executadas em um ambiente Java 21 GraalVM. A API 'workflow-proccessor' foi desenvolvida utilizando uma abordagem reativa com WebFlux, enquanto a API 'workflow' utilizou uma abordagem bloqueante tradicional.

#### Workflow (Reativa)
![Descrição da imagem](./images/workflowprocessor.png)


#### Workflow (normal)
![Descrição da imagem](./images/workflow.png)

**Análise Detalhada:**

| Métrica                   | workflow-proccessor | workflow  | TOTAL      |
| ------------------------- | ------------------- | --------- | ---------- |
| Número de Amostras        | 500000              | 500000    | 1000000    |
| Média (ms)                | 95                  | 122       | 109        |
| Mediana (ms)              | 76                  | 105       | 88         |
| Percentil 90% (ms)        | 187                 | 238       | 216        |
| Percentil 95% (ms)        | 236                 | 287       | 266        |
| Percentil 99% (ms)        | 348                 | 395       | 377        |
| Mínimo (ms)               | 3                   | 1         | 1          |
| Máximo (ms)               | 949                 | 1238      | 1238       |
| Taxa de Erro (%)          | 0.000%              | 0.009%    | 0.004%     |
| Vazão (req/seg)           | 867.74672           | 801.01376 | 1600.35592 |
| KB Recebidos por Segundo  | 132.03              | 298.90    | 420.33     |
| KB Enviados por Segundo   | 120.42              | 107.25    | 218.17     |

## 1. workflow-proccessor:

* **Samples:** 500,000 - Um número significativo de amostras, fornecendo dados estatísticos confiáveis.
* **Average:** 95 ms - Tempo de resposta médio bom.
* **Median:** 76 ms - A maioria das requisições respondeu em 76 ms ou menos, indicando consistência.
* **Percentis (90%, 95%, 99%):** Os percentis mostram um aumento gradual, mas ainda em valores aceitáveis.
* **Min:** 3 ms - Tempo de resposta mínimo muito rápido.
* **Max:** 949 ms - Tempo de resposta máximo consideravelmente alto, indicando variações significativas.
* **Error %:** 0.000% - Nenhuma requisição resultou em erro, o que é excelente.
* **Throughput:** 867.74672/sec - Um throughput alto.
* **Received KB/sec:** 132.03 - Taxa de recebimento de dados razoável.
* **Sent KB/sec:** 120.42 - Taxa de envio de dados razoável.

## 2. workflow:

* **Samples:** 500,000 - Também um número significativo de amostras.
* **Average:** 122 ms - Tempo de resposta médio um pouco maior que "workflow-proccessor".
* **Median:** 105 ms - A maioria das requisições respondeu em 105 ms ou menos.
* **Percentis (90%, 95%, 99%):** Os percentis mostram um aumento maior em relação ao "workflow-proccessor", indicando maior variabilidade nos tempos de resposta.
* **Min:** 1 ms - Tempo de resposta mínimo muito rápido.
* **Max:** 1238 ms - Tempo de resposta máximo consideravelmente alto, indicando variações significativas.
* **Error %:** 0.009% - Uma taxa de erro muito baixa, mas existente.
* **Throughput:** 801.01376/sec - Um throughput alto, mas ligeiramente menor que "workflow-proccessor".
* **Received KB/sec:** 298.90 - Taxa de recebimento de dados maior que "workflow-proccessor".
* **Sent KB/sec:** 107.25 - Taxa de envio de dados razoável.

## 3. TOTAL:

* **Samples:** 1,000,000 - Número total de requisições.
* **Average:** 109 ms - Tempo de resposta médio geral bom.
* **Median:** 88 ms - Tempo de resposta mediano geral bom.
* **Percentis (90%, 95%, 99%):** Percentis gerais aceitáveis.
* **Min:** 1 ms - Tempo de resposta mínimo geral.
* **Max:** 1238 ms - Tempo de resposta máximo geral.
* **Error %:** 0.004% - Uma taxa de erro muito baixa, mas existente.
* **Throughput:** 1600.35592/sec - Throughput geral bom.
* **Received KB/sec:** 420.33 - Taxa de recebimento de dados geral alta.
* **Sent KB/sec:** 218.17 - Taxa de envio de dados geral razoável.

## Avaliação Geral:

Ambas as APIs apresentaram um bom desempenho geral, com tempos de resposta médios e medianos aceitáveis.
A taxa de erros é muito baixa, mas a API "workflow" apresentou erros, o que precisa ser investigado.
"workflow-proccessor" apresentou um desempenho ligeiramente superior em termos de tempo de resposta e throughput.
"workflow" apresentou um recebimento de dados muito maior.
Os tempos máximos de resposta (Max) são consideravelmente altos em ambas as APIs, indicando variações significativas. Isso sugere que, embora a maioria das requisições seja rápida, algumas demoram muito mais.
Os resultados indicam que o sistema testado está respondendo bem sob carga, e houve uma melhora comparado ao teste anterior.

