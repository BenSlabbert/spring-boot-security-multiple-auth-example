package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  // see the answer here
  // https://stackoverflow.com/questions/54706291/spring-multiple-authentication-methods-for-different-api-endpoints
  // http - the security filter chain
  // http.antMatcher - the entry point to the security filter chain
  // http.authorizeRequests - start of my endpoint access restrictions
  // http.authorizeRequests.antMatchers - list of URLs with specific access

  @Order(1)
  @Configuration
  @RequiredArgsConstructor
  public static class ApiWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationProviderV1 customAuthenticationProviderV1;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

      auth.authenticationProvider(customAuthenticationProviderV1);
      auth.inMemoryAuthentication()
          .withUser("memuser")
          .password(passwordEncoderV1().encode("pass"))
          .roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.antMatcher("/v1/**") // customized entry point
          .authorizeRequests()
          .antMatchers("/v1/**")
          .authenticated()
          .and()
          .httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoderV1() {
      return new BCryptPasswordEncoder();
    }
  }

  @Order(2)
  @Configuration
  @RequiredArgsConstructor
  public static class ApiTokenSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationProviderV2 customAuthenticationProviderV2;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

      auth.authenticationProvider(customAuthenticationProviderV2);
      auth.inMemoryAuthentication()
          .withUser("memuser2")
          .password(passwordEncoderV2().encode("pass"))
          .roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.antMatcher("/v2/**") // customized entry point
          .authorizeRequests()
          .antMatchers("/v2/**")
          .authenticated()
          .and()
          .httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoderV2() {
      return new BCryptPasswordEncoder();
    }
  }
}
