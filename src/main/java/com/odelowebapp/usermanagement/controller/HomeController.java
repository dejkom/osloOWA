/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.usermanagement.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.omnifaces.util.Faces;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 *
 * @author harsha
 */
@Named
@RequestScoped
public class HomeController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private SecurityContext securityContext;

    private String userEmail;

    private String avtiveGroup;

    private String name;

    private String firstname;
    private String lastname;
    private String externalId;
    private String codeksid;

    private String mail;
    private String telephone;
    private String mobilephone;
    private String jobtitle;

    private boolean loggedIn;
    private boolean roleCheck;

    private Subject currentUser;

    public boolean isRoleCheck(String rolename) {
        try {
            System.out.println("Sem v isRoleCheck, searching for role: " + rolename + " result will be:" + SecurityUtils.getSubject().hasRole(rolename));
            if (currentUser == null) {
                currentUser = SecurityUtils.getSubject();
            }
            return currentUser.hasRole(rolename);
        } catch (Exception e) {
            System.out.println("NAPAKAA:" + e.getMessage());
            return false;
        }
    }
    
    public void testMethod(int parameter){
        System.out.println("Sem v testMethod():"+parameter);
    }

    public boolean isRoleCheck() {
        return roleCheck;
    }

    public void setRoleCheck(boolean roleCheck) {
        this.roleCheck = roleCheck;
    }

    public String getUserEmail() {
        if (isLoggedIn()) {
            return securityContext.getCallerPrincipal().getName();
        }
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getAvtiveGroup() {
        return (String) Faces.getSession().getAttribute("activeGroup");
    }

    public void setAvtiveGroup(String avtiveGroup) {
        this.avtiveGroup = avtiveGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getCodeksid() {
        return codeksid;
    }

    public void setCodeksid(String codeksid) {
        this.codeksid = codeksid;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

//        //isLoggedIn dela čudno, zmeraj false. Napišemo svoj parameter loggedin
//	public boolean isLoggedIn() {
//                System.out.println("isLoggedIn:"+securityContext != null && securityContext.getCallerPrincipal() != null);
//		return securityContext != null && securityContext.getCallerPrincipal() != null;
//	}    
    @PostConstruct
    public void init() {
        Subject currentUser = SecurityUtils.getSubject();

        //this.name = (String) currentUser.getSession().getAttribute("username");
        if (null != currentUser.getSession().getAttribute("username")) {
            this.name = (String) currentUser.getSession().getAttribute("username");
            this.firstname = (String) currentUser.getSession().getAttribute("firstname");
            this.lastname = (String) currentUser.getSession().getAttribute("lastname");
            this.externalId = (String) currentUser.getSession().getAttribute("externalid");
            this.codeksid = currentUser.getSession().getAttribute("codeksid") + "";

            this.jobtitle = (String) currentUser.getSession().getAttribute("jobtitle");
            this.telephone = (String) currentUser.getSession().getAttribute("telephone");
            this.mobilephone = (String) currentUser.getSession().getAttribute("mobilephone");
            this.mail = (String) currentUser.getSession().getAttribute("mail");

            this.loggedIn = true;

        } else {
            this.name = "Guest";
            this.firstname = "Visitor";
            this.loggedIn = false;
        }
    }

//	public String logout() throws ServletException {
//		getHttpRequestFromFacesContext().logout();
//                //TO JE NESPREMENJENO, V NAŠEM PRIMERU NE DELA, TREBA DATI NOV LINK
//		return "/login.xhtml?faces-redirect=true";
//	}
    private HttpServletRequest getHttpRequestFromFacesContext() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }
}
