package com.workintech.twitterapi.controller;

import com.workintech.twitterapi.dto.TweetResponseDTO;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tweet")
public class TweetController {

    @Autowired
    private TweetService tweetService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<TweetResponseDTO> createTweet(@RequestBody Tweet tweet, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        tweet.setUser(user);
        tweet.setCreatedAt(LocalDateTime.now());
        Tweet savedTweet = tweetService.createTweet(tweet);
        return ResponseEntity.ok(convertToDTO(savedTweet));
    }

    @GetMapping("/findByUserId")
    public ResponseEntity<List<TweetResponseDTO>> getTweetsByUserId(@RequestParam Long userId) {
        List<Tweet> tweets = tweetService.getTweetsByUserId(userId);
        List<TweetResponseDTO> tweetDTOs = tweets.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tweetDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TweetResponseDTO> getTweetById(@PathVariable Long id) {
        Tweet tweet = tweetService.getTweetById(id);
        return ResponseEntity.ok(convertToDTO(tweet));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TweetResponseDTO> updateTweet(@PathVariable Long id, @RequestBody Tweet tweet) {
        Tweet updatedTweet = tweetService.updateTweet(id, tweet);
        return ResponseEntity.ok(convertToDTO(updatedTweet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable Long id) {
        tweetService.deleteTweet(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TweetResponseDTO>> getAllTweets() {
        List<Tweet> tweets = tweetService.getAllTweets();
        List<TweetResponseDTO> tweetDTOs = tweets.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tweetDTOs);
    }
    
    // Entity'den DTO'ya dönüştürme metodu
    private TweetResponseDTO convertToDTO(Tweet tweet) {
        TweetResponseDTO dto = new TweetResponseDTO();
        dto.setId(tweet.getId());
        dto.setContent(tweet.getContent());
        dto.setCreatedAt(tweet.getCreatedAt());
        dto.setUpdateAt(tweet.getUpdateAt());
        
        if (tweet.getUser() != null) {
            TweetResponseDTO.UserSummaryDTO userDTO = new TweetResponseDTO.UserSummaryDTO(
                tweet.getUser().getId(),
                tweet.getUser().getUsername()
            );
            dto.setUser(userDTO);
        }
        
        return dto;
    }
}
