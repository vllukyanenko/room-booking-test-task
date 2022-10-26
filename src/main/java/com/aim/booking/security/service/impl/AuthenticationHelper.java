package com.aim.booking.security.service.impl;

import com.aim.booking.security.SystemRolesEnum;
import com.aim.booking.security.UserDetailsImpl;
import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationHelper {

  public AuthenticationHelper() {
  }

  public static String getCurrentUserId() {
    return getUserDetails().getId();
  }

  public static String getCurrentUserEmail() {
    return getUserDetails().getEmail();
  }

  public static String getCurrentUserRole() {
    Collection<? extends GrantedAuthority> authorities = getUserDetails().getAuthorities();
    SimpleGrantedAuthority auth = (SimpleGrantedAuthority) authorities.toArray()[0];
    return auth.getAuthority();
  }

  public static boolean isUserSystemAdmin() {
    return SystemRolesEnum.ROLE_SUPER_ADMINISTRATOR.name()
        .equals(getCurrentUserRole());
  }

  public static boolean isUserAuthenticated() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return null != authentication && !("anonymousUser").equals(authentication.getName());
  }


  public static UserDetailsImpl getUserDetails() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return (UserDetailsImpl) authentication.getDetails();
  }

  public static void clearAuthentication() {
    SecurityContextHolder.clearContext();
  }
}
