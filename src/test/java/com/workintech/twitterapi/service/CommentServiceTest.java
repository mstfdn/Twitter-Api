package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.Comment;
import com.workintech.twitterapi.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCommentsByTweetId_ShouldReturnCommentList() {
        // Arrange
        Long tweetId = 1L;
        List<Comment> expectedComments = Arrays.asList(
            new Comment(),
            new Comment()
        );
        when(commentRepository.findByTweet_Id(tweetId)).thenReturn(expectedComments);

        // Act
        List<Comment> result = commentService.getCommentsByTweetId(tweetId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(commentRepository).findByTweet_Id(tweetId);
    }

    @Test
    void updateComment_ShouldUpdateAndReturnComment() {
        // Arrange
        Long commentId = 1L;
        Comment existingComment = new Comment();
        existingComment.setId(commentId);
        existingComment.setContent("Old content");

        Comment updatedComment = new Comment();
        updatedComment.setContent("New content");

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(existingComment));
        when(commentRepository.save(any(Comment.class))).thenReturn(updatedComment);

        // Act
        Comment result = commentService.updateComment(commentId, updatedComment);

        // Assert
        assertNotNull(result);
        assertEquals("New content", result.getContent());
        verify(commentRepository).findById(commentId);
        verify(commentRepository).save(any(Comment.class));
    }
} 