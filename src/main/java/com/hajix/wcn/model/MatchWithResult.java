package com.hajix.wcn.model;

public class MatchWithResult {

    private final String home;
    private final String homeFlag;
    private final String away;
    private final String awayFlag;
    private final long timeStamp;
    private final int homeScore;
    private final int awayScore;
    
    public MatchWithResult(Match m, MatchResult r) {
        if (m.getHome() != r.getHome() || m.getAway() != r.getAway()) {
            throw new IllegalStateException("Didn't match: " + m + " and " + r);
        }
        
        home = m.getHome().getPrintableName();
        homeFlag = m.getHome().getFlagName();
        away = m.getAway().getPrintableName();
        awayFlag = m.getAway().getFlagName();
        timeStamp = m.getTimestamp();
        homeScore = r.getHomeScore();
        awayScore = r.getAwayScore();
    }

    public String getHome() {
        return home;
    }

    public String getHomeFlag() {
        return homeFlag;
    }

    public String getAway() {
        return away;
    }

    public String getAwayFlag() {
        return awayFlag;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }
}
