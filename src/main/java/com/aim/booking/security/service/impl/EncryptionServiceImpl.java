package com.aim.booking.security.service.impl;

import com.aim.booking.security.service.EncryptionService;
import lombok.extern.log4j.Log4j2;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class EncryptionServiceImpl implements EncryptionService {

  private final StrongPasswordEncryptor passwordEncryptor;

  public EncryptionServiceImpl(StrongPasswordEncryptor passwordEncryptor) {
    this.passwordEncryptor = passwordEncryptor;
  }

  @Override
  public String encryptPassword(String input) {
    return passwordEncryptor.encryptPassword(input);
  }

  @Override
  public boolean checkPassword(String plainPassword, String encryptedPassword) {
    return passwordEncryptor.checkPassword(plainPassword, encryptedPassword);
  }


  @Override
  public String encode(CharSequence charSequence) {
    return encryptPassword(charSequence.toString());
  }

  @Override
  public boolean matches(CharSequence charSequence, String s) {
    return checkPassword(charSequence.toString(), s);
  }
}
