package com.flexisaf.controllers;
import com.flexisaf.models.user;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

@WebFilter("/secured/*")
public class RoleFilter implements Filter  {
    private Properties role;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        role = loadRoleProperties();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) resp;
        String requestedUrl = httpRequest.getServletPath() ;

        HttpSession session = httpRequest.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpResponse.getWriter().println("Please login");
            return;
        }

        user.UserType Role = ((user) session.getAttribute("user")).getUserType();

        if (!hasRequiredRole( Role  ,requestedUrl)){
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().println("You are not authorized to this page");
            return;
        }
        filterChain.doFilter(req,resp);
    }

    @Override
    public void destroy() {

    }
    private Properties loadRoleProperties(){
        Properties role = new Properties();
        try(InputStream input = getClass().getClassLoader().getResourceAsStream("role.properties")){
            role.load(input);
        } catch (IOException e){
            throw new RuntimeException("Unable to load properties file", e);
        }
        return  role;
    }

    private boolean hasRequiredRole( user.UserType userRole  , String requestedUrl){
        String allowedRoles = role.getProperty(requestedUrl);
        if (allowedRoles != null && Arrays.asList(allowedRoles.split(",")).contains(userRole.name())){
            return userRole == user.UserType.admin || (userRole == user.UserType.owner );
        }

        return false;
    }

}