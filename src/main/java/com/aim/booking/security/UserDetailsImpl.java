package com.aim.booking.security;

import com.aim.booking.domain.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class UserDetailsImpl implements UserDetails {


  private String id;
  private String fullName;
  private String email;
  @JsonIgnore
  private String password;
  private Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(String id, String firstName, String lastName, String email,
      String password,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    if (firstName != null && lastName != null) {
      this.fullName = firstName.concat(" ").concat(lastName);
    } else if (firstName != null) {
      this.fullName = firstName;
    } else if (lastName != null) {
      this.fullName = lastName;
    } else {
      this.fullName = "N/A";
    }
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }

  public static UserDetailsImpl build(UserDto user, String role) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
    authorities.add(authority);

    return new UserDetailsImpl(
        user.getId(),
        user.getFirstName(),
        user.getLastName(),
        user.getEmail(),
        user.getPassword(),
        authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return fullName;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
