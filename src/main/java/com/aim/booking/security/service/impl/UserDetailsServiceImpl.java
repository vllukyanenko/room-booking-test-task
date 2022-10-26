package com.aim.booking.security.service.impl;


import com.aim.booking.domain.UserDto;
import com.aim.booking.security.SystemRolesEnum;
import com.aim.booking.security.UserDetailsImpl;
import com.aim.booking.service.UserService;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {

  @Value("${app.security.admin.user.names}")
  List<String> superAdminUserNames;

  private final UserService userService;

  public UserDetailsServiceImpl(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {
    UserDto user = userService.findByEmail(email);
    String role =
        superAdminUserNames.contains(user.getEmail())
            ? SystemRolesEnum.ROLE_SUPER_ADMINISTRATOR.name()
            : SystemRolesEnum.ROLE_USER.name();

    return UserDetailsImpl.build(user, role);
  }
}
