package org.quarkus.models;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.quarkus.utils.user.Role;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Cacheable
@SuppressWarnings("unused")
public class User extends PanacheEntityBase {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column
  @NotNull
  private String name;

  @Column
  @NotEmpty
  private String email;

  @Column
  @NotNull
  private String password;

  @Enumerated(EnumType.STRING)
  @Column
  @NotNull
  private Role role;

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