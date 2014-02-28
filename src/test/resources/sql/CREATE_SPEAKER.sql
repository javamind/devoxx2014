CREATE TABLE speaker
(
  id serial NOT NULL,
  firstname character varying(40),
  lastname character varying(40),
  company character varying(50),
  streetadress character varying(50),
  postalcode character varying(40),
  city character varying(40),
  country_id integer,
  majuser character varying(40),
  majdate date,
  CONSTRAINT speaker_name_key UNIQUE (firstname,lastname)
)
WITH (
  OIDS=FALSE
);
