package com.workintech.twitterapi.controller;

import com.workintech.twitterapi.entity.Retweet;
import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.service.RetweetService;
import com.workintech.twitterapi.service.TweetService;
import com.workintech.twitterapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/retweet")
public class RetweetController {

    @Autowired
    private RetweetService retweetService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private TweetService tweetService;

    @PostMapping("/{tweetId}")
    public ResponseEntity<Map<String, Object>> retweet(@PathVariable Long tweetId, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        Tweet tweet = tweetService.getTweetById(tweetId);
        
        try {
            Retweet retweet = retweetService.createRetweet(user, tweet);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Tweet retweet edildi");
            response.put("retweetId", retweet.getId());
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{tweetId}")
    public ResponseEntity<Map<String, Object>> deleteRetweet(@PathVariable Long tweetId, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        
        retweetService.deleteRetweetByTweetAndUser(tweetId, user.getId());
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Retweet kaldırıldı");
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/check/{tweetId}")
    public ResponseEntity<Map<String, Object>> checkRetweet(@PathVariable Long tweetId, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        
        boolean isRetweeted = retweetService.isRetweeted(tweetId, user.getId());
        
        Map<String, Object> response = new HashMap<>();
        response.put("isRetweeted", isRetweeted);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/count/{tweetId}")
    public ResponseEntity<Map<String, Object>> getRetweetCount(@PathVariable Long tweetId) {
        int retweetCount = retweetService.getRetweetCount(tweetId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("count", retweetCount);
        
        return ResponseEntity.ok(response);
    }
}
