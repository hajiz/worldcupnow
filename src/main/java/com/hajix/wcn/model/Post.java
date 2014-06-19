package com.hajix.wcn.model;


public class Post {

    private final User author;
    private final String content;
    private final long timeMs;
    
    public Post(User author, String content, long timeMs) {
        this.author = author;
        this.content = content;
        this.timeMs = timeMs;
    }

    public User getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public long getTimeMs() {
        return timeMs;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((author == null) ? 0 : author.hashCode());
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        result = prime * result + (int) (timeMs ^ (timeMs >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Post other = (Post) obj;
        if (author == null) {
            if (other.author != null) {
                return false;
            }
        } else if (!author.equals(other.author)) {
            return false;
        }
        if (content == null) {
            if (other.content != null) {
                return false;
            }
        } else if (!content.equals(other.content)) {
            return false;
        }
        if (timeMs != other.timeMs) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Post [author=" + author + ", content=" + content + ", timeMs=" + timeMs + "]";
    }
    
}
