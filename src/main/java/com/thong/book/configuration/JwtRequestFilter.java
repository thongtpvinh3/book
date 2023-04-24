package com.thong.book.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thong.book.common.JwtConstant;
import com.thong.book.common.JwtUtil;
import com.thong.book.common.ResponseObject;
import com.thong.book.exception.AuthorizationException;
import com.thong.book.service.UserServiceSecurity;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserServiceSecurity userService;
    private final JwtUtil jwtUtil;
    private final ObjectMapper mapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(JwtConstant.JWT_HEADER);
        String token;
        String username;
        if (header != null && header.startsWith(JwtConstant.JWT_TOKEN_PREFIX)) {
            token = header.substring(JwtConstant.JWT_TOKEN_PREFIX.length());
            try {
                username = jwtUtil.extractUsername(token);
            } catch (JwtException e) {
                AuthorizationException exception = new AuthorizationException(e.getMessage());
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                response.getWriter().write(mapper.writeValueAsString(new ResponseObject(exception.getMessage(), HttpStatus.valueOf(exception.getStatus().value()))));
                return;
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(username);
                if (jwtUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
