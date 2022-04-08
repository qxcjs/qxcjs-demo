package com.qxcjs.demo.security;


import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.ZoneId;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log4j2
public class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void generate() {
        String token = jwtTokenProvider.generate("liss");
        log.info("jwt token : {}", token);
    }

    @Test
    public void parse() {
        Claims claims = jwtTokenProvider.parse("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsaXNzIiwiaWF0IjoxNjQ2MDE2NDA0LCJleHAiOjE2NDYxMDI4MDR9.WwqZPyQGhNcomM_QWUNb9Cc4r1h21nO6RqhWx3J0adgjpsIYHKcBQJsHMXd51OQcq12tMIMwUfSjNPqSzN43Ig");
        log.info("subject : {} , expiration : {}", claims.getSubject(), Instant.ofEpochMilli(claims.getExpiration().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());
    }
}