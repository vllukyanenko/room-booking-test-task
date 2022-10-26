package com.aim.booking.security;

import static org.springframework.util.StringUtils.hasText;

import com.aim.booking.security.service.impl.JwtService;
import com.aim.booking.security.service.impl.UserDetailsServiceImpl;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Log4j2
public class AuthTokenFilter extends OncePerRequestFilter {

  private static final String AUTHORIZATION = "Authorization";
  private static final String BEARER_PREFIX = "Bearer ";
  private static final Set<String> paths = new HashSet<>();
  @Autowired
  private JwtService jwtService;
  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Override
  protected void initFilterBean() {
    paths.add("/api/authentication/login");
  }

  @Override
  protected void doFilterInternal(HttpServletRequest servletRequest,
      HttpServletResponse servletResponse,
      FilterChain filterChain) throws ServletException, IOException {
    log.info("do filter...");
    String token = getTokenFromRequest(servletRequest);
    if (StringUtils.isNotEmpty(token) && jwtService.validateToken(token)) {
      handleToken(token);
    }
    filterChain.doFilter(servletRequest, servletResponse);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getRequestURI();
    return paths.contains(path);
  }

  private void handleToken(String token) {
    String tokenType = jwtService.getTokenDataType(token);
    switch (tokenType) {
      case "user": {
        handleUserToken(token);
        break;
      }
    }
  }

  private void handleUserToken(String token) {
    String email = jwtService.getEmailFromJwtToken(token);
    UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(email);
    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
        userDetails, null, userDetails.getAuthorities());
    auth.setDetails(userDetails);
    SecurityContextHolder.getContext().setAuthentication(auth);
  }


  private String getTokenFromRequest(HttpServletRequest request) {
    String bearer = request.getHeader(AUTHORIZATION);
    if (hasText(bearer) && bearer.startsWith(BEARER_PREFIX)) {
      return bearer.substring(7);
    }
    return null;
  }
}

