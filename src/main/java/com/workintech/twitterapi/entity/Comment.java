package com.workintech.twitterapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime createdAt;

    //validasyonlar
    @NotNull
    @Size(max = 280)
    private String content;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tweet_id")
    @JsonBackReference(value = "tweet-comments")
    private Tweet tweet;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "user-comments")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTweetId() {
        return tweet != null ? tweet.getId() : null;
    }

    public void setTweetId(Long tweetId) {

    }

    public Long getUserId() {
        return user != null ? user.getId() : null;
    }

    public void setUserId(Long userId) {

    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
