<h1 align="center">API Rest com Spring, Postgres e Rabbitmq.</h1>

*Código organizado por negócio, dentro de cada package está contido tudo sobre uma determinada entidade.

*Dentro de cada package de negócio tem package e classes com as seguintes nomenclaturas:*
- Repository (JpaRepository da Classe)
- Command (contém os métodos que realiza alterações nos registro)
- Query (contém métodos de buscas apenas)
- Specification (contém filtros por criteria)
- package web (contém classes refentes aos endpoints)
- RestService dentro do package web (classe de controller, com os endpoints)
- package integration (contém classes referentes a messageria)

*Basicamente o funcionamento da API é o seguinte:*

**Contém 3 classes e tabelas diferentes, classe de Agenda(Pauta), Vote(Votos) e Result(Resultado da votação da pauta)**
1. Agenda(Pauta):
    - Criar uma nova pauta que nasce com o status de Wating
    - Alterar uma pauta (só é possível alterar pauta com o status Waiting)
    - Iniciar a votação de uma pauta passando quantidade de minutos que ficara aberto para votação essa pauta, e caso não passe valor nenhum será considero pro default 1 minuto (só é possível iniciar uma pauta com status Waiting e será passado o status para Voting)
    - Buscar pautas fazendo um like por descrição
    - Buscar pauta por id
    - Deletar uma pauta (só será possível deletar pauta com status Waiting)
2. Vote(Votos):
    - Realizar um voto (só sendo possível vota em uma pauta com status Voting e com data de final de votação menor que a data atual, e é realizado uma verificação se já não tem voto do cpf informado para essa mesma pauta, e também é consultado o cpf na API externa informada na descrição do teste)
3. Result(Resultado da votação da pauta):
    - Buscar pautas com determinados resultados finais
    - Buscar todos os resultados de votações das pautas
    - Buscar um resultado por id da pauta

**Foi feito um schedule que roda de 5 em 5 minutos que busca todas as pautas com status Voting e que já passou a data de fim de votação, e para cada pauta encontrada nessa condição, é passado o status da pauta para Finished e é disparado uma mensagem para o rabbimq com o resultado final da votação da pauta.**

**Fiz o consumo dessa mensagem no mesmo projeto, e ao consumir a mensagem, é gerado um registro de Result.**

**Criei testes unitários de todas as classes de negócio.**

**Criei um teste de contrato apenas para um endpoint (apenas para ter um exemplo feito)**

**Coloquei hateoas em apenas um endpoint (apenas para ter um exemplo feito)**

---

<h3>Executar com docker:</h3>

**Basta executar dentro da pasta raiz do projeto o comando:**

`docker-compose up`

E depois compilar o projeto.

<h4>Ou</h4>

**Basta executar os seguintes comandos docker:**

`docker run -p 5432:5432 --name postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=cooperativa -d postgres:11.1-alpine`

`docker run -d --hostname localhost --name rabbitmq -p 5672:5672 -p 5671:5671 -p 15672:15672 rabbitmq:3.6.6-management`

E depois compilar o projeto.

---

<h4>Executar sem docker:<h4>

Iniciar banco postgresql.

Criar schema novo.

Iniciar Rabbitmq.

Configurar no application.yaml a url, o username e o password do datasource, e alterar o host, port, username e password do rabbitmq.
