package com.VoxPopuli.CommentService.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.VoxPopuli.CommentService.services.CommentService;
import com.VoxPopuli.commentcontracts.CommentEditRequest;
import com.VoxPopuli.commentcontracts.CommentRequest;
import com.VoxPopuli.commentcontracts.CommentResponse;
import com.VoxPopuli.headercontracts.NamingConventions;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> comment(
            @RequestHeader(NamingConventions.userId) UUID userId,
            @RequestBody CommentRequest request) {
        CommentResponse response = commentService.registerComment(request, userId);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<CommentResponse> editComment(
            @RequestHeader(NamingConventions.userId) UUID userId,
            @RequestBody CommentEditRequest request) {
        CommentResponse edited = commentService.registerCommentEdit(request, userId);
        return ResponseEntity.ok().body(edited);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentResponse> deleteComment(
            @RequestHeader(NamingConventions.userId) UUID userId,
            @PathVariable("commentId") UUID commentID) {
        CommentResponse deleted = commentService.registerCommentDeletion(commentID, userId);
        return ResponseEntity.ok().body(deleted);

    }

    @GetMapping("/{sourceLinkHash}")
    public ResponseEntity<List<CommentResponse>> findAllCommentsForSite(
            @PathVariable("sourceLinkHash") String sourceLinkHash) {
        return ResponseEntity.ok().body(commentService.getAllCommentsForSite(sourceLinkHash));
    }

}
