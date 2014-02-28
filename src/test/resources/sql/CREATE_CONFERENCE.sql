CREATE TABLE conference
(
  id serial NOT NULL,
  name character varying(40),
  city character varying(40),
  streetadress character varying(50),
  postalcode character varying(40),
  country_id integer,
  datestart date,
  dateend date,
  majuser character varying(40),
  majdate date,
  CONSTRAINT conference_name_key UNIQUE (name)
)
WITH (
  OIDS=FALSE
);
