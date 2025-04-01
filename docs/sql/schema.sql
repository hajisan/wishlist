-- Opretter databasen, hvis den ikke allerede findes
CREATE DATABASE IF NOT EXISTS wish_list;
USE wish_list;

-- ========================
-- Tabel: profile
-- Gemmer information om brugere, der har ønskelister
-- ========================
CREATE TABLE profile (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(225),
  birthday DATE,
  email VARCHAR(225) UNIQUE,
  username VARCHAR(225) UNIQUE,
  password VARCHAR(255),
  PRIMARY KEY (id)
) AUTO_INCREMENT = 1000;

-- ========================
-- Tabel: wish_list
-- Indeholder ønskelister, som tilhører profiler
-- ========================
CREATE TABLE wish_list (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(225) UNIQUE,
  description VARCHAR(225),
  profile_id INT,
  PRIMARY KEY (id),
  FOREIGN KEY (profile_id) REFERENCES profile(id)
) AUTO_INCREMENT = 100;

-- ========================
-- Tabel: wish
-- Indeholder specifikke ønsker på ønskelister
-- ========================
CREATE TABLE wish (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(225),
  description VARCHAR(225),
  link VARCHAR(225),
  quantity INT,
  price DOUBLE,
  reserved TINYINT(1), -- 0 = ikke reserveret, 1 = reserveret
  wish_list_id INT,
  PRIMARY KEY (id),
  FOREIGN KEY (wish_list_id) REFERENCES wish_list(id)
) AUTO_INCREMENT = 5000;

-- ========================
-- Tabel: reservation
-- Gemmer information om reservationer lavet på ønsker
-- Navn kan være NULL, hvis personen ønsker at være anonym
-- profile_id kan også være NULL, hvis man reserverer uden at være logget ind
-- ========================
-- CREATE TABLE reservation (
-- id INT NOT NULL AUTO_INCREMENT,
-- name VARCHAR(225), -- Navn på personen der har reserveret (valgfrit)
-- wish_id INT,
-- wish_list_id INT,
-- profile_id INT,
-- PRIMARY KEY (id),
-- FOREIGN KEY (wish_id) REFERENCES wish(id),
-- FOREIGN KEY (wish_list_id) REFERENCES wish_list(id),
-- FOREIGN KEY (profile_id) REFERENCES profile(id)
-- ) AUTO_INCREMENT = 10000;