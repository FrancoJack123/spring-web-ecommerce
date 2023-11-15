package com.example.web.springwebecommerce.interceptor;

import com.example.web.springwebecommerce.entidad.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        if (request.getSession().getAttribute("usuario") == null) {
            response.sendRedirect("/Ecommerce/");
            return false;
        }

        if (usuario.getCargoId().getRolId() == 3){
            String requestURI = request.getRequestURI();
            if (requestURI.startsWith("/Dashboard/")) {
                response.sendRedirect("/Ecommerce/");
                return false;
            }
        }

        return true;
    }

}