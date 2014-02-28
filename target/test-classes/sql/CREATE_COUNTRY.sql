CREATE TABLE country
(
  id serial NOT NULL,
  code character varying(10),
  name character varying(40),
  CONSTRAINT code_name_key UNIQUE (code)
)
WITH (
  OIDS=FALSE
);
