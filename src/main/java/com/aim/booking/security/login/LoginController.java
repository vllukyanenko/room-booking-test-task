package com.aim.booking.security.login;

import com.aim.booking.security.UserDetailsImpl;
import com.aim.booking.security.service.impl.JwtService;
import com.aim.booking.web.swagger.AuthorizationControllerEndpoint;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/authentication")
public class LoginController implements AuthorizationControllerEndpoint {
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public LoginController(JwtService jwtService,
      AuthenticationManager authenticationManager) {
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
  }


  @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
    Authentication authentication;
    try {
      authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));

    } catch (Exception e) {
      log.error(e.getMessage(), e.getCause());
      throw new InternalAuthenticationServiceException("UNAUTHORIZED " + e.getMessage());
    }
    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetailsImpl memberDetails = (UserDetailsImpl) authentication.getPrincipal();
    String jwt = jwtService
        .generateUserAuthenticationToken(memberDetails.getEmail(), memberDetails.getId(), null);

    List<String> roles = memberDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());
    log.info("User with email {} and id {} logged", memberDetails.getEmail(),
        memberDetails.getId());

    LoginResponse loginResponse = LoginResponse.builder()
        .token(jwt)
        .username(memberDetails.getUsername())
        .email(memberDetails.getEmail())
        .role(roles.get(0))
        .userId(memberDetails.getId())
        .build();

    return new ResponseEntity<>(loginResponse, HttpStatus.OK);
  }


  @PostMapping(value = "/logout")
  public ResponseEntity<Void> logout() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication.isAuthenticated()) {
      UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
          .getAuthentication()
          .getDetails();
      SecurityContextHolder.clearContext();
      log.info("Logout user with email {} with id {} ", userDetails.getEmail(),
          userDetails.getId());
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
