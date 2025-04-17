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
