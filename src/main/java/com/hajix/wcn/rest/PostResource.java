package com.hajix.wcn.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.eclipse.jetty.util.StringUtil;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hajix.wcn.model.Post;
import com.hajix.wcn.model.User;
import com.hajix.wcn.services.PostStorage;
import com.hajix.wcn.services.UserLookup;

@Path("/post")
@Singleton
public class PostResource {

    private static final Logger log = Logger.getLogger(PostResource.class);
    
    private final PostStorage postStorage;
    private UserLookup userLookup;

    @Inject
    public PostResource(PostStorage postStorage, UserLookup userLookup) {
        this.postStorage = postStorage;
        this.userLookup = userLookup;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Post> listPosts(@QueryParam("since") long since) {
        return postStorage.getPosts(since);
    }
    
    @Path("{userName}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Post registerPost(@PathParam("userName") String userName, String content) {
        User user = userLookup.lookupUser(userName);
        
        if (user == null) {
            throw new WebApplicationException(Response.status(Status.UNAUTHORIZED).entity("relogin").build());
        }
        
        if (StringUtil.isBlank(content)) {
            throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity("").build());
        }
        
        Post post = new Post(User.encodedUser(user), StringEscapeUtils.escapeHtml3(content), System.currentTimeMillis());
        
        log.info(String.format("Adding new post: %s", post));
        postStorage.addPost(post);
        
        return post;
    }

}
