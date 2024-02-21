package net.microwonk.studentenverwaltung.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    JwtTokenService jwtTokenService;
    JwtUserDetailsService jwtUserDetailsService;

    public JwtRequestFilter(JwtTokenService jwtTokenService, JwtUserDetailsService jwtUserDetailsService) {
        this.jwtTokenService = jwtTokenService;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain
    ) throws ServletException, IOException {
        // look for Bearer auth header
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        final String token = header.substring(7);
        final String username = jwtTokenService.validateTokenAndGetUsername(token);
        if (username == null) {
            // validation failed or token expired
            chain.doFilter(request, response);
            return;
        }

        // set user details on spring security context
        final JwtUserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // continue with authenticated user
        chain.doFilter(request, response);
    }
}
