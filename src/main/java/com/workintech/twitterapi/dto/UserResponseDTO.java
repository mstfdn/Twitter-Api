package com.workintech.twitterapi.dto;

import java.util.List;

public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private List<TweetSummaryDTO> tweets;

    public UserResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<TweetSummaryDTO> getTweets() {
        return tweets;
    }

    public void setTweets(List<TweetSummaryDTO> tweets) {
        this.tweets = tweets;
    }

    // İç sınıf olarak tweet özeti
    public static class TweetSummaryDTO {
        private Long id;
        private String content;

        public TweetSummaryDTO() {
        }

        public TweetSummaryDTO(Long id, String content) {
            this.id = id;
            this.content = content;
        }

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
    }
} 