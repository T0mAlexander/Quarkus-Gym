## Fluxo de orientação a objetos

- Arquitetura em uso: [Layered Architecture](https://www.baeldung.com/cs/layered-architecture)

### 1. Crie uma classe do objeto
- Defina a classe que representará o objeto.
- Adicione os atributos necessários.

```java
@Entity
@Table(name = "users")
public class User extends PanacheEntity {
  @Column
  @NotNull
  private String name;
  
  @Column
  @NotNull
  private String email;
  
  @Column
  @NotNull
  private String password;
  
  // Construtor padrão do Hibernate
  public User () {}

  public User(String name, String email, String password) {
    this.name = name;
    this.email = email;
    this.password = password;
  }
  
  // Getters & Setters
}
```

### 2. Gerencie a transação com o banco de dados
- Na pasta `database`, implemente classes para gerenciar transações específicas.

```java
public class UserRegisterTransaction {
  // Lógicas de transações
  
  @Transactional
  public User create(User user) {
    persist(user);

    return user;
  }
}
```

### 3. Crie uma interface de repositório
- Defina os métodos para acessar e manipular os dados no banco de dados.

```java
public interface UsersRepository {
    User findById(Long id);
    User findByName(String name);
    User findByEmail(String email);
    void create(User user);
}
```

### 3. Implemente a lógica de negócios
- Crie uma classe de serviço na pasta `services`
- Utilize o repositório para acessar os dados

```java
@ApplicationScoped
public class UserService {
    private final UserRegisterTransaction database;

    @Inject
    public UserService(UsersRegisterTransaction database) {
      this.database = database;
    }

    public User registerUser(User user) {
        // Pré validações de registro
        User user = new User();
        // Lógica de validações
        
        return database.create(user);
    }
}
```

### 4. Crie uma fábrica para instanciar o serviço
- Na pasta `factories`, crie uma fábrica para instanciar e configurar o serviço e o repositório

```java
@ApplicationScoped
public class UserRegisterFactory {
  public UserService userRegisterService(UsersRegisterTransaction transaction) {
    return new UserService(transaction);
  }
}
```

### 5. Valide os dados de entrada
- Na pasta `validations`, crie uma classe para validar os dados na interação da API. 

> Neste arquivo, é onde se manipula requisição e resposta da API como no exemplo abaixo em que a senha cadastrada é oculta na resposta JSON

```java
public class UserRegisterValidation {
  @NotBlank
  private String name;

  @Email
  @NotBlank
  private String email;

  @NotBlank
  @Size(min = 6)
  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;

  // Getters & Setters
}
```

### 6. Crie uma excessão customizada para tratativa de erros em cenários de falha

> Observação: esta etapa é recomendada apesar de opcional

- Retornando a pasta `services`, crie uma subpasta chamada `errors` para referir-se a erros que não condiz com a lógica de negócios

```java
public class UserExistsException extends RuntimeException {
  public UserExistsException(String message) {
    super(message);
  }
}
```

### 7. Defina as rotas da aplicação
- Na pasta `routes` (ou `controllers`), crie uma classe para definir as rotas e lidar com as requisições e respostas

```java
@Path("/users")
@RegisterRestClient
public class UserRegisterRoute {
  private static final Logger log = LoggerFactory.getLogger(UserRegisterRoute.class);
  @Inject
  UserService service;

  @POST
  @Path("/signup")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response register(@Valid UserRegisterValidation user) {
    try {
      service.create(user);

      log.info("Usuário \"{}\" registrou-se na aplicação!", user.getName());

      return Response.status(Response.Status.CREATED).entity(user).build();
    } catch (UserExistsException error) {
      return Response.status(Response.Status.CONFLICT).entity(
        error.getMessage()
      ).build();
    }
  }
}
```
