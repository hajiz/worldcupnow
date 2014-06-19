package com.hajix.wcn.services;

import java.util.Map;

import org.apache.log4j.Logger;

import com.google.common.collect.Maps;
import com.google.inject.Singleton;
import com.hajix.wcn.model.Country;
import com.hajix.wcn.model.MatchResult;

@Singleton
public class ResultLookupImpl implements ResultLookup {

    private static final Logger log = Logger.getLogger(ResultLookupImpl.class);
    private Map<Tuple, MatchResult> results = Maps.newHashMap();

    @Override
    public void registerResult(Country c1, Country c2, int score1, int score2) {
        Tuple key = new Tuple(c1, c2);
        if (!results.containsKey(key) || results.get(key).getHomeScore() != score1 || results.get(key).getAwayScore() != score2) {
            log.info(String.format("Registering %s %d - %d %s", c1.name(), score1, score2, c2.name()));
            results.put(key, new MatchResult(c1, c2, score1, score2));
        }
    }

    @Override
    public MatchResult getResult(Country home, Country away) {
        Tuple key = new Tuple(home, away);
        if (results.containsKey(key)) {
            return results.get(key);
        }
        
        return MatchResult.noResult(home, away);
    }

    @Override
    public int numberOfRegisteredResults() {
        return results.size();
    }

}

class Tuple {
    Country c1, c2;

    public Tuple(Country c1, Country c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((c1 == null) ? 0 : c1.hashCode());
        result = prime * result + ((c2 == null) ? 0 : c2.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Tuple other = (Tuple) obj;
        if (c1 != other.c1) {
            return false;
        }
        if (c2 != other.c2) {
            return false;
        }
        return true;
    }
}