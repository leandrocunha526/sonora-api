# Sonora API

## Informações

Database: MariaDB ou MySQL  
Basta alterar em application.properties de MariaDB para MySQL.

## SOLID

Aplicando-se SOLID:

- SRP: Cada classe e pacote (por exemplo, UserService, ProductController) deve ter uma única responsabilidade.
- OCP: Use interfaces e classes abstratas para permitir a extensão sem alterar a implementação existente.
- LSP: Garanta que subclasses e implementações possam ser trocadas sem impactar o sistema.
- ISP: Divida interfaces grandes em interfaces menores e mais específicas.
- DIP: Use injeção de dependência para garantir que os componentes não dependam diretamente de implementações específicas, facilitando a troca e o teste de dependências.

## Execução

Você pode executar diretamente com Maven (usando CLI) ou usar IDEs como Intellij Community ou Ultimate (no qual o projeto foi gerado e desenvolvido).

```bash
mvn spring-boot:run
```

Este comando irá obter as depedências do projeto, irá compilar e executar.

## Permissões

ADMIN: Todas as permissões  
USER: Registros de produtos, cidades, perfil e cadastro do próprio usuário.

## Execução de testes

```bash
mvn test
```

NOTE: O front-end possui 5 testes e a API testa a criação de usuário.

TESTES MANUAIS (tanto API e front-end): OK
