package com.hajix.wcn.services;

import java.util.List;

import com.hajix.wcn.model.Post;

public interface PostStorage {

    void addPost(Post post);
    
    List<Post> getPosts(long since);
    
    /* Stats */
    int storageSize();
}
