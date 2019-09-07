package com.dsc.housemarket.SecurityConfiguration;

import com.dsc.housemarket.Services.CustomUserDetailService;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.dsc.housemarket.SecurityConfiguration.SecurityParameters.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final CustomUserDetailService customUserDetailService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, CustomUserDetailService customUserDetailService) {
        super(authenticationManager);
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        Cookie cookie = getCookieFromRequest(request, HEADER_STRING);

        if(cookie == null){
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);

    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request){

        Cookie cookie = getCookieFromRequest(request, HEADER_STRING);

        if(cookie == null) return null;

        String username = Jwts.parser().setSigningKey(SECRET)
                .parseClaimsJws(cookie.getValue())
                .getBody()
                .getSubject();

        UserDetails userDetails = customUserDetailService.loadUserByUsername(username);

        return username != null
                ? new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities())
                : null;
    }

    static Cookie getCookieFromRequest(HttpServletRequest request, String name){
        Cookie[] cookies = request.getCookies();

        if(cookies == null) return null;

        Cookie cookie = null;

        for (Cookie cks: cookies) {
            if(cks.getName().equals(name)) {
                cookie = cks;
            }
        }

        return cookie;
    }
}
