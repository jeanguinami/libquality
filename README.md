# LibQuality

Ferramenta usada para tirar métricas de qualidade de repositórios no GitHub.

## Instalação

Para que a aplicação rode corretamente, é necessário ter um **MySQL** na **porta 3306** com usuário **root** e senha **pass**.

O código foi escrito usando **IntelliJ Community** na **JDK 12.0.2**.

## Execução
O primeiro passo para testar as execuções é colocar as credenciais válidas de um usuário do GitHub no `application.properties`, possibilitando que a comunicação com a API do GitHub seja autenticada.

Para executar testes nessa aplicação, é necessário ter agente para conversar com a API (por exemplo [Postman](https://www.postman.com)).

As chamadas dos endpoints são as seguintes:

### [POST] http://localhost:8080/repositories

>Este endpoint cria um repositório no banco de dados da aplicação. O JSON para alimentá-lo deve ser:
```
{
    "repoName":"VAR1",
    "repoUrl":"VAR2"
}
```
>VAR1 equivale ao nome que o usuário deseja que o repositório tenha na aplicação.

>VAR2 equivale a parte da URL que identifica o repositório, como por exemplo:
>Na URL https://github.com/facebook/react, é usado o **facebook/react**.

### [GET] http://localhost:8080/repositories/name/VAR1
>Este endpoint procura e retorna uma entidade de repositório dentro do banco de dados da aplicação, com a sua devida informação de qualidade.

>VAR1 equivale ao nome cadastrado anteriormente para o repositório desejado.

### [GET] http://localhost:8080/repositories
>Este endpoint retorna todos os repositórios presentes na base de dados da aplicação, com a informação de qualidade de cada um deles.

