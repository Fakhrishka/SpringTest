package com.example.demo.security.jwt;

import com.example.demo.exceptions.CustomJwtException;
import com.example.demo.security.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final static String TOKENEXPIRED = "Token has expired";
    private final static String TOKENINVALID = "Token is not valid";

    @Autowired
    JWTTokenProvider jwtTokenProvider;

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            final String authHeader = request.getHeader("Authorization");

            if (request.getRequestURI().equals("/api/auth") ||
                    request.getRequestURI().equals("/api/auth/login")
            ) {
                filterChain.doFilter(request, response);
                return;
            }

            if(authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new CustomJwtException(TOKENINVALID);
            }

            String jwt = authHeader.substring(7);

            if (jwtTokenProvider.isTokenBlackListed(jwt)) {
                throw new CustomJwtException(TOKENINVALID);
            }

            String username = jwtTokenProvider.getUsernameFromToken(jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtTokenProvider.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                else
                    throw new CustomJwtException(TOKENINVALID);
            }
            else
                throw new CustomJwtException(TOKENINVALID);
            filterChain.doFilter(request, response);

        }
        catch (CustomJwtException e)
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // Set unauthorized status
            response.getWriter().write("Unauthorized: " + e.getMessage());
        }
    }
}
