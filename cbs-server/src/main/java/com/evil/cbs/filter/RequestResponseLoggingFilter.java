package com.evil.cbs.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(2)
public class RequestResponseLoggingFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        LOGGER.info(
                "Logging Request  {} : {}", req.getMethod(),
                req.getRequestURI());
        chain.doFilter(request, response);
        LOGGER.info(
                "Logging Response :{}",
                res.getContentType());
    }

    @Override
    public void destroy() {

    }
}