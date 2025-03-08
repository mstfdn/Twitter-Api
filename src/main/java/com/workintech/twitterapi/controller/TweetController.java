package com.workintech.twitterapi.controller;

import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweet")
public class TweetController {

    @Autowired
    private TweetService tweetService;

    @PostMapping
    public ResponseEntity<Tweet> createTweet(@RequestBody Tweet tweet) {
        return ResponseEntity.ok(tweetService.createTweet(tweet));
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
}
