package com.hajix.wcn.services;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.hajix.wcn.model.Country;

public class ResultFetcher {

    private static final Logger log = Logger.getLogger(ResultFetcher.class);
    private final String resultsWikiPage = "http://en.wikipedia.org/wiki/List_of_2014_FIFA_World_Cup_matches";
    private ResultLookup resultLookup;
    
    @Inject
    public ResultFetcher(ResultLookup resultLookup) {
        this.resultLookup = resultLookup;
    }
    
    public void fetchUrl() throws IOException {
        Document doc = Jsoup.connect(resultsWikiPage).get();
        Elements rows = doc.select(".wikitable tr");
        
        for (Element row : rows) {
            Elements links = row.select("a");
            List<String> countries = Lists.<String>newArrayList();
            String result = "";
            boolean betweenCountries = false;
            for (Element link : links) {
                String href = link.attr("href");
                boolean isCountry = isCountry(href);
                if (isCountry) {
                    countries.add(link.html());
                    betweenCountries = !betweenCountries;
                } else {
                    if (betweenCountries) {
                        result = link.html();
                    }
                }
            }
            if (countries.size() == 2) {
                register(countries, result);
            } else if (countries.size() > 0) {
                log.warn("Wrong size of list: " + countries);
            }
        }
    }
    
    private void register(List<String> countries, String result) {
        Country home = Country.lookup(countries.get(0)),
                away = Country.lookup(countries.get(1));
        
        if (result.matches("\\d+–\\d+")) {
            int indexOf = result.indexOf('–');
            int homeScore = Integer.parseInt(result.substring(0, indexOf)),
                    awayScore = Integer.parseInt(result.substring(indexOf+1));
            
            resultLookup.registerResult(home, away, homeScore, awayScore);
        }
    }
    
    private boolean isCountry(String href) {
        return href.endsWith("national_football_team") ||
                href.endsWith("national_soccer_team") ||
                href.endsWith("association_football_team");
    }
    
    public void start() {
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        log.info("Starting a fetch");
                        fetchUrl();
                        log.info("Fetch complete! Sleeping..");
                    } catch (IOException e1) {
                        log.error("Failed while fetching url", e1);
                    }
                    try {
                        sleep(20000);
                    } catch (InterruptedException e) {
                    }
                }
            };
        }.start();
    }
}
