/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.portal.beans;

/**
 *
 * @author dematjasic
 */
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

public class FakeEndpoint extends Endpoint {
    
 @Override
 public void onOpen(Session session, EndpointConfig config) {
  // https://java.net/jira/browse/WEBSOCKET_SPEC-240
 }   
}