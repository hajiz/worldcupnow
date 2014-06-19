package com.hajix.wcn.services;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

public class HealthChecker {

    private static final Logger log = Logger.getLogger(HealthChecker.class);
    
    PostStorage postStorage;
    UserLookup userLookup;
    ResultLookup resultLookup;
    
    @Inject
    public HealthChecker(PostStorage postStorage, UserLookup userLookup, ResultLookup resultLookup) {
        this.postStorage = postStorage;
        this.userLookup = userLookup;
        this.resultLookup = resultLookup;
    }

    public void start() {
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        sleep(TimeUnit.MINUTES.toMillis(60));
                    } catch (InterruptedException e) {
                    }
                    checkHealth();
                }
            };
        }.start();
    }
    
    private void checkHealth() {
        int postStorageSize = postStorage.storageSize(),
                userCount = userLookup.numberOfUsers(),
                resultCount = resultLookup.numberOfRegisteredResults();
        
        log.info(String.format("Posts: %d, users: %d, results: %d", postStorageSize, userCount, resultCount));
    }
}
