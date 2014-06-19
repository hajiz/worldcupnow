package com.hajix.wcn.model;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.google.common.collect.Lists;
import com.hajix.wcn.json.MatchSerializer;

@JsonSerialize(using = MatchSerializer.class)
public enum Match {
    H1(Country.Belgium, Country.Algeria, 17, 5, 2014, 9),
    A3(Country.Brazil, Country.Mexico, 17, 5, 2014, 12),
    H2(Country.Russia, Country.Korea_Republic, 17, 5, 2014, 15),
    
    B3(Country.Australia, Country.Netherlands, 18, 5, 2014, 9),
    B4(Country.Spain, Country.Chile, 18, 5, 2014, 12),
    A4(Country.Cameroon, Country.Croatia, 18, 5, 2014, 15),
    
    C3(Country.Colombia, Country.Cote_d_Ivoire, 19, 5, 2014, 9),
    D3(Country.Uruguay, Country.England, 19, 5, 2014, 12),
    C4(Country.Japan, Country.Greece, 19, 5, 2014, 15),
    
    D4(Country.Italy, Country.Costa_Rica, 20, 5, 2014, 9),
    E3(Country.Switzerland, Country.France, 20, 5, 2014, 12),
    E4(Country.Honduras, Country.Ecuador, 20, 5, 2014, 15),
    
    F3(Country.Argentina, Country.Iran, 21, 5, 2014, 9),
    G3(Country.Germany, Country.Ghana, 21, 5, 2014, 12),
    F4(Country.Nigeria, Country.Bosnia_and_Herzegovina, 21, 5, 2014, 15);
    
    public static final List<Match> allMatches;
    static {
        allMatches = Lists.newArrayList(Match.values());
    }
    
    private Country home;
    private Country away;
    private long timestamp;

    private Match(Country home, Country away, int day, int month, int year, int hour) {
        this.home = home;
        this.away = away;
        
        Calendar instance = Calendar.getInstance(TimeZone.getTimeZone("America/Vancouver"));
        instance.set(year, month, day, hour, 0);
        this.timestamp = instance.getTimeInMillis();
    }

    public Country getHome() {
        return home;
    }

    public Country getAway() {
        return away;
    }

    public long getTimestamp() {
        return timestamp;
    }
}