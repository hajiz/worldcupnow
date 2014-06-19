package com.hajix.wcn.services;

import com.hajix.wcn.model.Country;
import com.hajix.wcn.model.MatchResult;

public interface ResultLookup {

    void registerResult(Country c1, Country c2, int score1, int score2);
    
    MatchResult getResult(Country home, Country away);
    
    /* Stats */
    int numberOfRegisteredResults();
}
