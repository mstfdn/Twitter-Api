package com.workintech.twitterapi.controller;

import com.workintech.twitterapi.entity.Retweet;
import com.workintech.twitterapi.service.RetweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/retweet")
public class RetweetController {

    @Autowired
    private RetweetService retweetService;

    @PostMapping
    public ResponseEntity<Void> retweet(@RequestBody Retweet retweet) {
        retweetService.retweet(retweet);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRetweet(@PathVariable Long id) {
        retweetService.deleteRetweet(id);
        return ResponseEntity.noContent().build();
    }
}
