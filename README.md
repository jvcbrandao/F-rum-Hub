
# Forum Hub API

## Descrição
Esta é uma API REST desenvolvida com Spring Boot, que utiliza Spring Security para autenticação e PostgreSQL como banco de dados. 
A API oferece um CRUD básico para gerenciamento de tópicos e autenticação baseada em tokens JWT.

## Tecnologias Utilizadas
- **Spring Boot**
- **Spring Security**
- **PostgreSQL**
- **JWT (JSON Web Token)**
- **Insomnia** (para testes de API)

## Endpoints Principais

### Autenticação
**POST** `/login`  
Para iniciar o uso da API, faça uma requisição para o endpoint `/login` com um usuário cadastrado no banco de dados.  
Se as credenciais forem válidas, um token JWT será retornado.  

Exemplo de Payload:
```json
{
  "login": "usuario",
  "senha": "senha123"
}
```
Resposta:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

Este token deve ser enviado no cabeçalho das próximas requisições como:
```
Authorization: Bearer <seu_token>
```

### CRUD de Tópicos
**Base URL:** `/topicos`

#### Listar Tópicos
**GET** `/topicos`  
Lista todos os tópicos cadastrados. É possível paginar os resultados.

#### Cadastrar um Tópico
**POST** `/topicos`  
Cadastrar um novo tópico.

Exemplo de Payload:
```json
{
  "tituloDoComentario": "Título do Tópico",
  "mensagem": "Mensagem do tópico",
  "autor": "Autor",
  "curso": "Curso relacionado"
}
```

#### Atualizar um Tópico
**PUT** `/topicos/{id}`  
Atualiza os dados de um tópico pelo ID.

Exemplo de Payload:
```json
{
  "tituloDoComentario": "Título Atualizado",
  "mensagem": "Mensagem Atualizada"
}
```

#### Excluir um Tópico
**DELETE** `/topicos/{id}`  
Exclui um tópico pelo ID.

## Segurança
- Todas as requisições, exceto o endpoint `/login`, exigem autenticação via token JWT.
- Senhas são armazenadas de forma segura usando `BCrypt`.

## Como Executar
1. Clone este repositório.
2. Configure o arquivo `application.properties` com as credenciais do PostgreSQL.
3. Inicie a aplicação com `mvn spring-boot:run`.
4. Use o Insomnia ou outra ferramenta para testar os endpoints.

## Contribuições
Contribuições são bem-vindas! Abra uma issue ou envie um pull request para melhorias.
