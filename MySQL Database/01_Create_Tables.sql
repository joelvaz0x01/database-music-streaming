DROP DATABASE IF EXISTS Project;
CREATE DATABASE Project;
USE Project;

CREATE TABLE Artist (
	artist_id INTEGER PRIMARY KEY AUTO_INCREMENT,
	artist_name VARCHAR(255) NOT NULL,
	birth_year INTEGER NOT NULL,
	birth_country VARCHAR(255) NOT NULL
);

# Instrument multivalued attributte
CREATE TABLE ArtistInstruments (
	artist_id INTEGER NOT NULL,
	instrument_name VARCHAR(255) NOT NULL,
	PRIMARY KEY (artist_id, instrument_name),
	FOREIGN KEY (artist_id) REFERENCES Artist (artist_id)
);

CREATE TABLE SoloArtist (
	artist_id INTEGER PRIMARY KEY,
	start_career DATE NOT NULL,
	stage_name VARCHAR(255) NOT NULL,
	FOREIGN KEY (artist_id) REFERENCES Artist (artist_id)
);

CREATE TABLE Band (
	artist_id INTEGER PRIMARY KEY NOT NULL,
	band_name VARCHAR(255) NOT NULL,
	start_contract DATE NOT NULL,
	end_contract DATE,
	FOREIGN KEY (artist_id) REFERENCES Artist (artist_id)
);

CREATE TABLE Album (
	album_id INTEGER PRIMARY KEY AUTO_INCREMENT,
	artist_id INTEGER NOT NULL,
	album_name VARCHAR(255) NOT NULL,
	release_year INTEGER NOT NULL,
    release_month INTEGER NOT NULL,
    release_day INTEGER NOT NULL,
	release_data DATE GENERATED ALWAYS AS (CONCAT(release_year, '-', release_month,'-', release_day)) VIRTUAL,
	FOREIGN KEY (artist_id) REFERENCES Artist (artist_id)
);

CREATE TABLE StudioAlbum (
	album_id INTEGER PRIMARY KEY,
	studio VARCHAR(255) NOT NULL,
	FOREIGN KEY (album_id) REFERENCES Album (album_id)
);

CREATE TABLE LiveAlbum (
	album_id INTEGER PRIMARY KEY,
	date DATE NOT NULL,
	location VARCHAR(255) NOT NULL,
	FOREIGN KEY (album_id) REFERENCES Album (album_id)
);

CREATE TABLE Song (
	song_id INTEGER PRIMARY KEY AUTO_INCREMENT,
	album_id INTEGER NOT NULL,
	song_name VARCHAR(255) NOT NULL,
	song_length INTEGER NOT NULL,
	FOREIGN KEY (album_id) REFERENCES Album (album_id)
);

# SongMusicType multivalued attributte
CREATE TABLE SongMusicType (
	song_id INTEGER NOT NULL,
    music_type_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (song_id, music_type_name),
	FOREIGN KEY (song_id) REFERENCES Song (song_id)
);

CREATE VIEW vw_Artist AS
SELECT a.artist_id, a.artist_name, a.birth_year, a.birth_country, (YEAR(CURDATE()) - a.birth_year) AS age, GROUP_CONCAT(ai.instrument_name) AS instrument
FROM Artist a
INNER JOIN ArtistInstruments ai ON a.artist_id = ai.artist_id
GROUP BY a.artist_id;

CREATE VIEW vw_Band AS
SELECT b.artist_id, a.artist_name, b.start_contract, b.end_contract
FROM Band b
INNER JOIN Artist a ON b.artist_id = a.artist_id
GROUP BY b.artist_id;

CREATE VIEW vw_Album AS
SELECT al.album_id, ar.artist_name, al.album_name, al.release_data
FROM Album al
INNER JOIN Artist ar ON al.artist_id = ar.artist_id
GROUP BY al.album_id;

CREATE VIEW vw_StudioAlbum AS
SELECT a.album_name, s.studio
FROM StudioAlbum s
INNER JOIN Album a ON s.album_id = a.album_id
GROUP BY s.album_id;

CREATE VIEW vw_LiveAlbum AS
SELECT a.album_name, l.date, l.location
FROM LiveAlbum l
INNER JOIN Album a ON l.album_id = a.album_id
GROUP BY l.album_id;

CREATE VIEW vw_Song AS
SELECT s.song_id, s.song_name, s.song_length, GROUP_CONCAT(smt.music_type_name) AS music_type, a.album_name
FROM Song s
INNER JOIN SongMusicType smt ON s.song_id = smt.song_id
INNER JOIN Album a ON s.album_id = a.album_id
GROUP BY s.song_id;

