package com.VoxPopuli.CommentService.repository;

import java.util.UUID;
import java.util.List;
import java.util.Optional;
import com.VoxPopuli.CommentService.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Optional<Comment> findByCommentId(UUID commentId);

    Optional<Comment> findByParentId(UUID parrentId);

    Optional<Comment> findByUserId(UUID userId);

    Optional<Comment> findBySourceLinkHash(String sourceLinkHash);

    List<Comment> findAllBySourceLinkHash(String sourceLinkHash);
}
