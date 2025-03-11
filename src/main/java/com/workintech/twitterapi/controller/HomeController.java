package com.workintech.twitterapi.controller;

import com.workintech.twitterapi.dto.TweetResponseDTO;
import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HomeController {

    private final TweetService tweetService;

    @Autowired
    public HomeController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping({"/", "/home"})
    public ResponseEntity<List<TweetResponseDTO>> getHomePage() {
        // Ana sayfada tüm tweetleri göster (gerçek uygulamada bu daha karmaşık olabilir)
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