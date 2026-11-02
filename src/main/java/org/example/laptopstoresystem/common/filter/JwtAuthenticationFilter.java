package org.example.laptopstoresystem.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.laptopstoresystem.service.UserService;
import org.example.laptopstoresystem.service.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        String jwtToken = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")){
            jwtToken = authHeader.substring(7);

            try {
                username = jwtUtil.extractUsername(jwtToken);
            }catch (Exception e){
                throw e;
            }
        }


        // username not null and user does not have authentication yet
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){

            // validate to db
            UserDetails userDetails = userService.loadUserByUsername(username);

            // check if jwt token is valid
            if (jwtUtil.validateToken(jwtToken,userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(userDetails , null , userDetails.getAuthorities());

                usernamePasswordAuthToken.setDetails(new WebAuthenticationDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthToken);

            }

        }



        filterChain.doFilter(request,response);
    }
}
