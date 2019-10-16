package com.example.filter;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;
import org.springframework.core.env.Environment;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class URLFilterTest {

    @Test
    public void doFilter_redirect() throws Exception {
        FilterChain chain = mock(FilterChain.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Environment environment = mock(Environment.class);

        when(environment.getActiveProfiles()).thenReturn(new String[]{});
        when(environment.getDefaultProfiles()).thenReturn(new String[]{"default"});

        URLFilter urlFilter = new URLFilter();

        FieldUtils.writeField(urlFilter,"environment",environment,true);
        urlFilter.doFilter(request,response,chain);
        verify(response,atLeastOnce()).sendRedirect(any());
    }

    @Test
    public void doFilter_passThrough() throws Exception {
        FilterChain chain = mock(FilterChain.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Environment environment = mock(Environment.class);

        when(environment.getActiveProfiles()).thenReturn(new String[]{});
        when(environment.getDefaultProfiles()).thenReturn(new String[]{"default"});

        Map<String,String[]> map = new HashMap<>();
        map.put("profile",new String[]{"something"});

        when(request.getParameterMap()).thenReturn(map);

        URLFilter urlFilter = new URLFilter();

        FieldUtils.writeField(urlFilter,"environment",environment,true);
        urlFilter.doFilter(request,response,chain);
        verify(chain,atLeastOnce()).doFilter(any(),any());
    }
}