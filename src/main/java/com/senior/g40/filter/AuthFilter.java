/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author PNattawut
 */
public class AuthFilter implements Filter {

    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    public AuthFilter() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpReq = ((HttpServletRequest) request);
        if (request.getParameter("opt") != null ? request.getParameter("opt").equals("rregis") : false) {
            chain.doFilter(request, response);
            return;
        }
        if (request.getParameter("opt") != null ? request.getParameter("opt").equals("admin") : false) {
            chain.doFilter(request, response);
            return;
        }
        if (debug) {
            if (httpReq.getSession(false) != null) {
                if (httpReq.getSession(false).getAttribute("pf") != null) {
                    chain.doFilter(request, response);
                }
            } else {
                request.setAttribute("msg", "<p style='color:red'>Only Rescuer User is Authorized.</p>");
                httpReq.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
            }
        }
    }

    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

}
