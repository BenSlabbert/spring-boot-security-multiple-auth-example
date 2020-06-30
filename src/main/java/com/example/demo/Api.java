package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Api {

  @GetMapping("/v1/echo")
  public ResponseEntity<String> helloV1() {
    return ResponseEntity.ok("hello from v1");
  }

  @GetMapping("/v2/echo")
  public ResponseEntity<String> helloV2() {
    return ResponseEntity.ok("hello from v2");
  }
}
