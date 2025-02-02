package org.quarkus.models;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.quarkus.utils.user.Role;

import java.util.List;
import java.util.UUID;

/**
 * Representa um usuário do sistema.
 * <p>
 * Esta classe mapeia a entidade de usuário no banco de dados, incluindo informações
 * sobre o nome, email, senha e papel do usuário.
 * </p>
 */

@Entity
@Table(name = "users")
@Cacheable
@SuppressWarnings("unused")
public class User extends PanacheEntityBase {

  /**
   * Identificador único do usuário.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  /**
   * Nome do usuário.
   */
  @Column
  @NotNull
  private String name;

  /**
   * Email do usuário.
   */
  @Column
  @NotEmpty
  private String email;

  /**
   * Senha do usuário.
   */
  @Column
  @NotNull
  private String password;

  /**
   * Papel do usuário no sistema.
   */
  @Enumerated(EnumType.STRING)
  @Column
  @NotNull
  private Role role;

  /**
   * Lista de check-ins associados ao usuário.
   */
  @OneToMany(mappedBy = "user")
  private List<CheckIn> checkIns;

  public User() {
  }

  public User(String name, String email, String password, Role role) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  public User(UUID userId) {
    this.id = userId;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
}