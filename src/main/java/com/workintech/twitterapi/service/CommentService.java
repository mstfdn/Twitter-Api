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
        comment.setCreatedAt(LocalDateTime.now()); // Yorum oluşturulma zamanını ayarlayın
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByTweetId(Long tweetId) {
        return commentRepository.findByTweetId(tweetId);
    }

    public Comment updateComment(Long id, Comment comment) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + id));

        existingComment.setContent(comment.getContent());
        // Diğer gerekli alanları buraya ekleyebilirsiniz

        return commentRepository.save(existingComment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
