--DROP SCHEMA IF EXISTS h2_wish_list;
CREATE SCHEMA IF NOT EXISTS h2_wish_list;
USE h2_wish_list;

DROP TABLE IF EXISTS wish;
DROP TABLE IF EXISTS wish_list;
DROP TABLE IF EXISTS profile;

CREATE TABLE profile
(
    id       INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(225),
    birthday DATE,
    email    VARCHAR(225) UNIQUE,
    username VARCHAR(225) UNIQUE,
    password VARCHAR(255)
);

CREATE TABLE wish_list
(
    id          INT NOT NULL AUTO_INCREMENT,
    name        VARCHAR(225) UNIQUE,
    description VARCHAR(225),
    profile_id  INT,
    PRIMARY KEY (id),
    FOREIGN KEY (profile_id) REFERENCES profile (id) ON DELETE CASCADE
);

CREATE TABLE wish
(
    id           INT NOT NULL AUTO_INCREMENT,
    name         VARCHAR(225),
    description  VARCHAR(225),
    link         VARCHAR(225),
    quantity     INT,
    price        DOUBLE,
    --reserved     TINYINT(1), -- 0 = ikke reserveret, 1 = reserveret
    wish_list_id INT,
    PRIMARY KEY (id),
    FOREIGN KEY (wish_list_id) REFERENCES wish_list (id) ON DELETE CASCADE
);

INSERT INTO profile (name, birthday, email, username, password)
VALUES ('Arne', '1995-12-12', 'Test1@example.com', 'test1', '12345678'),
       ('Birger', '2000-01-01', 'Test2@example.com', 'test2', 'qwertyui');

INSERT INTO wish_list (name, description, profile_id)
VALUES ('Arnes Liste', 'Ting til Arne', 1),
       ('Birgers Liste', 'Birgers ønsker', 2);

INSERT INTO wish (name, description, link, quantity, price, wish_list_id)
VALUES ('Arnes Yndlingsairfryer', 'Ninja Airfryer',
        'https://www.elgiganten.dk/product/hjem-rengoring-kokkenudstyr/kokkenudstyr/airfryer/ninja-airfryer-foodi-dual-zone-af300eu/244981?gStoreCode=3074&gQT=1',
        1, 1329.0, 1),
       ('Arnes 85 tommers fladskærm med Energimærke G', 'Samsung 4K Smart TV',
        'https://www.elgiganten.dk/product/tv-lyd-smart-home/tv-tilbehor/tv/samsung-85-du8075-4k-smart-tv-2024/763324',
        2, 8888.0, 1),
       ('Birgers Lurpak', 'Lurpak Smørbar (Saltet)', 'https://www.nemlig.com/smoerbar-saltet-104373', 1, 18.0, 2),
       ('Birgers Spisepinde', 'Trebent bambusspisepinde fra IKEA',
        'https://www.ikea.com/dk/da/p/trebent-spisepinde-4-saet-bambus-90342971/?utm_source=google&utm_medium=surfaces&utm_campaign=shopping_feed&utm_content=free_google_shopping_clicks_Eating&gStoreCode=686&gQT=1',
        1, 15.0, 2);
