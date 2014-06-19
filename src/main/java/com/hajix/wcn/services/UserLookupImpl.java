package com.hajix.wcn.services;

import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.common.collect.Maps;
import com.google.inject.Singleton;
import com.hajix.wcn.model.User;

@Singleton
public class UserLookupImpl implements UserLookup {

    Map<String, User> aliasToUser;
    Map<Long, User> userIdToUser;
    Object lock = new Object();
    long userIdGeneration = 0L;
    
    public UserLookupImpl() {
        aliasToUser = Maps.newConcurrentMap();
        userIdToUser = Maps.newConcurrentMap();
    }

    @Override
    public User registerUser(String userName, String firstName, String lastName) {
        if (!isUserNameUnique(userName)) {
            throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity("User name " + userName + " is not unique").build());
        }
        
        long nextUserId = getNextUserId();
        
        User user = new User(Long.toString(nextUserId), userName, firstName, lastName);
        
        aliasToUser.put(userName, user);
        userIdToUser.put(nextUserId, user);
        
        return user;
    }

    @Override
    public User lookupUser(String userName) {
        return aliasToUser.get(userName);
    }

    @Override
    public User getUser(long userId) {
        return userIdToUser.get(userId);
    }
    
    private long getNextUserId() {
        synchronized (lock) {
            userIdGeneration ++;
            return userIdGeneration;
        }
    }
    
    private boolean isUserNameUnique(String userName) {
        return !aliasToUser.containsKey(userName);
    }

    @Override
    public int numberOfUsers() {
        return userIdToUser.size();
    }

}
