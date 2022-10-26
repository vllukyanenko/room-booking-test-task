package com.aim.booking.utils;

import com.aim.booking.security.login.LoginRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class TestUtils {


  public HttpEntity getHttpEntity(String token, Object body) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + token);
    if (body == null) {
      return new HttpEntity<>(headers);
    }
    return new HttpEntity<>(body, headers);
  }

  public HttpEntity loginHttpEntity(LoginRequest request) {
    HttpHeaders headers = new HttpHeaders();
    return new HttpEntity<>(request, headers);
  }
}
