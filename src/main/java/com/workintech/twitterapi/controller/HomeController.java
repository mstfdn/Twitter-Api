package com.workintech.twitterapi.controller;

import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {

    private final TweetService tweetService;

    @Autowired
    public HomeController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping({"/", "/home"})
    public ResponseEntity<List<Tweet>> getHomePage() {
        // Ana sayfada tüm tweetleri göster (gerçek uygulamada bu daha karmaşık olabilir)
        List<Tweet> tweets = tweetService.getAllTweets();
        return ResponseEntity.ok(tweets);
    }
} 