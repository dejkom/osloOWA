/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.entity;

import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;



/**
 *
 * @author dematjasic
 */
public class DeanFilter extends RolesAuthorizationFilter{

    private static final String message = "Access denied.";
    
    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        System.out.println("Sem v isAccessAllowed()");
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        System.out.println("Sem v onAccessDenied()");
        HttpServletResponse httpResponse ;
                try { httpResponse = WebUtils.toHttp(response); }
                catch (ClassCastException ex) { 
                    // Not a HTTP Servlet operation
                    return super.onAccessDenied(request, response) ;
                }
                if ( message == null )
                    httpResponse.sendError(403) ;
                else
                    httpResponse.sendError(403, message) ;
                return false ;  // No further processing.
    }

    
    
    
    
}
