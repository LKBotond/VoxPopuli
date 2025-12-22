package com.VoxPopuli.CommentService.services;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.VoxPopuli.CommentService.domain.Comment;
import com.VoxPopuli.CommentService.dto.CommentEditRequest;
import com.VoxPopuli.CommentService.dto.CommentRequest;
import com.VoxPopuli.CommentService.dto.CommentResponse;
import com.VoxPopuli.CommentService.exceptions.CommentDoesntExistException;
import com.VoxPopuli.CommentService.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentResponse registerComment(CommentRequest commentRequest) {
        return buildCommentResponse(saveComment(commentRequest));
    }

    public CommentResponse registerCommentEdit(CommentEditRequest request) {
        return buildCommentResponse(editComment(request));
    }

    public CommentResponse registerCommentDeletion(UUID request) {
        return buildCommentResponse(deleteComment(request));
    }

    public List<CommentResponse> getAllCommentsForSite(String sourceLinkHash) {
        return parseCommentsIntoCommentResponseList(commentRepository.findAllBySourceLinkHash(sourceLinkHash));
    }

    private Comment deleteComment(UUID commentID) {
        Comment old = loadCommentById(commentID);
        Comment deleted = deleteUserSpecificData(old);
        return commentRepository.save(deleted);
    }

    private Comment editComment(CommentEditRequest request) {
        Comment old = loadCommentById(request.getCommentId());
        old.setContent(request.getEditedContent());
        return commentRepository.save(old);
    }

    private Comment saveComment(CommentRequest request) {
        return commentRepository.save(request.mapToComment());
    }

    private CommentResponse buildCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .parentId(comment.getParentId())
                .userId(comment.getUserId())
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
