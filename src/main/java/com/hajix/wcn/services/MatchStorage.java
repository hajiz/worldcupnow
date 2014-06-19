package com.hajix.wcn.services;

import java.util.List;

import com.hajix.wcn.model.Country;
import com.hajix.wcn.model.Match;

public class MatchStorage {
    public static List<Country> getAllCountries() {
        return Country.allCountries;
    }
    
    public static List<Match> getAllMatches() {
        return Match.allMatches;
    }
}
