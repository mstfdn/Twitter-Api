package com.workintech.twitterapi.controller;

import com.workintech.twitterapi.entity.Like;
import com.workintech.twitterapi.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping
    public ResponseEntity<Void> likeTweet(@RequestBody Like like) {
        likeService.likeTweet(like);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> dislikeTweet(@RequestParam Long tweetId, @RequestParam Long userId) {
        likeService.dislikeTweet(tweetId, userId);
        return ResponseEntity.noContent().build();
    }
}
