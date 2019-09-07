package com.dsc.housemarket.SecurityConfiguration;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.dsc.housemarket.SecurityConfiguration.SecurityParameters.CSRF_NAME;

public class CSRFTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        if(csrfToken != null) {

            Cookie cookieCsrf = WebUtils.getCookie(request, CSRF_NAME);
            String tokenValue = csrfToken.getToken();

            if(cookieCsrf == null || tokenValue != null && !tokenValue.equals(cookieCsrf.getValue())) {
                cookieCsrf = new Cookie(CSRF_NAME, tokenValue);
                cookieCsrf.setPath("/");
                response.addCookie(cookieCsrf);
            }
        }

        filterChain.doFilter(request, response);
    }
}
