@echo off

set PROJECT_DIRS=profile address occupation rules

for %%D in (%PROJECT_DIRS%) do (
  echo Iniciando projeto em: %%D
  cd %%D || (echo Erro: Não foi possível acessar o diretório %%D & exit /b 1)
  
  start /b mvn spring-boot:run ^
  
  cd .. || (echo Erro: Não foi possível voltar ao diretório pai & exit /b 1)
  echo Projeto %%D iniciado em segundo plano.
)

echo Todos os projetos iniciados.