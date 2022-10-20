package com.aim.booking.persistence.entity;

import com.aim.booking.persistence.enums.UserProfileStatus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
  private String emailName;
  @Column(name = "password")
  private String passwordName;
  @Column(name = "user_profile_status")
  @ToString.Exclude
  private UserProfileStatus userProfileStatus;
}
