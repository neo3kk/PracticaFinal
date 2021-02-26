package com.rest.vue.interceptors;


import com.rest.vue.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class GetInterceptorCategory implements HandlerInterceptor {
    @Autowired
    TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            return true;
        }
        String header = request.getHeader("AUTHORIZATION");
        if (header == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        String token = header.replace("Bearer ", "");
        if (!request.getMethod().equalsIgnoreCase("GET") && !request.getMethod().equalsIgnoreCase("PUT")) {
            try {
                String email = tokenService.verifyToken(token);
                if (email == null) {
                    System.out.println(email);
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return false;
                }
                request.setAttribute("email", email);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
