package com.gipit.bookshop_backend.filters;

import com.gipit.bookshop_backend.components.JwtTokenUtil;
import com.gipit.bookshop_backend.services.impl.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
           String authHeader = request.getHeader("Authorization");
           String token=null;
           String username=null;
           if(authHeader !=null &&  authHeader.startsWith("Bearer ")){
               token=authHeader.substring(7);
               username=jwtTokenUtil.extractUsername(token);
           }
           if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
               UserDetails userDetails=userService.loadUserByUsername(username);
                   if(jwtTokenUtil.validateToken(token,userDetails)){
                       UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                       authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                       SecurityContextHolder.getContext().setAuthentication(authToken);
                   }
           }
           filterChain.doFilter(request, response);
    }
}
