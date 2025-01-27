# API de Academia

## Requisitos funcionais

- [x] Cadastros
- [x] Autenticação
- [x] Realização de um check-in pelo usuário
- [ ] Consultar o perfil de um usuário logado
- [ ] Consultar o histórico de check-ins
- [x] Criação de academias
- [ ] Consultar o n° de check-ins feitos pelo usuário logado
- [x] Buscas de academias pelo nome
- [x] Buscas de academias dentro do raio de 5km
- [ ] Validação do check-in do usuário pelos administradores da academia

## Requisitos opcionais

- [x] Impedir cadastro de endereços de email duplicados
- [x] Impedir 2 check-in no mesmo dia
- [x] Permitir check-in numa distância máxima de até 250 metros da academia
- [ ] Estipular tempo máximo de 10 minutos para validação do check-in
- [ ] Check-in apenas será validado por administradores
- [ ] Academias serão cadastradas apenas por administradores

## Requisitos Não-Funcionais

- [x] Persistência de dados com PostgreSQL
- [x] Senhas devem ser criptografadas por segurança
- [x] Lista de dados com 10 itens por página
- [x] Identificação de usuários com JSON Web Token (JWT)