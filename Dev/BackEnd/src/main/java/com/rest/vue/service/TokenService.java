package com.rest.vue.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class TokenService {

    @Value("${token.secret}")
    String tokenSecret;


    @Value("${token.expiration.time}")
    long tokenExpirationTime;


    public String newToken(String username) {
        String token = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenExpirationTime))
                .sign(Algorithm.HMAC512(tokenSecret.getBytes()));
        return token;
    }

    public String getSubject(HttpServletRequest request) {
        String header = request.getHeader("authorization");
        String token = header.replace("Bearer ", "");
        try {
            String subject = JWT.require(Algorithm.HMAC512(tokenSecret.getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();
            if (subject != null) {
                return subject;
            } else {
               return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String verifyToken(String token) {
        try {
            String subject = JWT.require(Algorithm.HMAC512(tokenSecret.getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();
            return subject;

        } catch (Exception e) {
            return null;
        }
    }
}
