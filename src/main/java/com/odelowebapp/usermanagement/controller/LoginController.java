/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.usermanagement.controller;

import com.odelowebapp.HR.beans.JantarPINConvert;
import com.odelowebapp.HR.entity.VADCODEKSUsers;
import com.odelowebapp.HR.entity.VCodeksUsersAktualniZaposleni;
import com.odelowebapp.HR.facade.HRvcodeksusersFacade;
import com.odelowebapp.HR.facade.VADCODEKSUsersFacade;
import com.odelowebapp.entity.CustomActiveDirectoryRealm;
import com.odelowebapp.portal.beans.Country;
import com.odelowebapp.portal.beans.CountryService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.primefaces.PrimeFaces;

@Named
@SessionScoped
public class LoginController implements Serializable {

    @EJB
    private VADCODEKSUsersFacade usersFacade;
    @EJB
    private HRvcodeksusersFacade usersEJB;

    private static final long serialVersionUID = 1L;

    public static final String HOME_URL = "/osloWebApp/faces/index.xhtml";

    @Inject
    private CountryService service;

    private List<Country> countries;
    private Country country;
    private Locale currentL;
    private String maliCountry;

    @PostConstruct
    public void init() {
        countries = service.getCountries();
        System.out.println("Countries:" + countries.size());
        //System.out.println("Selected country:" + country.getName());
        currentL = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
        System.out.println("Current locale:" + currentL.getCountry());

        maliCountry = currentL.getCountry().toLowerCase();
    }

    //@Email(message = "Must be a well-formed email address")
    @NotNull(message = "Cannot be empty")
    private String username;

    @NotNull(message = "Cannot be empty")
    private String password;

    @NotNull(message = "Please select an option")
    private String userType;

    public void showRfidLoginDialog() {
        System.out.println("Sem v showRfidLoginDialog");
    }

    private String kljucekStringBelezenje;
    private String izbrancitalec;

    public void readerChange() {
        System.out.println("Sem v readerChange");
        System.out.println("Izbran citalec:" + izbrancitalec);
        if (izbrancitalec != null) {
            System.out.println("izbral si čitalec");
            kljucekStringBelezenje = "";
        } else {
            System.out.println("Čitalec ni izbran");
        }
        //RequestContext.getCurrentInstance().execute("document.getElementById('form2:kin').focus(); $('#kin').focus();");
        //PrimeFaces.current().executeScript("document.getElementById('form2:kin2').focus(); $('#kin2').focus();");
        PrimeFaces.current().focus("form2:kin");

    }

    JantarPINConvert jpc = new JantarPINConvert();

    public void prijaviSKljuckom() {
        System.out.println("Sem v prijaviSKljuckom, kljucek:" + kljucekStringBelezenje);
        VCodeksUsersAktualniZaposleni prebran;
        VADCODEKSUsers user;
        //tukaj prijavi
        if (izbrancitalec.equals("emcard")) {
            String k = jpc.pretvoriKljucekVJantarOblikoPublic(kljucekStringBelezenje);
            //prebran = codeksFacade.getUsersByCard(k);
            kljucekStringBelezenje = k;
            prebran = usersEJB.findUserByCard(k);
        } else {
            prebran = usersEJB.findUserByCard(kljucekStringBelezenje);
        }

        user = usersFacade.findUserByCodeksId(prebran.getId());

        System.out.println("Prebran user:" + prebran.getId());
        System.out.println("Sedaj se prijavljam v sessionID:" + SecurityUtils.getSubject().getSession().getId() + " s podatki:" + prebran.getId() + "-->" + kljucekStringBelezenje + "-->" + username);
        System.out.println("Prijavljam se na NOVO z username:" + prebran.getUsername() + " in začasnim geslom blabla");
        SecurityUtils.getSubject().login(new UsernamePasswordToken(prebran.getId() + "", kljucekStringBelezenje));
        SecurityUtils.getSubject().getSession().setAttribute("username", prebran.getExternalId());
        SecurityUtils.getSubject().getSession().setAttribute("externalid", prebran.getExternalId());
        SecurityUtils.getSubject().getSession().setAttribute("firstname", prebran.getFirstname());
        SecurityUtils.getSubject().getSession().setAttribute("lastname", prebran.getLastname());
        SecurityUtils.getSubject().getSession().setAttribute("codeksid", prebran.getId() + "");

        SecurityUtils.getSubject().getSession().setAttribute("mail", user.getMail());
        SecurityUtils.getSubject().getSession().setAttribute("telephone", user.getTelephoneNumber());
        SecurityUtils.getSubject().getSession().setAttribute("mobilephone", user.getMobile());
        SecurityUtils.getSubject().getSession().setAttribute("jobtitle", user.getTitle());

        SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(Faces.getRequest());
        Faces.redirect(savedRequest != null ? savedRequest.getRequestUrl() : HOME_URL);

//        try {
//            
//            System.out.println("Prijavljam se na NOVO z username:"+prebran.getUsername()+" in začasnim geslom blabla");
//            SecurityUtils.getSubject().login(new UsernamePasswordToken(prebran.getUsername(), "blabla"));
//
//            System.out.println("Sedaj se prijavljam v sessionID:" + SecurityUtils.getSubject().getSession().getId() + " z cardom:" + prebran.getCard());
//
//            System.out.println("User externalid:" + prebran.getExternalId());
//
//            SecurityUtils.getSubject().getSession().setAttribute("username", username);
//            SecurityUtils.getSubject().getSession().setAttribute("externalid", prebran.getExternalId());
//            SecurityUtils.getSubject().getSession().setAttribute("firstname", prebran.getFirstname());
//            SecurityUtils.getSubject().getSession().setAttribute("lastname", prebran.getLastname());
//            SecurityUtils.getSubject().getSession().setAttribute("codeksid", prebran.getId() + "");
//            SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(Faces.getRequest());
//            Faces.redirect(savedRequest != null ? savedRequest.getRequestUrl() : HOME_URL);
//
//        } catch (UnknownAccountException uae) {
//            Messages.addGlobalError("Unknown user, please try again");
//        } catch (IncorrectCredentialsException ice) {
//            Messages.addGlobalError("Incorrect username or password");
//        } catch (LockedAccountException lae) {
//            Messages.addGlobalError("Account Locked");
//        } catch (ExcessiveAttemptsException eae) {
//            Messages.addGlobalError("Too many attemps");
//        } catch (AuthenticationException ae) {
//            Messages.addGlobalError("User not authorized");
//        }
        //počisti prej vpisano geslo
        kljucekStringBelezenje = "";
        izbrancitalec = null;
    }

