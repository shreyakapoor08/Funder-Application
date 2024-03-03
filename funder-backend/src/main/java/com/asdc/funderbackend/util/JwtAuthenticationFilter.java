package com.asdc.funderbackend.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.asdc.funderbackend.controller.UserController;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer";
    private static final int SEVEN = 7;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    	if(LOGGER.isInfoEnabled()) {
			LOGGER.info("Entering into doFilterInternal(){}");
		}    	
    	
        String requestHeader = request.getHeader(AUTHORIZATION);
        String username = null;
        String token = null;

        if (requestHeader != null && requestHeader.startsWith(BEARER)) {
            token = requestHeader.substring(SEVEN);
            try {
                username = this.jwtUtil.extractUsername(token);
            } catch (IllegalArgumentException e) {
                logger.info("Illegal Argument while fetching the username");
                e.printStackTrace();
            } catch (ExpiredJwtException e) {
                logger.info("token is expired");
                e.printStackTrace();
            } catch (MalformedJwtException e) {
                logger.info("Invalid Token");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            logger.info("Invalid Header Value !! ");
        }
 
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            Boolean validateToken = this.jwtUtil.validateToken(token, userDetails);

            if (validateToken) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                logger.info("Validation fails !!");
            }

        }
        filterChain.doFilter(request, response);
    }
}