package com.VoxPopuli.CommentService.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.VoxPopuli.CommentService.dto.CommentEditRequest;
import com.VoxPopuli.CommentService.dto.CommentRequest;
import com.VoxPopuli.CommentService.dto.CommentResponse;
import com.VoxPopuli.CommentService.services.CommentService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<CommentResponse> comment(@RequestBody CommentRequest request) {
        CommentResponse response = commentService.registerComment(request);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/edit")
    public ResponseEntity<CommentResponse> editComment(@RequestBody CommentEditRequest request) {
        CommentResponse edited = commentService.registerCommentEdit(request);
        return ResponseEntity.ok().body(edited);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentResponse> postMethodName(@PathVariable("commentId") UUID commentID) {
        CommentResponse deleted = commentService.registerCommentDeletion(commentID);
        return ResponseEntity.ok().body(deleted);

    }

    @GetMapping("/{sourceLinkHash}")
    public ResponseEntity<List<CommentResponse>> findAllCommentsForSite(@PathVariable("sourceLinkHash") String sourceLinkHash) {
        return ResponseEntity.ok().body(commentService.getAllCommentsForSite(sourceLinkHash));
    }

}
