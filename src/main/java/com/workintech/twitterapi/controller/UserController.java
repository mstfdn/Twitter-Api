package com.workintech.twitterapi.controller;

import com.workintech.twitterapi.dto.UserResponseDTO;
import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{username}")
    public UserResponseDTO getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return convertToDTO(user);
    }
    

    private UserResponseDTO convertToDTO(User user) {
        if (user == null) {
            return null;
        }
        
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        

        if (user.getTweets() != null) {
            List<UserResponseDTO.TweetSummaryDTO> tweetDTOs = user.getTweets().stream()
                .map(tweet -> new UserResponseDTO.TweetSummaryDTO(
                    tweet.getId(),
                    tweet.getContent()
                ))
                .collect(Collectors.toList());
            dto.setTweets(tweetDTOs);
        }
        
        return dto;
    }
}