/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.entity;

/**
 *
 * @author
 * https://github.com/opticyclic/shiro-spring-examples/blob/master/ms-active-directory-custom/src/main/java/com/github/opticyclic/shiro/realm/CustomActiveDirectoryRealm.java
 */
import java.util.*;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.LdapContext;

//import com.odelowebapp.entity.UserPrincipal_renamed;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.activedirectory.ActiveDirectoryRealm;
import org.apache.shiro.realm.ldap.LdapContextFactory;
import org.apache.shiro.realm.ldap.LdapUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomActiveDirectoryRealm extends ActiveDirectoryRealm {

    private static final Logger log = LoggerFactory.getLogger(CustomActiveDirectoryRealm.class);
    
    private String name;

    /**
     * This is called during the log in process. Authenticate but also store the
     * roles/groups on a custom principal
     */
    @Override
    protected AuthenticationInfo queryForAuthenticationInfo(AuthenticationToken token, LdapContextFactory ldapContextFactory) throws NamingException {
        System.out.println("Sem v queryForAuthenticationInfo");
        System.out.println("Sem v queryForAuthenticationInfo realm name:"+this.name);

        SimpleAuthenticationInfo authenticationInfo = (SimpleAuthenticationInfo) super.queryForAuthenticationInfo(token, ldapContextFactory);
        PrincipalCollection principals = authenticationInfo.getPrincipals();
        System.out.println("Principals list size:"+principals.asList().size());

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        String userPrincipalName = getUserPrincipalName(token);
        
        //System.out.println("Sem v queryForAuthenticationInfo userPrincipalName:"+userPrincipalName);
        

        Set<String> roleNames;
        // Binds using the username and password provided by the user.
        LdapContext ldapContext = null;
        try {
            ldapContext = ldapContextFactory.getLdapContext(userPrincipalName, (usernamePasswordToken.getPassword()));
            roleNames = getRoleNamesForUser(username, ldapContext);
        } finally {
            LdapUtils.closeContext(ldapContext);
        }

        List<UserPrincipal> customPrincipals = getCustomPrincipals(userPrincipalName, roleNames);

        //Merge the custom principals and the main principals
        SimplePrincipalCollection principalCollection = new SimplePrincipalCollection(customPrincipals, CustomActiveDirectoryRealm.class.getName());
        principalCollection.addAll(principals);

        authenticationInfo.setPrincipals(principalCollection);
        
        return authenticationInfo;
    }
    
    

    private List<UserPrincipal> getCustomPrincipals(String userPrincipalName, Set<String> roleNames) {
        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setPrincipalName(userPrincipalName);
        userPrincipal.setRoleNames(roleNames);

        List<UserPrincipal> customPrincipals = new ArrayList<>();
        customPrincipals.add(userPrincipal);
        System.out.println("getCustomPrincipals() vraca vrstic:"+customPrincipals.size());
        for(UserPrincipal u:customPrincipals){
            System.out.println("p:"+u.getPrincipalName());
        }
        return customPrincipals;
    }

    /**
     * This is called during checks for hasRole.Use the roles that we found on
     * login
     *
     * @param principals
     * @param ldapContextFactory
     * @return
     * @throws javax.naming.NamingException
     */
    @Override
    protected AuthorizationInfo queryForAuthorizationInfo(PrincipalCollection principals, LdapContextFactory ldapContextFactory) throws NamingException {
        //System.out.println("Sem v queryForAuthorizationInfo ");
        //UserPrincipal availablePrincipal = (UserPrincipal)getAvailablePrincipal(principals);
        String username = (String) getAvailablePrincipal(principals);
        System.out.println("Sem v queryForAuthorizationInfo username:"+username);
        //Set<String> roleNames = availablePrincipal.getRoleNames();
        // Perform context search
        LdapContext ldapContext = ldapContextFactory.getSystemLdapContext();
        Set<String> roleNames;
        try {
            roleNames = getRoleNamesForUser(username, ldapContext);
        } finally {
            LdapUtils.closeContext(ldapContext); //ALI SE LAHKO TO ZAPRE PREDEN KLIČEM buildAuthorizationInfo() v nadaljevanju ?
        }

        //System.out.println("Klicem buildAuthorizationInfo(roleNames), roleNames size:"+roleNames.size());
        for(String s:roleNames){
            System.out.println("RoleName:"+s);
        }
        return buildAuthorizationInfo(roleNames);
    }

    /**
     * Copied from the super class until the method is made protected
     * https://github.com/apache/shiro/pull/38
     * @throws javax.naming.NamingException
     */
  @Override
  protected Set<String> getRoleNamesForUser(String username, LdapContext ldapContext) throws NamingException {
    System.out.println("Sem v getRoleNamesForUser ");
    Set<String> roleNames;
    roleNames = new LinkedHashSet<String>();

    SearchControls searchCtls = new SearchControls();
    searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
    String[] attrIDs = {"distinguishedName", "sn", "givenname", "mail", "memberOf"};
    searchCtls.setReturningAttributes(attrIDs);

    String userPrincipalName = username;
    if(principalSuffix != null) {
      userPrincipalName += principalSuffix;
    }
    
    //System.out.println("Sem v getRoleNamesForUser userPrincipalName:"+userPrincipalName);
    //System.out.println("Sem v getRoleNamesForUser searchBase:"+searchBase);
    
    //SHIRO-115 - prevent potential code injection:
    
    String searchFilter = "(&(objectClass=user)(objectClass=person)(sAMAccountName="+username+"))";
    Object[] searchArguments = new Object[]{userPrincipalName};

    //System.out.println("Sem v getRoleNamesForUser searchFilter:"+searchFilter);
    
    //NamingEnumeration answer = ldapContext.search(searchBase, searchFilter, searchArguments, searchCtls);
    //searchBase = "DC=pr,DC=lighting,DC=int";
    NamingEnumeration answer = ldapContext.search(searchBase, searchFilter, searchCtls);

    while(answer.hasMoreElements()) {
      SearchResult sr = (SearchResult)answer.next();

      if(log.isDebugEnabled()) {
         System.out.println("Retrieving group names for user [" + sr.getName() + "]"); 
         log.debug("Retrieving group names for user [" + sr.getName() + "]");
      }
      
      //izpis za test, izbriši naslednjo
      //System.out.println("Retrieving group names for user [" + sr.getName() + "]"); 

      Attributes attrs = sr.getAttributes();

      if(attrs != null) {
        NamingEnumeration ae = attrs.getAll();
        while(ae.hasMore()) {
          Attribute attr = (Attribute)ae.next();

          if(attr.getID().equals("memberOf")) {

            Collection<String> groupNames = LdapUtils.getAllAttributeValues(attr);

            if(log.isDebugEnabled()) {
              System.out.println("Groups found for user [" + username + "]: " + groupNames.size());
              log.debug("Groups found for user [" + username + "]: " + groupNames);
            }
            
            //izpis za test, pobriši naslednje 4 vrstice
//            System.out.println("Groups found for user [" + username + "]: " + groupNames.size());
//            for(String g:groupNames){
//                System.out.println("Group found for user [" + username + "]: " + g);
//            }

            Collection<String> rolesForGroups = getRoleNamesForGroups(groupNames);
            roleNames.addAll(rolesForGroups);
          }
        }
      }
    }
    return roleNames;
  }
    /**
     * Helper method to convert from AuthenticationToken to a username + suffix
     * if exists
     */
    private String getUserPrincipalName(AuthenticationToken token) {
        System.out.println("Sem v getUserPrincipalName");
        String userPrincipalName = String.valueOf(token.getPrincipal());
        if (principalSuffix != null) {
            userPrincipalName += principalSuffix;
        }
        System.out.println("Sem v getUserPrincipalName() metodi ki vrača string:"+userPrincipalName);
        return userPrincipalName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    

}
