package com.aim.booking.persistence.entity;

import com.aim.booking.persistence.enums.UserStatus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@NoArgsConstructor
@Table(name = "user")
public class User extends BaseEntity {

  @Column(name = "first_name")
  private String firstName;
  @Column(name = "last_name")
  private String lastName;
  @Column(name = "email")
  private String email;
  @Column(name = "password")
  private String password;
  @Column(name = "user_status")
  @Enumerated(EnumType.STRING)
  private UserStatus userStatus;
}
