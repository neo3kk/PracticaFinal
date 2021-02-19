package com.rest.vue.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Service
public class TokenService {

    @Value("${token.secret}")
    String tokenSecret;


    @Value("${token.expiration.time}")
    String tokenExpirationTime;




    public String newToken(String username) {

        System.out.println(username);
        String token = JWT.create()
                .withSubject(username)
                //.withExpiresAt(new Date(System.currentTimeMillis() + tokenExpirationTime))
                .sign(Algorithm.HMAC512(tokenSecret.getBytes()));
        return token;
    }

    public String getSubject(String token) {
        String subject = JWT.require(Algorithm.HMAC512(tokenSecret.getBytes()))
                .build()
                .verify(token)
                .getSubject();
        return subject;
    }
}
