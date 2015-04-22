

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
	created_at timestamp DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE pictures ADD album_id int;

CREATE TABLE friends (
	user_id_1 int4 NOT NULL,
	user_id_2 int4 NOT NULL
);

SELECT * FROM Users WHERE user_id IN (SELECT user_id_1 AS id WHERE user_id_2 = ? UNION SELECT user_id_2 AS id WHERE user_id_1 = ?);

CREATE SEQUENCE Tags_tag_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 14
  CACHE 1;

CREATE TABLE tag (
	tag_name varchar(200),
	tag_id int PRIMARY KEY DEFAULT nextval('Tags_tag_id_seq')
);

CREATE TABLE tag_photo (
	tag_id int,
	photo_id int
);