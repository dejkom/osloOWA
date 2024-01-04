/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.entity;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.activedirectory.ActiveDirectoryRealm;
import org.apache.shiro.realm.ldap.LdapContextFactory;
import org.apache.shiro.realm.ldap.LdapUtils;
import org.apache.shiro.subject.PrincipalCollection;

import javax.naming.NamingException;
import javax.naming.ldap.LdapContext;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author itstudent
 */
@Deprecated
public class CustomADRealm extends ActiveDirectoryRealm { 
    
    @Override
    protected AuthorizationInfo buildAuthorizationInfo(Set<String> roleNames) {
        AuthorizationInfo test = super.buildAuthorizationInfo(roleNames);
        return test;//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected AuthorizationInfo queryForAuthorizationInfo(PrincipalCollection principals, LdapContextFactory ldapContextFactory) throws NamingException {
        String username = (String) getAvailablePrincipal(principals);
        LdapContext ldapContext = ldapContextFactory.getSystemLdapContext();
        //AuthorizationInfo test = super.queryForAuthorizationInfo(principals, ldapContextFactory);
        Set<String> roleNames;
        try {
            roleNames = getRoleNamesForUser(username, ldapContext);
        } finally {
            LdapUtils.closeContext(ldapContext);
        }
        return buildAuthorizationInfo(roleNames);
    }

    @Override
    protected AuthenticationInfo buildAuthenticationInfo(String username, char[] password) {
        AuthenticationInfo test = super.buildAuthenticationInfo(username, password);
        return test; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected AuthenticationInfo queryForAuthenticationInfo(AuthenticationToken token, LdapContextFactory ldapContextFactory) throws NamingException {
        AuthenticationInfo test = super.queryForAuthenticationInfo(token, ldapContextFactory);
        return test;//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setGroupRolesMap(Map<String, String> groupRolesMap) {
        Map<String, String> test = groupRolesMap;
        super.setGroupRolesMap(groupRolesMap); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Collection<String> getRoleNamesForGroups(Collection<String> groupNames) {
        Collection<String> test = super.getRoleNamesForGroups(groupNames);
        return test;//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Set<String> getRoleNamesForUser(String username, LdapContext ldapContext) throws NamingException {
        Set<String> test = super.getRoleNamesForUser(username, ldapContext);
        return test;//To change body of generated methods, choose Tools | Templates.
    }

}
