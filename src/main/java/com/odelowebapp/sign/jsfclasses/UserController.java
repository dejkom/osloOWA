package com.odelowebapp.sign.jsfclasses;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("userController")
@ViewScoped
public class UserController implements Serializable {

    private String username;
    private String password;
    private final String[] searchBaseArray = new String[]{
        "OU=Power Users (PC or Notebook\\+Citrix),OU=Citrix Users,OU=SAPS,DC=pr,DC=lighting,DC=int",
        "OU=BGinfo,OU=Normal Users (Thin Clients),OU=Citrix Users,OU=SAPS,DC=pr,DC=lighting,DC=int",
        "OU=Normal Users (Thin Clients),OU=Citrix Users,OU=SAPS,DC=pr,DC=lighting,DC=int",
        "OU=BGInfo,OU=Power Users (PC or Notebook\\+Citrix),OU=Citrix Users,OU=SAPS,DC=pr,DC=lighting,DC=int",
        "OU=IT,OU=SAPS,DC=pr,DC=lighting,DC=int"
    };
    
    public static final String HOME_URL = "/osloWebApp/faces/index.xhtml";


    private Session session;
    private Subject subject;
    private String serverservinguser;

    public UserController() {
    }

    @PostConstruct
    public void init() {
        System.out.println("Sem v init() metodi UserControllerja");
        String name="";
        try {
            serverservinguser = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ex) {
            serverservinguser = "Unknown server";
        }
        try {
            name = SecurityUtils.getSubject().getPrincipal().toString();           
        } catch (Exception e) {
            //System.out.println("NAPAKA:"+e);
            name = "Gost";
        }
        System.out.println("Uporabnik:"+name+" uname:"+username+" pass:"+password);
        
        
    }
    
    public void loginCustom() throws IOException{
        System.out.println("Sem v loginCustom()");
        System.out.println("uname:"+username+" pass:"+password);
        final ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        
        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password, false));
            SavedRequest savedRequest = WebUtils.getAndClearSavedRequest((ServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest());
            externalContext.redirect(savedRequest != null ? savedRequest.getRequestUrl() : HOME_URL);
        }
        catch (AuthenticationException e) {
            System.out.println("Unknown user, please try again");
            e.printStackTrace(); // TODO: logger.
        }
          
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }

    public String getServerservinguser() {
        return serverservinguser;
    }

    public void setServerservinguser(String serverservinguser) {
        this.serverservinguser = serverservinguser;
    }
    
    

    public String getUsername() {
        try {
            return SecurityUtils.getSubject().getPrincipal().toString();
        } catch (Exception e) {
            //System.out.println("NAPAKA:"+e);
            return "Gost";
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public void setSession(Session session) {
        System.out.println("Sem v setSession. START:" + session.getStartTimestamp() + "  lastTS:" + session.getLastAccessTime());
        this.session = session;
    }

    public Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public boolean hasRole(String roles) {
        Subject currentUser = SecurityUtils.getSubject();
        List<String> roleList = Arrays.asList(roles.split(","));
        boolean[] hasRoleArray = currentUser.hasRoles(roleList);
        for (boolean hasRole : hasRoleArray) {
            if (hasRole == true) {
                return true;
            }
        }
        return false;
    }

    public void logout() throws IOException {
        System.out.println("Sem v UserController logout()");
        final ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        SecurityUtils.getSubject().logout();
        externalContext.invalidateSession();  // cleanup user related session state
        //externalContext.redirect("faces/login.xhtml?faces-redirect=true");
        //externalContext.redirect("faces/index.xhtml?faces-redirect=true");
        externalContext.redirect("/osloWebApp/faces/index.xhtml");
    }

    //NOT IN USE
    @Deprecated
    public void login() {
        System.out.println("Sem v DEPRECATED login() metodi");
        String searchFilter = "(&(objectClass=person)(sAMAccountName=" + username + ")(userPrincipalName=" + username + "@pr.lighting.int))";

        org.apache.shiro.realm.activedirectory.ActiveDirectoryRealm realm = new org.apache.shiro.realm.activedirectory.ActiveDirectoryRealm();
        realm.setUrl("ldap://10.36.64.39:389");
        realm.setSystemUsername(username + "@pr.lighting.int");
        realm.setSystemPassword(password);
        realm.setSearchFilter(searchFilter);

        for (int i = 0; i < searchBaseArray.length; i++) {
            realm.setSearchBase(searchBaseArray[i]);
            SecurityManager securityManager = new DefaultSecurityManager(realm);
            SecurityUtils.setSecurityManager(securityManager);
            UsernamePasswordToken token = new UsernamePasswordToken(this.username + "@pr.lighting.int", this.password);

            try {
                Subject currentUser = securityManager.login(SecurityUtils.getSubject(), token);
                if (currentUser.isAuthenticated()) {
                    Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Is authenticated");
                    break;
                } else {
                    Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Is not authenticated");
                }
            } catch (UnknownAccountException uae) {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Unknown account for user: {0} with searchBase: {1}", new Object[]{username, searchBaseArray[i]});
            } catch (IncorrectCredentialsException ice) {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Incorrect credentials for user: {0} with searchBase: {1}", new Object[]{username, searchBaseArray[i]});
            } catch (LockedAccountException lae) {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Locked account for user: {0} with searchBase: {1}", new Object[]{username, searchBaseArray[i]});
            } catch (ExcessiveAttemptsException eae) {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Excessive attempts for user: {0} with searchBase: {1}", new Object[]{username, searchBaseArray[i]});
            } catch (AuthenticationException ae) {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Authentication failed for user: {0} with searchBase: {1}", new Object[]{username, searchBaseArray[i]});
            }
        }
    }

}
