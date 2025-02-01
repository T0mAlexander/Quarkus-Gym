package org.quarkus.models;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;
import org.quarkus.utils.PointConverter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "gyms")
@Cacheable
@NamedQuery(
  name = "Gym.findCloseGyms",
  query = "SELECT target FROM Gym target WHERE target.location IS NOT NULL",
  hints = @QueryHint(name = "org.hibernate.cacheable", value = "true")
)
@SuppressWarnings("unused")
public class Gym extends PanacheEntityBase {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column
  @NotNull
  private String name;

  @Column
  @NotNull
  private String email;

  @Column
  private String description;

  @Column
  @NotNull
  private String phone;

  @Column(columnDefinition = "geometry(POINT, 4326)")
  @NotNull
  @Convert(converter = PointConverter.class)
  private Point location;

  @OneToMany(mappedBy = "gym")
  private List<CheckIn> checkIns;

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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Point getLocation() {
    return location;
  }

  public void setLocation(Point location) {
    this.location = location;
  }
}
