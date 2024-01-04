/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.primefaces.testingcomponents;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 *
 * @author dematjasic
 */
@Named("primefacesTest")
@ViewScoped
public class PrimefacesTest implements Serializable{
    
    private int someInt;
    private String someString;
    
    @PostConstruct
    public void init() {
        someInt = 1;
        someString = "Example String";
    }

    public int getSomeInt() {
        return someInt;
    }

    public void setSomeInt(int someInt) {
        this.someInt = someInt;
    }

    public String getSomeString() {
        return someString;
    }

    public void setSomeString(String someString) {
        this.someString = someString;
    }
    
    
}
