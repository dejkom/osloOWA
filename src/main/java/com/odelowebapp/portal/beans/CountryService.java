/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.portal.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author dematjasic
 */
@Named
@ApplicationScoped
public class CountryService {

    private List<Country> countries;
    private Map<Integer, Country> countriesAsMap;
    private List<Country> locales;
    private Map<Integer, Country> localesAsMap;

    @PostConstruct
    public void init() {
        countries = new ArrayList<>();
        locales = new ArrayList<>();

        String[] isoCodes = Locale.getISOCountries();

//        for (int i = 0; i < isoCodes.length; i++) {
//            Locale locale = new Locale("", isoCodes[i]);
//            if(locale.getCountry().equals("SI") || locale.getCountry().equals("DE") || locale.getCountry().equals("EN") || locale.getCountry().equals("TR")){
//                System.out.println(locale.getCountry());
//                countries.add(new Country(i, locale));
//            }
//
//            //countries.add(new Country(0, new Locale("sl", "SI")));
//        }
        
        countries.add(new Country(0, new Locale("sl", "si")));
        countries.add(new Country(1, new Locale("en", "US")));
        countries.add(new Country(2, new Locale("de", "DE")));
        countries.add(new Country(3, new Locale("tr", "TR")));
        

        Collections.sort(countries, (Country c1, Country c2) -> c1.getName().compareTo(c2.getName()));

        int i = 0;
        locales.add(new Country(i++, Locale.US));
//        locales.add(new Country(i++, Locale.FRANCE));
//        locales.add(new Country(i++, Locale.GERMANY));
//        locales.add(new Country(i++, Locale.ITALY));
//        locales.add(new Country(i++, Locale.KOREA));
//        locales.add(new Country(i++, new Locale("es", "ES")));
//        locales.add(new Country(i++, new Locale("ca", "ES")));
//        locales.add(new Country(i++, new Locale("nl", "NL")));
//        locales.add(new Country(i++, new Locale("pt", "BR")));
//        locales.add(new Country(i++, new Locale("pt", "PT")));
//        locales.add(new Country(i++, new Locale("ar", "SA"), true));
//        locales.add(new Country(i++, new Locale("cs", "CZ")));
//        locales.add(new Country(i++, new Locale("el", "GR")));
//        locales.add(new Country(i++, new Locale("fa", "IR"), true));
//        locales.add(new Country(i++, new Locale("hi", "IN")));
//        locales.add(new Country(i++, new Locale("in", "ID")));
//        locales.add(new Country(i++, new Locale("hr", "HR")));
//        locales.add(new Country(i++, new Locale("hu", "HU")));
//        locales.add(new Country(i++, new Locale("iw", "IL"), true));
//        locales.add(new Country(i++, new Locale("ka", "GE")));
//        locales.add(new Country(i++, new Locale("lt", "LT")));
//        locales.add(new Country(i++, new Locale("lv", "LV")));
//        locales.add(new Country(i++, new Locale("no", "NO")));
//        locales.add(new Country(i++, new Locale("pl", "PL")));
//        locales.add(new Country(i++, new Locale("ro", "RO")));
//        locales.add(new Country(i++, new Locale("ru", "RU")));
//        locales.add(new Country(i++, new Locale("sk", "SK")));
        locales.add(new Country(i++, new Locale("sl", "SI")));
//        locales.add(new Country(i++, new Locale("sr", "RS")));
//        locales.add(new Country(i++, new Locale("sv", "SE")));
//        locales.add(new Country(i++, new Locale("tr", "TR")));
//        locales.add(new Country(i++, new Locale("uk", "UA")));
//        locales.add(new Country(i++, new Locale("vi", "VN")));
//        locales.add(new Country(i++, Locale.SIMPLIFIED_CHINESE));
//        locales.add(new Country(i++, Locale.TRADITIONAL_CHINESE));
    }

    public List<Country> getCountries() {
        return new ArrayList<>(countries);
    }

    public Map<Integer, Country> getCountriesAsMap() {
        if (countriesAsMap == null) {
            countriesAsMap = getCountries().stream().collect(Collectors.toMap(Country::getId, country -> country));
        }
        return countriesAsMap;
    }

    public List<Country> getLocales() {
        return new ArrayList<>(locales);
    }

    public Map<Integer, Country> getLocalesAsMap() {
        if (localesAsMap == null) {
            localesAsMap = getLocales().stream().collect(Collectors.toMap(Country::getId, country -> country));
        }
        return localesAsMap;
    }
}
