package com.EShop.ecommerce_backend.Security.JWT;



import com.EShop.ecommerce_backend.Security.Services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger logger =
            LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        logger.info(
                "Before filter chain - Authentication = {}",
                SecurityContextHolder.getContext().getAuthentication()
        );
        try {

            String jwt = parseJwt(request);

            if (jwt != null) {

                boolean valid = jwtUtils.validateJwtToken(jwt);

                logger.info("JWT VALID = {}", valid);

                if (valid) {

                    String username =
                            jwtUtils.getUserNameFromJwtToken(jwt);

                    logger.info("USERNAME = {}", username);

                    UserDetails userDetails =
                            userDetailsService
                                    .loadUserByUsername(username);

                    logger.info("AUTHORITIES = {}",
                            userDetails.getAuthorities());

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);

                    logger.info("AUTHENTICATION SET = {}",
                            SecurityContextHolder
                                    .getContext()
                                    .getAuthentication());
                }
            }

        } catch (Exception e) {

            logger.error(
                    "Cannot set user authentication",
                    e
            );
        }
        logger.info("REQUEST URI = {}", request.getRequestURI());
        logger.info("AUTH BEFORE CONTROLLER = {}",
                SecurityContextHolder.getContext().getAuthentication());

        filterChain.doFilter(request, response);


    }


    private String parseJwt(HttpServletRequest request) {

        // First check Authorization: Bearer <token>
        String jwtFromHeader =
                jwtUtils.getJwtFromHeader(request);

        if (jwtFromHeader != null) {
            return jwtFromHeader;
        }

        // If header doesn't exist, check cookie
        String jwtFromCookie =
                jwtUtils.getJwtFromCookies(request);

        if (jwtFromCookie != null) {
            return jwtFromCookie;
        }

        return null;
    }
}
