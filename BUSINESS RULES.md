# API de Academia

## Requisitos Funcionais

- [x] Cadastros
- [ ] Autenticação
- [ ] Realização de um check-in pelo usuário
- [ ] Consultar o perfil de um usuário logado
- [ ] Consultar o histórico de check-ins
- [ ] Criação de academias
- [ ] Consultar o n° de check-ins feitos pelo usuário logado
- [ ] Buscas de academias pelo nome
- [ ] Buscas de academias dentro do raio de 10km
- [ ] Validação do check-in do usuário pelos administradores da academia

## Regras de Negócios

- [x] Impedir cadastro de endereços de email duplicados
- [ ] Impedir 2 check-in no mesmo dia
- [ ] Permitir check-in numa distância máxima de até 250 metros da academia
- [ ] Estipular tempo máximo de 10 minutos para validação do check-in
- [ ] Check-in apenas será validado por administradores
- [ ] Academias serão cadastradas apenas por administradores

## Requisitos Não-Funcionais

- [x] Senhas devem ser criptografadas por segurança
- [x] Persistência de dados com PostgreSQL
- [ ] Lista de dados com 20 itens por página

- [ ] Identificação de usuários com JSON Web Token (JWT)