####################
#
#  CREATING TABLES
#
####################

############ Fundemental tables ############
# User

CREATE TABLE user (
	user_id integer PRIMARY KEY, 
	password varchar(20) NOT NULL, 
	first_name varchar(100) NOT NULL, 
	last_name varchar(100) NOT NULL, 
	email varchar(100) NOT NULL,
	dob date NOT NULL,
	male boolean,  
	home_city varchar(20),
	home_state varchar(2),
	home_country varchar(100),
	current_city varchar(20),
	current_state varchar(2),
	current_country varchar(100),
	university varchar(100),
	UNIQUE(user_id)
);

# Album

CREATE TABLE album (
	album_id int PRIMARY KEY,
	user_id int,
	created_at date
);

# Photo

CREATE TABLE photo (
	photo_id int PRIMARY KEY,
	album_id int,
	caption varchar(500),
	data bytea
);

# Tag

CREATE TABLE tag (
	tag_name varchar(200) PRIMARY KEY,
	tag_id int UNIQUE
);

# Comment

CREATE TABLE comment (
	comment_id int PRIMARY KEY,
	text varchar(500),
	user_id int,
	created_at date
);

############ Relation tables ############

# Friend
CREATE TABLE friend (
	user_id_1 int,
	user_id_2 int
);

# Likes
CREATE TABLE like (
	user_id int,
	photo_id int
);

# Comments on Photos
CREATE TABLE comment_photo (
	comment_id int PRIMARY KEY,
	photo_id int
);

# Tags on Photos
CREATE TABLE tag_photo (
	tag_id int,
	photo_id int
);

####################
#
#    SQL QUERIES
#   FOR USE CASES
#
####################

############ User management ############

# Check if user exists
SELECT COUNT(1) FROM user u WHERE u.user_id = ?; # values(user_id)
# Register user
INSERT INTO user (
	user_id,
	password,
	first_name,
	last_name,
	email, 
	dob, 
	male, 
	home_city, 
	home_state, 
	home_country, 
	current_city, 
	current_state, 
	current_country,
	university
) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);

# Insert friendship
IF NOT EXISTS (SELECT * FROM friend WHERE user_id_1 = ? AND user_id_2 = ?)
AND 
IF NOT EXISTS (SELECT * FROM friend WHERE user_id_2 = ? AND user_id_1 = ?)
THEN 
	INSERT INTO friend (user_id_1, user_id_2) VALUES (?, ?);
END IF; # Values (user_id_1, user_id_2, user_id_1, user_id_2, user_id_1, user_id_2)

# User Activity
SELECT user_id, SUM(total_comments + total_photos) AS total FROM (
	SELECT COUNT(*) AS total_comments, c.user_id AS user_id 
	FROM comment c GROUP BY c.user_id # COUNT COMMENTS PER USER
	INNER JOIN
	SELECT COUNT(*) AS total_photos
	FROM photo p GROUP BY p.user_id # COUNT PHOTOS PER USER	
	ON c.user_id = p.user_id
) ORDER BY total DESC LIMIT 10;


############ Album and photo management ############
# Get all photos
SELECT * FROM photo;

# Get certain number
SELECT * FROM photo LIMIT 100;

# Get 100 most recent photos
SELECT * FROM photo ORDER BY created_at DESC LIMIT 100;

# Get 100 most recent photos after 
SELECT * FROM photo ORDER BY created_at WHERE created_at < ? ORDER BY created_at DESC LIMIT 100; # Values (current_date)

# Create Album
INSERT INTO album (album_id, user_id, created_at) VALUES (SELECT cast((RAND()*1000) as int), ?, CURDATE());

# Create Photo
IF EXISTS (SELECT * FROM album WHERE album_id = ?)
THEN
	INSERT INTO photo (photo_id, album_id, caption, data) VALUES (cast((RAND()*1000) as int), ?, ?, ?);
END IF;

# Delete Photo 
DELETE FROM photo WHERE photo_id = ? AND user_id = ?;

# Delete Album + Photos
IF EXISTS (SELECT * FROM album WHERE album_id = ? AND user_id = ?)
THEN
	DELETE FROM photo WHERE album_id = ?;
END IF;
DELETE FROM album_id WHERE album_id = ? AND user_id = ?;

############ Tag management ############

# View photos by tag name
SELECT p.* FROM photo p 
WHERE p.photo_id IN (SELECT tp.photo_id FROM tag_photo tp
					 INNER JOIN tag t
					 ON tp.tag_id = t.tag_id
					 AND t.tag_name = ?); 

# View user photos by tag name
SELECT p.* FROM photo p 
WHERE p.user_id = ? 
AND p.photo_id IN (SELECT tp.photo_id FROM tag_photo tp
				   INNER JOIN tag t
				   ON tp.tag_id = t.tag_id
				   AND t.tag_name = ?); 

# View most popular tags
SELECT t.* FROM tag t
WHERE t.tag_id IN (SELECT COUNT(*) FROM tag_photo tp
				   GROUP BY tp.tag_id DESC LIMIT 10);













