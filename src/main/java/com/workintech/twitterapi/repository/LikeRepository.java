package com.workintech.twitterapi.repository;

import com.workintech.twitterapi.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    // Belirli bir tweet ve kullanıcı için Like nesnesini bul
    Like findByTweet_IdAndUser_Id(Long tweetId, Long userId);

    // Tweet ve kullanıcı için Like var mı kontrol et
    boolean existsByTweet_IdAndUser_Id(Long tweetId, Long userId);
}