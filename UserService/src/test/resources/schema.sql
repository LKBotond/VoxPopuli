-- ============================
-- Initial setup during development
-- Drops tables and types if they exist (for clean setup)
-- ============================
DROP TABLE IF EXISTS users CASCADE;

DROP EXTENSION IF EXISTS citext;

DROP EXTENSION IF EXISTS pgcrypto;

CREATE EXTENSION IF NOT EXISTS citext;

CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- ============================
-- Users Table
-- ============================
CREATE TABLE
    users (
        user_id UUID DEFAULT gen_random_uuid() NOT NULL,
        email CITEXT UNIQUE NOT NULL,
        alias CITEXT UNIQUE NOT NULL,
        pass_hash TEXT NOT NULL,
        CONSTRAINT pk_users PRIMARY KEY (user_id)
    );

CREATE INDEX idx_email ON users (email);