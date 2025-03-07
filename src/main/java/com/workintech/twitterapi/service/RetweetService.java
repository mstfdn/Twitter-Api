package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.Retweet;
import com.workintech.twitterapi.repository.RetweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RetweetService {

    @Autowired
    private RetweetRepository retweetRepository;

    public void retweet(Retweet retweet) {
        retweetRepository.save(retweet);
    }

    public void deleteRetweet(Long id) {
        retweetRepository.deleteById(id);
    }
}
