/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.usermanagement.controller;

import org.apache.shiro.web.filter.authc.UserFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class FacesAjaxAwareUserFilter extends UserFilter {
	
	private static final String FACES_REDIRECT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<partial-response><redirect url=\"%s\"></redirect></partial-response>";

    @Override
    protected void redirectToLogin(ServletRequest req, ServletResponse res) throws IOException {
        System.out.println("Sem v redirectToLogin - FacesAjaxAwareUserFilter");
        HttpServletRequest request = (HttpServletRequest) req;

        if ("partial/ajax".equals(request.getHeader("Faces-Request"))) {
            res.setContentType("text/xml");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().printf(FACES_REDIRECT_XML, request.getContextPath() + getLoginUrl());
        }
        else {
            super.redirectToLogin(req, res);
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        System.out.println("Sem v onAccessDenied - FacesAjaxAwareUserFilter");
        return super.onAccessDenied(request, response); //To change body of generated methods, choose Tools | Templates.
    }

}
