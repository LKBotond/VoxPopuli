package com.VoxPopuli.CommentService.services;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.VoxPopuli.CommentService.clients.FilterClient;
import com.VoxPopuli.CommentService.domain.Comment;
import com.VoxPopuli.CommentService.exceptions.CommentDoesntExistException;
import com.VoxPopuli.CommentService.exceptions.VandalismException;
import com.VoxPopuli.CommentService.mappers.CommentMapper;
import com.VoxPopuli.CommentService.repository.CommentRepository;
import com.VoxPopuli.commentcontracts.CommentEditRequest;
import com.VoxPopuli.commentcontracts.CommentRequest;
import com.VoxPopuli.commentcontracts.CommentResponse;
import com.VoxPopuli.filtercontracts.CensorRequest;
import com.VoxPopuli.filtercontracts.CensorResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final FilterClient filterClient;

    public CommentResponse registerComment(CommentRequest commentRequest) {
        CensorResponse response = censorComment(commentRequest.getContent());
        if (response.isFlagged()) {
            throw new VandalismException("The following words got caught in our web", response.getCaughtWords());
        }
        return buildCommentResponse(saveComment(commentRequest));
    }

    public CommentResponse registerCommentEdit(CommentEditRequest request) {
        CensorResponse response = censorComment(request.getEditedContent());
        if (response.isFlagged()) {
            throw new VandalismException("The following words got caught in our web", response.getCaughtWords());
        }
        return buildCommentResponse(editComment(request));
    }

    public CommentResponse registerCommentDeletion(UUID request) {
        return buildCommentResponse(deleteComment(request));
    }

    public List<CommentResponse> getAllCommentsForSite(String sourceLinkHash) {
        return parseCommentsIntoCommentResponseList(commentRepository.findAllBySourceLinkHash(sourceLinkHash));
    }

    private CensorResponse censorComment(String content) {
        return filterClient.checkRequest(new CensorRequest(content));
    }

    private Comment deleteComment(UUID commentID) {
        Comment old = loadCommentById(commentID);
        Comment deleted = deleteUserSpecificData(old);
        return commentRepository.save(deleted);
    }

    private Comment editComment(CommentEditRequest request) {
        Comment old = loadCommentById(UUID.fromString(request.getCommentId()));
        old.setContent(request.getEditedContent());
        return commentRepository.save(old);
    }

    private Comment saveComment(CommentRequest request) {
        return commentRepository.save(CommentMapper.fromRequest(request));
    }

    private CommentResponse buildCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getCommentId().toString())
                .parentId(comment.getParentId().toString())
                .userId(comment.getUserId().toString())
                .content(comment.getContent())
                .updatedAt(comment.getLastUpdated())
                .build();
    }

    private List<CommentResponse> parseCommentsIntoCommentResponseList(List<Comment> comments) {
        List<CommentResponse> responseList = new ArrayList<>();
        for (Comment comment : comments) {
            responseList.add(buildCommentResponse(comment));
        }
        return responseList;
    }

    private Comment loadCommentById(UUID commentId) {
        return commentRepository.findByCommentId(commentId)
                .orElseThrow(() -> new CommentDoesntExistException(
                        "Comment with the ID of: " + commentId + " doesnt exist"));
    }

    private Comment deleteUserSpecificData(Comment comment) {
        comment.setUserId(null);
        comment.setContent(null);
        comment.setLastUpdated(OffsetDateTime.now());
        return comment;
    }
}
