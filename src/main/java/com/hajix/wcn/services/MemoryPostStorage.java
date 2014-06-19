package com.hajix.wcn.services;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Singleton;
import com.hajix.wcn.model.Post;

@Singleton
public class MemoryPostStorage implements PostStorage {
    
    private final TreeSet<Post> posts;

    public MemoryPostStorage() {
        posts = Sets.newTreeSet(new Comparator<Post>() {
            @Override
            public int compare(Post a, Post b) {
                return Long.compare(a.getTimeMs(), b.getTimeMs());
            }
        });
    }
    
    @Override
    public void addPost(Post post) {
        posts.add(post);
    }

    @Override
    public List<Post> getPosts(long since) {
        return Lists.newArrayList(posts.tailSet(query(since)));
    }
    
    private Post query(long since) {
        return new Post(null, null, since);
    }

    @Override
    public int storageSize() {
        return posts.size();
    }

}
