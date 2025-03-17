package com.workintech.twitterapi.repository;

import com.workintech.twitterapi.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Like findByTweet_IdAndUser_Id(Long tweetId, Long userId);


    boolean existsByTweet_IdAndUser_Id(Long tweetId, Long userId);
}