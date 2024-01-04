/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.portal.beans;

import org.omnifaces.cdi.push.SocketEvent;
import org.omnifaces.cdi.push.SocketEvent.Switched;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.faces.event.WebsocketEvent.Closed;
import javax.faces.event.WebsocketEvent.Opened;
import javax.websocket.CloseReason.CloseCode;

/**
 *
 * @author dematjasic
 */
@ApplicationScoped
public class SocketObserver {

    public void onOpen(@Observes @Opened SocketEvent event) {
        String channel = event.getChannel(); // Returns <o:socket channel>.
        Long userId = event.getUser(); // Returns <o:socket user>, if any.
        // Do your thing with it. E.g. collecting them in a concurrent/synchronized collection.
        // Do note that a single person can open multiple sockets on same channel/user.
        System.out.println("SocketObserver onOpen | channel:"+channel+" | userId:"+userId);
    }

    public void onSwitch(@Observes @Switched SocketEvent event) {
        String channel = event.getChannel(); // Returns <o:socket channel>.
        Long currentUserId = event.getUser(); // Returns current <o:socket user>, if any.
        Long previousUserId = event.getPreviousUser(); // Returns previous <o:socket user>, if any.
        // Do your thing with it. E.g. updating in a concurrent/synchronized collection.
        System.out.println("SocketObserver onSwitch | currentUserId:"+currentUserId+" | previousUserId:"+previousUserId);
    }

    public void onClose(@Observes @Closed SocketEvent event) {
        String channel = event.getChannel(); // Returns <o:socket channel>.
        Long userId = event.getUser(); // Returns <o:socket user>, if any.
        CloseCode code = event.getCloseCode(); // Returns close reason code.
        // Do your thing with it. E.g. removing them from collection.
        System.out.println("SocketObserver onClose | channel:"+channel+" | userId:"+userId+" | closeCode:"+code);
    }

}
