package com.hajix.wcn.model;

public class MatchResult {

    private final Country home;
    private final Country away;
    private final int homeScore;
    private final int awayScore;
    
    public MatchResult(Country home, Country away, int homeScore, int awayScore) {
        this.home = home;
        this.away = away;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }
    
    public static MatchResult noResult(Country home, Country away) {
        return new MatchResult(home, away, -1, -1);
    }

    public Country getHome() {
        return home;
    }

    public Country getAway() {
        return away;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    @Override
    public String toString() {
        return "MatchResult [home=" + home + ", away=" + away + ", homeScore=" + homeScore + ", awayScore=" + awayScore + "]";
    }
    
}
