package com.aim.booking.persistence.repository;

import com.aim.booking.persistence.entity.User;
import com.aim.booking.persistence.enums.UserProfileStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
  boolean existsByEmailAndUserProfileStatus(String email, UserProfileStatus status);
}
