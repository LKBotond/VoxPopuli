package com.VoxPopuli.Gateway.clients;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.VoxPopuli.Gateway.dtos.commetnClient.CommentEditRequest;
import com.VoxPopuli.Gateway.dtos.commetnClient.CommentRequest;
import com.VoxPopuli.Gateway.dtos.commetnClient.CommentResponse;

@FeignClient(name = "comment-service", url = "http://comment-service:8080")
public interface CommentClient {

    @PostMapping("/internal/comments/comment")
    public CommentResponse postComment(@RequestBody CommentRequest request);

    @PutMapping("/internal/comments/edit")
    public CommentResponse editComment(@RequestBody CommentEditRequest request);

    @DeleteMapping("/internal/comments/delete/{commentId}")
    public CommentResponse deleteComment(@PathVariable("commentId") UUID commentID);

    @GetMapping("/internal/comments/{sourceLinkHash}")
    public List<CommentResponse> findAllCommentsForSite(
            @PathVariable("sourceLinkHash") String sourceLinkHash);
}