    //private boolean remember;
    public String getUsername() {
        return username;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Locale getCurrentL() {
        return currentL;
    }

    public void setCurrentL(Locale currentL) {
        this.currentL = currentL;
    }

    public String getMaliCountry() {
        return maliCountry;
    }

    public void setMaliCountry(String maliCountry) {
        this.maliCountry = maliCountry;
    }

    public String getKljucekStringBelezenje() {
        return kljucekStringBelezenje;
    }

    public void setKljucekStringBelezenje(String kljucekStringBelezenje) {
        this.kljucekStringBelezenje = kljucekStringBelezenje;
    }

    public String getIzbrancitalec() {
        return izbrancitalec;
    }

    public void setIzbrancitalec(String izbrancitalec) {
        this.izbrancitalec = izbrancitalec;
    }

//	public boolean isRemember() {
//		return remember;
//	}
//
//	public void setRemember(boolean remember) {
//		this.remember = remember;
//	}
    public void submitAD() {
        System.out.println("sem v SUBMIT");

        try {

            SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password));
            System.out.println("Sedaj se prijavljam v sessionID:" + SecurityUtils.getSubject().getSession().getId() + " z userjem:" + username);

            RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
            Realm realm = securityManager.getRealms().iterator().next();

            if (realm instanceof CustomActiveDirectoryRealm) {
                CustomActiveDirectoryRealm activeDirectoryRealm = (CustomActiveDirectoryRealm) realm;
                String realmName = activeDirectoryRealm.getName();
                System.out.println("Realm used: " + realmName);
            } else {
                System.out.println("Unknown realm");
            }

            VADCODEKSUsers user = usersFacade.findUserByUsername(username);
            System.out.println("User externalid:" + user.getExternalId());

            SecurityUtils.getSubject().getSession().setAttribute("username", username);
            SecurityUtils.getSubject().getSession().setAttribute("externalid", user.getExternalId());
            SecurityUtils.getSubject().getSession().setAttribute("firstname", user.getFirstname());
            SecurityUtils.getSubject().getSession().setAttribute("lastname", user.getLastname());
            SecurityUtils.getSubject().getSession().setAttribute("codeksid", user.getId() + "");

            SecurityUtils.getSubject().getSession().setAttribute("mail", user.getMail());
            SecurityUtils.getSubject().getSession().setAttribute("telephone", user.getTelephoneNumber());
            SecurityUtils.getSubject().getSession().setAttribute("mobilephone", user.getMobile());
            SecurityUtils.getSubject().getSession().setAttribute("jobtitle", user.getTitle());

            SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(Faces.getRequest());
            Faces.redirect(savedRequest != null ? savedRequest.getRequestUrl() : HOME_URL);

        } catch (UnknownAccountException uae) {
            Messages.addGlobalError("Unknown user, please try again");
        } catch (IncorrectCredentialsException ice) {
            Messages.addGlobalError("Incorrect username or password");
        } catch (LockedAccountException lae) {
            Messages.addGlobalError("Account Locked");
        } catch (ExcessiveAttemptsException eae) {
            Messages.addGlobalError("Too many attemps");
        } catch (AuthenticationException ae) {
            Messages.addGlobalError("User not authorized");
        }

    }

    public void logOut() throws IOException {
        System.out.println("Sedaj bom odjavil sessionID:" + SecurityUtils.getSubject().getSession().getId());
        System.out.println("Ta sessionID je pripadal osebi:" + SecurityUtils.getSubject().getSession().getAttribute("username").toString());
        SecurityUtils.getSubject().logout();
        Faces.invalidateSession();
        //Faces.redirect("/");
        Faces.redirect("/osloWebApp/faces/index.xhtml");
    }

    //value change event listener
    public void localeChanged() {
        try {
            System.out.println("Sem v localeChanged:" + country.getLocale().getLanguage() + " locale:" + country.getLocale().getCountry().toLowerCase() + " dispc:" + country.getLocale().getDisplayCountry() + " loccountry:" + country.getLocale().getCountry());
            FacesContext.getCurrentInstance().getViewRoot().setLocale(country.getLocale());

            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

            currentL = country.getLocale();
            maliCountry = country.getLocale().getCountry().toLowerCase();

            SecurityUtils.getSubject().getSession().setAttribute("country", country);

            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
