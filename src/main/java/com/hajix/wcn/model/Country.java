package com.hajix.wcn.model;

import java.util.List;

import com.google.common.collect.Lists;

public enum Country {
    Algeria("dz"),
    Argentina("ar"),
    Australia("au"),
    Belgium("be"),
    Bosnia_and_Herzegovina("ba"),
    Brazil("br"),
    Cameroon("cm"),
    Chile("cl"),
    Colombia("co"),
    Costa_Rica("cr"),
    Cote_d_Ivoire("ci"),
    Croatia("hr"),
    Ecuador("ec"),
    England("gb"),
    France("fr"),
    Germany("de"),
    Ghana("gh"),
    Greece("gr"),
    Honduras("hn"),
    Iran("ir"),
    Italy("it"),
    Japan("jp"),
    Korea_Republic("kr"),
    Mexico("mx"),
    Netherlands("nl"),
    Nigeria("ng"),
    Portugal("pt"),
    Russia("ru"),
    Spain("es"),
    Switzerland("ch"),
    Uruguay("uy"),
    USA("us");
    
    private String flagName;
    private Country(String flagName) {
        this.flagName = flagName;
    }
    
    public String getPrintableName() {
        if (this == Cote_d_Ivoire) {
            return "Ivory Coast";
        }
        return name().replace('_', ' ');
    }
    
    public String getFlagName() {
        return flagName;
    }

    public static final List<Country> allCountries;
    static {
        allCountries = Lists.newArrayList(Country.values());
    }
    
    public static Country lookup(String name) {
        if (name.equals("Ivory Coast")) {
            return Country.Cote_d_Ivoire;
        } else if (name.equals("United States")) {
            return Country.USA;
        } else if (name.equals("South Korea")) {
            return Country.Korea_Republic;
        }
        
        name = name.replace(' ', '_');
        return Country.valueOf(name);
    }
}