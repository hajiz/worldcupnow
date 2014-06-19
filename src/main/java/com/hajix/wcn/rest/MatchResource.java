package com.hajix.wcn.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hajix.wcn.model.Match;
import com.hajix.wcn.model.MatchWithResult;
import com.hajix.wcn.services.MatchStorage;
import com.hajix.wcn.services.ResultLookup;

@Path("/match")
@Singleton
public class MatchResource {

    private final ResultLookup resultLookup;

    @Inject
    public MatchResource(ResultLookup resultLookup) {
        this.resultLookup = resultLookup;
        
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MatchWithResult> getAllMatches() {
        return Lists.transform(MatchStorage.getAllMatches(), new Function<Match, MatchWithResult>() {
            @Override
            public MatchWithResult apply(Match match) {
                return new MatchWithResult(match, resultLookup.getResult(match.getHome(), match.getAway()));
            }
        });
    }
}
