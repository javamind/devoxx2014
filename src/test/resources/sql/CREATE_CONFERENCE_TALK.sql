CREATE TABLE conference_talk
(
  id integer NOT NULL,
  conference_id integer,
  talk_id integer,
  CONSTRAINT conference_talk_key UNIQUE (conference_id, talk_id)
)
WITH (
  OIDS=FALSE
);

CREATE SEQUENCE seq_conference_talk
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
