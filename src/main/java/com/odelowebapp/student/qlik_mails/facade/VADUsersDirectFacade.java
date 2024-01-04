/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.odelowebapp.student.qlik_mails.facade;

import com.odelowebapp.student.qlik_mails.entity.VADusersdirect;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author itstudent
 */
@Stateless
public class VADUsersDirectFacade extends AbstractFacade<VADusersdirect> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public VADUsersDirectFacade() {
        super(VADusersdirect.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
