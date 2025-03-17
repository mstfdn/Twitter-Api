package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.Comment;
import com.workintech.twitterapi.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment addComment(Comment comment) {
        comment.setCreatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByTweetId(Long tweetId) {
        return commentRepository.findByTweet_Id(tweetId);
    }

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Yorum bulunamadı: " + id));
    }

    public Comment updateComment(Long id, Comment comment) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + id));

        existingComment.setContent(comment.getContent());


        return commentRepository.save(existingComment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
