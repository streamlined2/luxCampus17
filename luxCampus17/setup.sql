-- Table: blog.post

-- DROP TABLE blog.post;

CREATE TABLE IF NOT EXISTS blog.post
(
    id bigint NOT NULL,
    title character varying(100) COLLATE pg_catalog."default" NOT NULL,
    content character varying(10000) COLLATE pg_catalog."default" NOT NULL,
    star boolean NOT NULL,
    CONSTRAINT primary_key PRIMARY KEY (id),
    CONSTRAINT title UNIQUE (title)
)

-- SEQUENCE: blog.post_generator

-- DROP SEQUENCE blog.post_generator;

CREATE SEQUENCE blog.post_generator
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- Table: blog.comment

-- DROP TABLE blog.comment;

CREATE TABLE IF NOT EXISTS blog.comment
(
    id bigint NOT NULL,
    text character varying(10000) COLLATE pg_catalog."default" NOT NULL,
    creation_date date NOT NULL,
    post_id bigint NOT NULL,
    CONSTRAINT comment_pkey PRIMARY KEY (id),
    CONSTRAINT post_id FOREIGN KEY (post_id)
        REFERENCES blog.post (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

-- SEQUENCE: blog.comment_generator

-- DROP SEQUENCE blog.comment_generator;

CREATE SEQUENCE blog.comment_generator
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- Table: blog.tag

-- DROP TABLE blog.tag;

CREATE TABLE IF NOT EXISTS blog.tag
(
    id bigint NOT NULL,
    name character varying(20) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT id PRIMARY KEY (id),
    CONSTRAINT name UNIQUE (name)
)

-- SEQUENCE: blog.tag_generator

-- DROP SEQUENCE blog.tag_generator;

CREATE SEQUENCE blog.tag_generator
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- Table: blog.posts_tags

-- DROP TABLE blog.posts_tags;

CREATE TABLE IF NOT EXISTS blog.posts_tags
(
    post_id bigint NOT NULL,
    tag_id bigint NOT NULL,
    CONSTRAINT "unique" UNIQUE (post_id, tag_id)
)

-- SEQUENCE: blog.user_generator

-- DROP SEQUENCE blog.user_generator;

CREATE SEQUENCE blog.user_generator
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- Table: blog.user

-- DROP TABLE blog."user";

CREATE TABLE IF NOT EXISTS blog."user"
(
    id bigint NOT NULL,
    name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    password character varying(100) COLLATE pg_catalog."default" NOT NULL,
    role character varying(20) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id),
    CONSTRAINT unique_name UNIQUE (name)
)
