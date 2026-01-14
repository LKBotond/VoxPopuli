package com.VoxPopuli.Gateway.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.VoxPopuli.commentcontracts.CommentEditRequest;
import com.VoxPopuli.commentcontracts.CommentRequest;
import com.VoxPopuli.commentcontracts.CommentResponse;

@RequestMapping("api/v1/gateway/comments")
public class CommentController {

    @PostMapping("/comment")
    public ResponseEntity<CommentResponse> comment(@RequestBody CommentRequest request) {
        return ResponseEntity.ok().build();
    }

    @PutMapping("/edit")
    public ResponseEntity<CommentResponse> editComment(@RequestBody CommentEditRequest request) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<CommentResponse> postMethodName(@PathVariable("commentId") UUID commentID) {
        return ResponseEntity.ok().build();

    }

    @GetMapping("/{sourceLinkHash}")
    public ResponseEntity<List<CommentResponse>> findAllCommentsForSite(
            @PathVariable("sourceLinkHash") String sourceLinkHash) {
        return ResponseEntity.ok().build();
    }
}
