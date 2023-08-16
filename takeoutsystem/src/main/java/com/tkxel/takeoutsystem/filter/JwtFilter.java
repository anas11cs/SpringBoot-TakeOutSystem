package com.tkxel.takeoutsystem.filter;

import com.tkxel.takeoutsystem.service.impl.AppUserDetailsServiceImpl;
import com.tkxel.takeoutsystem.utils.JwtUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
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
@Log4j2
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AppUserDetailsServiceImpl appUserDetailsServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authorizationHeader = httpServletRequest.getHeader("Authorization");

            String token = null;
            String userName = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);
                userName = jwtUtils.extractUsername(token);
            }

            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = appUserDetailsServiceImpl.loadUserByUsername(userName);

                if (jwtUtils.validateToken(token , userDetails)) {

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails , null , userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(httpServletRequest , httpServletResponse);
        }
        catch (AuthenticationException e){
            log.error(e);
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

}
