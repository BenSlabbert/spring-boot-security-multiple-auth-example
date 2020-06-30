package com.example.demo;

import java.util.Collections;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProviderV1 implements AuthenticationProvider {
  @Override
  public Authentication authenticate(Authentication auth) {
    String username = auth.getName();
    String password = auth.getCredentials().toString();

    if ("externaluser1".equals(username) && "pass".equals(password)) {
      return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
    } else {
      throw new BadCredentialsException("External system authentication failed");
    }
  }

  @Override
  public boolean supports(Class<?> auth) {
    return auth.equals(UsernamePasswordAuthenticationToken.class);
  }
}
