package com.rest.vue.interceptors;


import com.rest.vue.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {
   @Autowired
    TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(request.getMethod().equalsIgnoreCase("options")){
            return true;
        }

        String header = request.getHeader("authorization");

       if (header == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        String token = header.replace("Bearer ", "");
        try {
            String email = tokenService.verifyToken(token);
            if(email == null){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
           System.out.println(email);
           request.setAttribute("email", email);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
