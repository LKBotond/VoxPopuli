-- ============================
-- Initial setup during development
-- Drops tables and types if they exist 
-- ============================
DROP TABLE IF EXISTS comments;

DROP EXTENSION IF EXISTS pgcrypto;

CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- ============================
-- Users Table
-- ============================

CREATE TABLE
    comments (
        comment_id UUID DEFAULT gen_random_uuid () NOT NULL,
        parent_id UUID,
        user_id UUID,
        source_link_hash TEXT NOT NULL,
        content TEXT,
        last_updated TIMESTAMPTZ,
        CONSTRAINT pk_comments PRIMARY KEY (comment_id)
    );