## Backend:

Criar uma api para realizar o gerenciamento de um produto e validação de usuário com JWT.

Tabela Usuario:
Id
Nome
Senha
CPF
Role (permissão)

Tabela Produto:
Cod Produto
Nome produto
Valor Produto
Estoque
Cidade (Chave estrangeira)

A partir desse cadastro um recurso REST para gerenciamento desse modelo com os seguintes métodos devem estar disponíveis:

Usuario:
GET /user - Lista todos os produtos
GET /user/{id} - Busca um produto por id
POST /users - Cria um novo produto
PUT /users/{id} - Edita um produto
DELETE /users/{id} - Deleta um produto
GET /users - Lista todos os usuarios

Produto:
GET /products - Lista todos os produtos
GET /products/{id} - Busca um produto por id
POST /products - Cria um novo produto
PUT /products/{id} - Edita um produto
DELETE /products/{id} - Deleta um produto
GET /cidades - Lista todas as cidades

## Front-end:

Agora que nossa api já está feita, precisamos fazer um front-end para conversar com essa api. OK
O projeto do frontend, deverá ser feito com o framework angular+ OK

O projeto Backend devera ser feito em springboot(java) com organização em camadas (DTO, Service, Repository), tratamento de erros e banco de dados mysql. OK

Implementar teste unitário. OK

Utilizar as técnicas de clean code e orientação a objetos S.O.L.I.D OK
