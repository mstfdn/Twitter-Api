package com.workintech.twitterapi.controller;

import com.workintech.twitterapi.dto.CommentResponseDTO;
import com.workintech.twitterapi.entity.Comment;
import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.service.CommentService;
import com.workintech.twitterapi.service.TweetService;
import com.workintech.twitterapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private TweetService tweetService;

    @PostMapping("/{tweetId}")
    public ResponseEntity<CommentResponseDTO> addComment(
            @PathVariable Long tweetId,
            @RequestBody Map<String, String> payload,
            Authentication authentication) {
        
        String content = payload.get("content");
        if (content == null || content.trim().isEmpty()) {
            throw new RuntimeException("Yorum içeriği boş olamaz");
        }
        
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        Tweet tweet = tweetService.getTweetById(tweetId);
        
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setTweet(tweet);
        comment.setCreatedAt(LocalDateTime.now());
        
        Comment savedComment = commentService.addComment(comment);
        return ResponseEntity.ok(convertToDTO(savedComment));
    }

    @GetMapping("/tweet/{tweetId}")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsByTweetId(@PathVariable Long tweetId) {
        List<Comment> comments = commentService.getCommentsByTweetId(tweetId);
        List<CommentResponseDTO> commentDTOs = comments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(commentDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> updateComment(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload,
            Authentication authentication) {
        
        String content = payload.get("content");
        if (content == null || content.trim().isEmpty()) {
            throw new RuntimeException("Yorum içeriği boş olamaz");
        }
        
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        
        Comment existingComment = commentService.getCommentById(id);
        
        // Sadece yorum sahibi yorumu güncelleyebilir
        if (!existingComment.getUser().getId().equals(user.getId())) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Bu yorumu güncelleme yetkiniz yok");
            return ResponseEntity.status(403).body(null);
        }
        
        Comment comment = new Comment();
        comment.setContent(content);
        
        Comment updatedComment = commentService.updateComment(id, comment);
        return ResponseEntity.ok(convertToDTO(updatedComment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteComment(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        
        Comment existingComment = commentService.getCommentById(id);
        
        // Sadece yorum sahibi yorumu silebilir
        if (!existingComment.getUser().getId().equals(user.getId())) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Bu yorumu silme yetkiniz yok");
            return ResponseEntity.status(403).body(response);
        }
        
        commentService.deleteComment(id);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Yorum başarıyla silindi");
        
        return ResponseEntity.ok(response);
    }
    
    // Entity'den DTO'ya dönüştürme metodu
    private CommentResponseDTO convertToDTO(Comment comment) {
        CommentResponseDTO dto = new CommentResponseDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setTweetId(comment.getTweetId());
        
        if (comment.getUser() != null) {
            CommentResponseDTO.UserSummaryDTO userDTO = new CommentResponseDTO.UserSummaryDTO(
                comment.getUser().getId(),
                comment.getUser().getUsername()
            );
            dto.setUser(userDTO);
        }
        
        return dto;
    }
}
