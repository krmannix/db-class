

CREATE SEQUENCE Albums_album_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 14
  CACHE 1;

CREATE TABLE Albums (
	album_id int PRIMARY KEY DEFAULT nextval('Albums_album_id_seq'),
	user_id int,
	name varchar(200),
	created_at date
);

ALTER TABLE pictures ADD album_id int;

CREATE TABLE friends (
	user_id_1 int4 NOT NULL,
	user_id_2 int4 NOT NULL
);