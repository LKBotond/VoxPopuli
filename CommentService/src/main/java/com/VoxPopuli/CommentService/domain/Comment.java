package com.VoxPopuli.CommentService.domain;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID commentId;
    
    @Column(name = "parent_id")
    UUID parentId;
    
    @Column(name = "user_id")
    UUID userId;
    
    @Column(name = "source_link_hash")
    String sourceLinkHash;
    
    @Column(name = "content")
    String content;
    
    @Column(name = "last_updated")
    OffsetDateTime lastUpdated;

}
