package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TweetService {

    private final TweetRepository tweetRepository;

    @Autowired
    public TweetService(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    public Tweet createTweet(Tweet tweet) {
        tweet.setCreatedAt(LocalDateTime.now());
        // User nesnesini repository'den alıp tweet'e atayabilirsiniz
        return tweetRepository.save(tweet);
    }

    public List<Tweet> getTweetsByUserId(Long userId) {
        return tweetRepository.findByUser_Id(userId);
    }

    public Tweet getTweetById(Long id) {
        return tweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tweet not found with id: " + id));
    }

    public Tweet updateTweet(Long id, Tweet tweet) {
        Tweet existingTweet = getTweetById(id);
        existingTweet.setContent(tweet.getContent());
        existingTweet.setUpdateAt(LocalDateTime.now());
        // Diğer gerekli alanları güncelleyin

        return tweetRepository.save(existingTweet);
    }

    public void deleteTweet(Long id) {
        tweetRepository.deleteById(id);
    }

    public List<Tweet> getAllTweets() {
        return tweetRepository.findAll();
    }
}
