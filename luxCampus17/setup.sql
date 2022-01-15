-- Table: public.post

-- DROP TABLE public.post;

CREATE TABLE IF NOT EXISTS public.post
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    title character varying(100) COLLATE pg_catalog."default" NOT NULL,
    content character varying(10000) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT primary_key PRIMARY KEY (id),
    CONSTRAINT title UNIQUE (title)
)
