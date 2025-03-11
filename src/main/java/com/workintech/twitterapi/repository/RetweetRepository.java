package com.workintech.twitterapi.repository;

import com.workintech.twitterapi.entity.Retweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RetweetRepository extends JpaRepository<Retweet, Long> {
    boolean existsByTweet_IdAndUser_Id(Long tweetId, Long userId);
    List<Retweet> findByTweet_IdAndUser_Id(Long tweetId, Long userId);
}
