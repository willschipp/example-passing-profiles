package com.example.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class URLFilter implements Filter {

    private String profile;

    @Autowired
    private Environment environment;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (profile == null) {
            loadProfile();
        }//end if

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (httpServletRequest.getParameterMap().containsKey("profile") || (httpServletRequest.getHeader("X-Profile") != null)) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).setHeader("X-Profile",profile);
            ((HttpServletResponse) response).sendRedirect("/?profile=" + profile);
        }//end if
    }

    private void loadProfile() {
        if (environment.getActiveProfiles().length <= 0) {
            profile = environment.getDefaultProfiles()[0];
        } else {
            profile = environment.getActiveProfiles()[0];
        }//end if

    }
}
