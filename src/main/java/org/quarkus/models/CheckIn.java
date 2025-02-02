package org.quarkus.models;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.*;
import org.quarkus.utils.checkin.Status;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa um check-in realizado por um usuário em uma academia.
 * <p>
 * Esta classe mapeia a entidade de check-in no banco de dados, incluindo informações
 * sobre o usuário, a academia, datas de criação e validação, e o status do check-in.
 * </p>
 */

@Entity
@Table(name = "check_ins")
@Cacheable
@NamedQuery(
  name = "CheckIn.userHistory",
  query = "SELECT target FROM CheckIn target WHERE target.userId = :userId ORDER BY target.creationDate DESC",
  hints = @QueryHint(name = "org.hibernate.cacheable", value = "true")
)
@SuppressWarnings("unused")
public class CheckIn extends PanacheEntityBase {

  /**
   * Identificador único do check-in.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  /**
   * Identificador do usuário que realizou o check-in.
   */
  @Column(name = "user_id")
  private UUID userId;

  /**
   * Identificador da academia onde o check-in foi realizado.
   */
  @Column(name = "gym_id")
  private UUID gymId;

  /**
   * Data e hora de criação do check-in.
   */
  @Column(name = "creation_date")
  @NotNull
  private LocalDateTime creationDate;

  /**
   * Data e hora de validação do check-in.
   */
  @Column(name = "validation_date")
  private LocalDateTime validationDate;

  /**
   * Status atual do check-in.
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private Status status;

  /**
   * Usuário associado ao check-in.
   */
  @ManyToOne
  @NotNull
  @JoinColumn(
    name = "user_id",
    foreignKey = @ForeignKey(name = "fk_check_ins_user_id"),
    insertable = false,
    updatable = false
  )
  private User user;

  /**
   * Academia associada ao check-in.
   */
  @ManyToOne
  @NotNull
  @JoinColumn(
    name = "gym_id",
    foreignKey = @ForeignKey(name = "fk_check_ins_gym_id"),
    insertable = false,
    updatable = false
  )
  private Gym gym;

  public CheckIn() {}

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  public UUID getGymId() {
    return gymId;
  }

  public void setGymId(UUID gymId) {
    this.gymId = gymId;
  }

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }

  public LocalDateTime getValidationDate() {
    return validationDate;
  }

  public void setValidationDate(LocalDateTime validationDate) {
    this.validationDate = validationDate;
  }

  public Gym getGym() {
    return gym;
  }

  public void setGym(Gym gym) {
    this.gym = gym;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }
}