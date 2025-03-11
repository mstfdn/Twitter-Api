package com.workintech.twitterapi.controller;

import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.service.TweetService;
import com.workintech.twitterapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tweet")
public class TweetController {

    @Autowired
    private TweetService tweetService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Tweet> createTweet(@RequestBody Tweet tweet, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        tweet.setUser(user);
        tweet.setCreatedAt(LocalDateTime.now());
        Tweet savedTweet = tweetService.createTweet(tweet);
        return ResponseEntity.ok(savedTweet);
    }

    @GetMapping("/findByUserId")
    public ResponseEntity<List<Tweet>> getTweetsByUserId(@RequestParam Long userId) {
        return ResponseEntity.ok(tweetService.getTweetsByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tweet> getTweetById(@PathVariable Long id) {
        return ResponseEntity.ok(tweetService.getTweetById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tweet> updateTweet(@PathVariable Long id, @RequestBody Tweet tweet) {
        return ResponseEntity.ok(tweetService.updateTweet(id, tweet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable Long id) {
        tweetService.deleteTweet(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Tweet>> getAllTweets() {
        return ResponseEntity.ok(tweetService.getAllTweets());
    }
}
