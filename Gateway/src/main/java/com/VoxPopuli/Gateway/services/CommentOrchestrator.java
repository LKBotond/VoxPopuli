package com.VoxPopuli.Gateway.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.VoxPopuli.Gateway.clients.CommentClient;
import com.VoxPopuli.Gateway.clients.FilterClient;
import com.VoxPopuli.Gateway.dtos.commetnClient.CommentEditRequest;
import com.VoxPopuli.Gateway.dtos.commetnClient.CommentRequest;
import com.VoxPopuli.Gateway.dtos.commetnClient.CommentResponse;
import com.VoxPopuli.Gateway.dtos.filterClient.CensorRequest;
import com.VoxPopuli.Gateway.dtos.filterClient.CensorResponse;

import lombok.RequiredArgsConstructor;

//move this to comment service
@Service
@RequiredArgsConstructor
public class CommentOrchestrator {
    private final CommentClient commentClient;
    private final FilterClient filterClient;

    public Object postComment(CommentRequest request) {
        CensorResponse response = filterClient.checkRequest(new CensorRequest(request.getContent()));
        if (!response.isFlagged()) {
            return commentClient.postComment(request);
        }
        return response;
    }

    public Object editComment(CommentEditRequest request) {
        CensorResponse response = filterClient.checkRequest(new CensorRequest(request.getEditedContent()));
        if (!response.isFlagged()) {
            return commentClient.editComment(request);
        }
        return response;
    }

    public List<CommentResponse> getAllForSite(String sourceLinkHash) {
        return commentClient.findAllCommentsForSite(sourceLinkHash);
    }

    public void deleteComment(UUID commentId) {
        commentClient.deleteComment(commentId);
    }
}
