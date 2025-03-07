package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    public Tweet createTweet(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    public List<Tweet> getTweetsByUserId(Long userId) {
        return tweetRepository.findByUserId(userId);
    }

    public Tweet getTweetById(Long id) {
        return tweetRepository.findById(id).orElse(null);
    }

    public Tweet updateTweet(Long id, Tweet tweet) {
        Tweet existingTweet = tweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tweet not found with id: " + id));

        existingTweet.setContent(tweet.getContent());
        existingTweet.setUpdateAt(LocalDateTime.now());
        // Diğer gerekli alanları güncelleyin

        return tweetRepository.save(existingTweet);
    }

    public void deleteTweet(Long id) {
        tweetRepository.deleteById(id);
    }
}
