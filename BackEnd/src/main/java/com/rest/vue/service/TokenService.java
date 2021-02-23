package com.rest.vue.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenService {

    @Value("${token.secret}")
    String tokenSecret;


    @Value("${token.expiration.time}")
    String tokenExpirationTime;


    public String newToken(String username) {

        Instant instant = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        System.out.println(username);
        String token = JWT.create()
                .withSubject(username)
                .withExpiresAt(Date.from(instant))
                .sign(Algorithm.HMAC512(tokenSecret.getBytes()));
        return token;
    }

    public String getSubject(String token) {
        try {
            String subject = JWT.require(Algorithm.HMAC512(tokenSecret.getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();
            return subject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String verifyToken(String token) {
        try {
            String subject = String.valueOf(JWT.require(Algorithm.HMAC512(tokenSecret.getBytes()))
                    .build()
                    .verify(token)
                    .getSubject());
            System.out.println("Subject: " + subject);
            return subject;

        } catch (Exception e) {
            System.out.println("TOKEN EXPIRED");
            return null;
        }
    }
}
