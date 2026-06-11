Aluno: Arthur Vinicio da Silva Barbosa<br>
Mat.: 2025111890023<br>
Centro Universitário Uniesp
Disciplina: Banco de Dados Avançado

# ![globo](assets/mundo.png) Projeto Imigrante 

API REST em Spring Boot para cadastro de imigrantes, famílias, proprietários, alojamentos, endereços, alocação de famílias e relatórios com views do PostgreSQL.

## Requisitos

- Java 17
- PostgreSQL
- DBeaver
- Maven
- IntelliJ IDEA ou outra IDE Java
- Insomnia para testar a API

## Configuração do banco

1. Abra o DBeaver e conecte-se ao PostgreSQL. Após conectar-se, crie o banco de dados:

```sql
CREATE DATABASE projeto_imigrante;
```

2. Conecte-se ao banco de dados "projeto_imigrante" e execute os scripts contidos na pasta *scripts*, na seguinte ordem: 

  - *schema.sql*
  - *views.sql*
  - *triggers.sql*
  - *procedures.sql*
  - *inserts.sql*

3. Após a execução dos scripts, inicie a execução do projeto Spring no IntelliJ IDEA (ou em outra IDE de sua preferência) e teste no Front End. Para testar esta API foi usado o *Insomnia*. As collections usadas estão salvas na pasta *insomnia/*


## Insomnia

As requisições do Insomnia usadas para testar a API estão no arquivo:

```txt
Insomnia_requests.yaml
 
