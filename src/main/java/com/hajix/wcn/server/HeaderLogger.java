package com.hajix.wcn.server;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;

import org.apache.log4j.Logger;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

public class HeaderLogger implements ContainerRequestFilter, ContainerResponseFilter {
    
    private static final Logger log = Logger.getLogger(HeaderLogger.class);
    private static final boolean onlyOnResponse = true;
    
    @Context HttpServletRequest req;
    

    @Override
    public ContainerRequest filter(ContainerRequest request) {
        if (!onlyOnResponse) {
            log.info(String.format("%s %s %s (%s)", req.getRemoteAddr(), request.getMethod(), request.getPath(), getUserName(request)));
        }
        return request;
    }
    
    private String getUserName(ContainerRequest request) {
        Cookie firstName = request.getCookies().get("wcnFirstName");
        Cookie lastName = request.getCookies().get("wcnLastName");
        if (firstName != null && lastName != null) {
            return firstName.getValue() + " " + lastName.getValue();
        } else {
            return "N/A";
        }
    }

    @Override
    public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
        log.info(String.format("%s %s %s (%s) -> %d", req.getRemoteAddr(), request.getMethod(), request.getPath(), getUserName(request), response.getStatus()));
        return response;
    }

}
