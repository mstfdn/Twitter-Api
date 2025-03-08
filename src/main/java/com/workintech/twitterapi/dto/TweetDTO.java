package com.workintech.twitterapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TweetDTO {
    @NotNull
    @Size(max = 200)
    private String content;

    @NotNull
    private Long userId;
}
