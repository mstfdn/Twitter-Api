package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.Like;
import com.workintech.twitterapi.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    public void likeTweet(Like like) {
        likeRepository.save(like);
    }

    public void dislikeTweet(Long tweetId, Long userId) {
        likeRepository.deleteById(likeRepository.findByTweetIdAndUserId(tweetId, userId).getId());
    }

    public boolean isLiked(Long tweetId, Long userId) {
        return likeRepository.existsByTweetIdAndUserId(tweetId, userId);
    }
}
