USE Project;

INSERT INTO Artist (artist_name, birth_year, birth_country)
VALUES 
('John Doe', 1975, 'USA'),
('Jane Doe', 1985, 'Australia'),
('John Smith', 1989, 'Canada');

INSERT INTO ArtistInstruments (artist_id, instrument_name)
VALUES 
(1, 'Guitar'),
(1, 'Drums'),
(2, 'Piano'),
(3, 'Guitar'),
(3, 'Vocals');

INSERT INTO SoloArtist (artist_id, start_career, stage_name)
VALUES 
(1, '2000-01-01', 'Johnny D.'),
(2, '2005-02-02', 'Janey D.');

INSERT INTO Band (artist_id, band_name, start_contract, end_contract)
VALUES 
(3, 'The Smiths', '2010-03-03', '2020-04-04');

INSERT INTO Album (artist_id, album_name, release_year, release_month, release_day)
VALUES 
(1, 'The Best of John Doe', 2006, 9, 5),
(1, 'Live in Concert', 2007, 7, 15),
(2, 'The Best of Jane Doe', 2007, 10, 5),
(3, 'The Smiths Greatest Hits', 2011, 1, 15);

INSERT INTO StudioAlbum (album_id, studio)
VALUES 
(1, 'Studio A'),
(2, 'Studio B'),
(3, 'Studio C'),
(4, 'Studio D');

INSERT INTO LiveAlbum (album_id, date, location)
VALUES 
(2, '2007-07-15', 'New York'),
(4, '2011-01-15', 'Las Vegas');

INSERT INTO Song (album_id, song_name, song_length)
VALUES 
(1, 'Song 1', 240),
(1, 'Song 2', 275),
(2, 'Song 3', 288),
(2, 'Song 4', 435),
(3, 'Song 5', 212),
(3, 'Song 6', 345),
(4, 'Song 7', 245),
(4, 'Song 8', 290);

INSERT INTO SongMusicType (song_id, music_type_name)
VALUES 
(1, 'Rock'),
(2, 'Pop'),
(3, 'Folk'),
(4, 'Country'),
(5, 'Jazz'),
(6, 'Blues'),
(7, 'Classical'),
(8, 'Funk');
