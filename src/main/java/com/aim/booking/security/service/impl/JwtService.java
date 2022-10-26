package com.aim.booking.security.service.impl;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class JwtService {

  @Value("${app.security.jwt.secret}")
  private String jwtSecret;

  @Value("${app.security.jwt.expirationMs}")
  private long jwtExpirationMs;

  private static final String TOKEN_PROPERTY_EMAIL = "email";
  private static final String USER_TYPE = "user";
  private static final String TOKEN_PROPERTY_TYPE = "type";

  public String generateUserAuthenticationToken(String email, String id, Date date) {
    Map<String, String> tokenData = new HashMap<>();
    tokenData.put(TOKEN_PROPERTY_EMAIL, email);
    if (date == null) {
      long expMillis = System.currentTimeMillis() + jwtExpirationMs;
      date = new Date(expMillis);
    }
    return generateAuthenticationToken(tokenData, id, date, USER_TYPE);
  }

  private String generateAuthenticationToken(Map<String, String> tokenData, String id, Date
      date,
      String type) {

    tokenData.put(TOKEN_PROPERTY_TYPE, type);

    return Jwts.builder()

        .claim("tokenData", tokenData)
        .setExpiration(date)
        .setId(id)
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }


  public String getEmailFromJwtToken(String token) {
    Claims tokenBody = getTokenBody(token);
    return getTokenData(tokenBody).get(TOKEN_PROPERTY_EMAIL);
  }

  public String getTokenDataType(String token) {
    return getPropertyFromToken(token, TOKEN_PROPERTY_TYPE);
  }

  public boolean validateToken(String token) {
    boolean valid = false;
    try {
      Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token);
      valid = true;
    } catch (ExpiredJwtException expEx) {
      log.error("Token expired");
    } catch (UnsupportedJwtException unsEx) {
      log.error("Unsupported jwt");
    } catch (MalformedJwtException mjEx) {
      log.error("Malformed jwt");
    } catch (SignatureException sEx) {
      log.error("Invalid signature");
    } catch (Exception e) {
      log.error("Invalid token");
    }
    return valid;
  }

  public String getPropertyFromToken(String token, String property) {
    try {
      Claims tokenBody = getTokenBody(token);
      return getTokenData(tokenBody).get(property);
    } catch (Exception e) {
      log.error("Error during extract property {} from token", property, e);
      return null;
    }
  }

  private Claims getTokenBody(String token) {
    return Jwts.parserBuilder().setSigningKey(jwtSecret).build()
        .parseClaimsJws(token).getBody();
  }

  private Map<String, String> getTokenData(Claims claims) {
    Map tokenData = claims.get("tokenData", Map.class);
    Map<String, String> map = new HashMap<>();
    for (Object o : tokenData.keySet()) {
      map.put((String) o, (String) tokenData.get(o));
    }
    return map;
  }
}
