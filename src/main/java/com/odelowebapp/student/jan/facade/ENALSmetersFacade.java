/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.odelowebapp.student.jan.facade;

import com.odelowebapp.student.jan.entity.ENALSmeters;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author itstudent
 */
@Stateless
public class ENALSmetersFacade extends AbstractFacade<ENALSmeters> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ENALSmetersFacade() {
        super(ENALSmeters.class);
    }
    
}
