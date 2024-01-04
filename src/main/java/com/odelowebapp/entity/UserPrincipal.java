/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.entity;

/**
 *
 * @author https://github.com/opticyclic/shiro-spring-examples/blob/master/ms-active-directory-custom/src/main/java/com/github/opticyclic/shiro/msad/UserPrincipal.java
 */
import java.util.Set;

/**
 * Simple class for holding the role names on a principal
 */
public class UserPrincipal {

  private String principalName;
  private Set<String> roleNames;

  public String getPrincipalName() {
    return principalName;
  }

  public void setPrincipalName(String principalName) {
    this.principalName = principalName;
  }

  public Set<String> getRoleNames() {
    return roleNames;
  }

  public void setRoleNames(Set<String> roleNames) {
    this.roleNames = roleNames;
  }
  
  
}
