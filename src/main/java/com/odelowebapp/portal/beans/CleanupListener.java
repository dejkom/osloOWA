/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.portal.beans;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author dematjasic
 */
public class CleanupListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                System.out.println("deregistering JDBC driver: "+driver);
                //LOG.log(Level.INFO, String.format("deregistering jdbc driver: %s", driver));
            } catch (SQLException e) {
                System.out.println("deregistering JDBC driver: "+driver+" ERROR:"+e);
                //LOG.log(Level.SEVERE, String.format("Error deregistering driver %s", driver), e);
            }
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {

    }
}
