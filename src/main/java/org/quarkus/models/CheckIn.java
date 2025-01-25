package org.quarkus.models;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "check_ins")
public class CheckIn extends PanacheEntityBase {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column
  @NotNull
  private LocalDateTime creationDate;

  @Column
  private LocalDateTime validationDate;

  @ManyToOne
  @NotNull
  @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_check_ins_user_id"))
  private User user;

  @ManyToOne
  @NotNull
  @JoinColumn(name = "gym_id", foreignKey = @ForeignKey(name = "fk_check_ins_gym_id"))
  private Gym gym;

  public CheckIn() {}

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
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
}
