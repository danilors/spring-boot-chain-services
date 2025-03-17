@echo off

echo Parando aplicações Spring Boot (tentativa de parada graciosa)...

set PROJECT_DIRS=profile address occupation rules

for %%D in (%PROJECT_DIRS%) do (
    echo Parando projeto em: %%D
    cd %%D || (echo Erro: Não foi possível acessar o diretório %%D & continue)
    mvn spring-boot:stop
    cd .. || (echo Erro: Não foi possível voltar ao diretório pai & continue)
)

echo Matando todos os processos Java...

taskkill /f /im java.exe

if !errorlevel! == 0 (
  echo Todos os processos Java terminados.
) else (
  echo Alguns processos Java podem não ter sido terminados (ou nenhum estava em execução).
)

echo Concluído.