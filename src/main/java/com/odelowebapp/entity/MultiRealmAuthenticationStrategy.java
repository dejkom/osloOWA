/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.entity;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.realm.Realm;


/**
 *
 * @author dematjasic
 */
public class MultiRealmAuthenticationStrategy extends AtLeastOneSuccessfulStrategy {

    @Override
    public AuthenticationInfo afterAttempt(Realm realm, AuthenticationToken token, AuthenticationInfo singleRealmInfo, AuthenticationInfo aggregateInfo, Throwable t) throws AuthenticationException {
        if (realm instanceof CustomActiveDirectoryRealm) {
            // Determine the Active Directory realm being used
            CustomActiveDirectoryRealm activeDirectoryRealm = (CustomActiveDirectoryRealm) realm;
            String realmName = activeDirectoryRealm.getName();
            System.out.println("REALM NAME:"+realmName);

            // Store the realm name in the AuthenticationInfo object
            SimpleAuthenticationInfo modifiedInfo = new SimpleAuthenticationInfo(aggregateInfo.getPrincipals(), aggregateInfo.getCredentials(), activeDirectoryRealm.getName());
            //modifiedInfo.getPrincipals().add(realmName, realmName);


            return modifiedInfo;
        }

        return super.afterAttempt(realm, token, singleRealmInfo, aggregateInfo, t);
    }

}
