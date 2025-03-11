package com.workintech.twitterapi.controller;

import com.workintech.twitterapi.entity.Like;
import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.service.LikeService;
import com.workintech.twitterapi.service.TweetService;
import com.workintech.twitterapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/like")
public class LikeController {

    @Autowired
    private LikeService likeService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private TweetService tweetService;

    @PostMapping("/{tweetId}")
    public ResponseEntity<Map<String, Object>> likeTweet(@PathVariable Long tweetId, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        Tweet tweet = tweetService.getTweetById(tweetId);
        
        Like like = new Like();
        like.setUser(user);
        like.setTweet(tweet);
        
        likeService.likeTweet(like);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Tweet beğenildi");
        
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{tweetId}")
    public ResponseEntity<Map<String, Object>> dislikeTweet(@PathVariable Long tweetId, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        
        likeService.dislikeTweet(tweetId, user.getId());
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Tweet beğenisi kaldırıldı");
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/check/{tweetId}")
    public ResponseEntity<Map<String, Object>> checkLike(@PathVariable Long tweetId, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        
        boolean isLiked = likeService.isLiked(tweetId, user.getId());
        
        Map<String, Object> response = new HashMap<>();
        response.put("isLiked", isLiked);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/count/{tweetId}")
    public ResponseEntity<Map<String, Object>> getLikeCount(@PathVariable Long tweetId) {
        Tweet tweet = tweetService.getTweetById(tweetId);
        int likeCount = tweet.getLikes().size();
        
        Map<String, Object> response = new HashMap<>();
        response.put("count", likeCount);
        
        return ResponseEntity.ok(response);
    }
}
