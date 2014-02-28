CREATE TABLE country
(
  id serial NOT NULL,
  code character varying(10),
  name character varying(40),
  majuser character varying(40),
  majdate date,
  CONSTRAINT country_code_key UNIQUE (code)
)
WITH (
  OIDS=FALSE
);
