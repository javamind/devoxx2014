CREATE TABLE talk
(
  id serial NOT NULL,
  name character varying(40),
  description character varying(1000),
  place character varying(50),
  nbpeoplemax integer,
  level character varying(40),
  majuser character varying(40),
  majdate date,
  CONSTRAINT talk_name_key UNIQUE (name)
)
WITH (
  OIDS=FALSE
);