package com.hajix.wcn.services;

import com.hajix.wcn.model.User;

public interface UserLookup {

    User registerUser(String userName, String firstName, String lastName);
    
    User lookupUser(String userName);
    
    User getUser(long userId);
    
    /* Stats */
    int numberOfUsers();
    
}
