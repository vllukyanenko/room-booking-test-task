package com.aim.booking.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;

public interface EncryptionService extends PasswordEncoder {

  String encryptPassword(String input);

  boolean checkPassword(String plainPassword, String encryptedPassword);
}
