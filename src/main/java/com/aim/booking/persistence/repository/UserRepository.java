package com.aim.booking.persistence.repository;

import com.aim.booking.persistence.entity.User;
import com.aim.booking.persistence.enums.UserStatus;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
  boolean existsByEmailAndUserStatus(String email, UserStatus status);
}
