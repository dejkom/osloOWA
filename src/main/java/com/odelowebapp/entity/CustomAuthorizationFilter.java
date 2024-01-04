/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.entity;

import java.io.IOException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author itstudent
 */
public class CustomAuthorizationFilter extends AuthorizationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest sr, ServletResponse sr1, Object o) throws Exception {
        System.out.println("Sem v CustomAuthorizationFilter isAccessAllowed()");
        
        Subject currentUser = SecurityUtils.getSubject();
        List<String> roleList = Arrays.asList((String[]) o);
        boolean[] hasRoleArray = currentUser.hasRoles(roleList);
        for (boolean hasRole : hasRoleArray) {
            if (hasRole == true) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        
        System.out.println("Sem v CustomAuthorizationFilter onAccessDenied()");
        
        return true;
    }
    
    

}
