package com.ikvant.remotetask.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikvant.remotetask.entities.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.ikvant.remotetask.security.SecurityConstants.*;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(name, password));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
        Claims claims = Jwts.claims().setSubject(((User) auth.getPrincipal()).getUsername());
        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + token);
        res.addHeader("Access-Control-Expose-Headers", HEADER_STRING);
    }


}