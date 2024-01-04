/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.authentication;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author dematjasic
 */
@Named("sessionInfo")
@ViewScoped
public class SessionInfo implements Serializable {

    public SessionInfo() {
    }
    
    private Collection<Session> activeSessions;
    private String activeSessionsCount;
    
    @PostConstruct
    public void init() {
        System.out.println("Sem v init() SessionInfo");
        DefaultSecurityManager manager = (DefaultSecurityManager) SecurityUtils.getSecurityManager();
        DefaultWebSessionManager sessionManager = (DefaultWebSessionManager) manager.getSessionManager();
        activeSessions = (Collection<Session>)sessionManager.getSessionDAO().getActiveSessions();
        try{
        // invoke "sessionManager.getActiveSessions()" via reflection:
//        Method getActiveSessionsMethod = DefaultWebSessionManager.class.getDeclaredMethod("getActiveSessions");
//        getActiveSessionsMethod.setAccessible(true);
//        activeSessions = (Collection<Session>) getActiveSessionsMethod.invoke(sessionManager);
        

        System.out.println("Active sessions:"+activeSessions.toString());
        activeSessionsCount = activeSessions.size()+"";
        
        for(Session s : activeSessions){
            System.out.println("---Seja---");
            System.out.println("ID:"+s.getId());
            System.out.println("Start:"+s.getStartTimestamp());
            System.out.println("LastAccess:"+s.getLastAccessTime());
            Collection<Object> attributeKeys = s.getAttributeKeys();
            for(Object o : attributeKeys){
                System.out.println("Akey:"+o.toString());
            }
            
        }
        } catch(Exception e){
            System.out.println("NAPAKA:"+e);
        }
        
    }

    public Collection<Session> getActiveSessions() {
        return activeSessions;
    }

    public void setActiveSessions(Collection<Session> activeSessions) {
        this.activeSessions = activeSessions;
    }



    public String getActiveSessionsCount() {
        return activeSessionsCount;
    }

    public void setActiveSessionsCount(String activeSessionsCount) {
        this.activeSessionsCount = activeSessionsCount;
    }
    
    
    
}
