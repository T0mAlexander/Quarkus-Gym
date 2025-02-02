# API de Academia

## Requisitos funcionais

- [x] Cadastros
- [x] Autenticação
- [x] Realização de um check-in pelo usuário
- [x] Consultar o histórico de check-ins
- [x] Criação de academias
- [x] Buscas de academias pelo nome
- [x] Buscas de academias dentro do raio de 5 km
- [ ] Validação do check-in do usuário pelos administradores da academia

## Requisitos opcionais

- [x] Impedir cadastro de endereços de email duplicados
- [x] Impedir 2 check-in no mesmo dia
- [x] Permitir check-in numa distância máxima de até 250 metros da academia
- [x] Estipular tempo máximo de 10 minutos para validação do check-in
- [x] Academias cadastradas apenas por administrador
- [x] Check-in validadado por administrador da academia

## Requisitos Não-Funcionais

- [x] Persistência de dados com PostgreSQL
- [x] Geolocalização e coordenadas com PostGIS
- [x] Senhas devem ser criptografadas por segurança
- [x] Lista de dados com 10 itens por página
- [x] Identificação de usuários com JSON Web Token (JWT)
- [x] Documentação do uso da API com Swagger UI e OpenAPI
- [x] Documentar o código, métodos e submétodos com Javadoc
- [ ] Implementar testes nativos do Quarkus