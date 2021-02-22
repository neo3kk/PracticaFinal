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
/*    @Autowired
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
        try {
            String token = header.replace("Bearer ", "");
            String email = tokenService.getSubject(token);
            System.out.println(email);
            request.setAttribute("email", email);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }*/
}
