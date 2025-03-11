package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.Retweet;
import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.repository.RetweetRepository;
import com.workintech.twitterapi.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetweetService {

    @Autowired
    private RetweetRepository retweetRepository;
    
    @Autowired
    private TweetRepository tweetRepository;

    public Retweet retweet(Retweet retweet) {
        return retweetRepository.save(retweet);
    }
    
    public Retweet createRetweet(User user, Tweet tweet) {
        // Kullanıcı zaten retweet etmiş mi kontrol et
        if (isRetweeted(tweet.getId(), user.getId())) {
            throw new RuntimeException("Bu tweet zaten retweet edilmiş");
        }
        
        Retweet retweet = new Retweet();
        retweet.setUser(user);
        retweet.setTweet(tweet);
        
        return retweetRepository.save(retweet);
    }

    public void deleteRetweet(Long id) {
        retweetRepository.deleteById(id);
    }
    
    public void deleteRetweetByTweetAndUser(Long tweetId, Long userId) {
        List<Retweet> retweets = retweetRepository.findByTweet_IdAndUser_Id(tweetId, userId);
        if (!retweets.isEmpty()) {
            retweetRepository.deleteById(retweets.get(0).getId());
        }
    }
    
    public boolean isRetweeted(Long tweetId, Long userId) {
        return retweetRepository.existsByTweet_IdAndUser_Id(tweetId, userId);
    }
    
    public int getRetweetCount(Long tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new RuntimeException("Tweet bulunamadı: " + tweetId));
        return tweet.getRetweets().size();
    }
}
