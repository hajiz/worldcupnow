package com.hajix.wcn.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;

import com.google.inject.Inject;
import com.hajix.wcn.model.User;
import com.hajix.wcn.services.UserLookup;

@Path("user")
public class UserResource {

    private final UserLookup userLookup;

    @Inject
    public UserResource(UserLookup userLookup) {
        this.userLookup = userLookup;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User createUser(User user) {
        validateUser(user);
        return userLookup.registerUser(user.getUserName(), user.getFirstName(), user.getLastName());
    }
    
    private void validateUser(User user) {
        String errorMessage = null;
        if (user == null) {
            errorMessage = "Please enter login information";
        } else if (StringUtils.isBlank(user.getFirstName())) {
            errorMessage = "Please enter valid first name";
        } else if (StringUtils.isBlank(user.getLastName())) {
            errorMessage = "Please enter valid last name";
        } else if (StringUtils.isBlank(user.getUserName())) {
            errorMessage = "Please enter valid user name";
        }
        if (errorMessage != null) {
            throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity(errorMessage).build());
        }
    }

}
