package com.example.projeto02m02.security;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

// Filtro onde todas as requisições serão capturadas para autenticar
public class JwtApiAutenticacaoFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // estabelecer a autenticação para requisição
        Authentication authentication = new JwtTokenAuthenticationService()
                .getAuthentication((HttpServletRequest) request);

        // colocar o processo de autenticação no spring security
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Continua o processo
        chain.doFilter(request, response);
    }
}
