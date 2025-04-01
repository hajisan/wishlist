-- ========================
-- INSERT INTO profile
-- ========================
INSERT INTO profile (id, name, birthday, email, username, password) VALUES
(6000, 'Nimaa Salami', '1992-06-30', 'nima.salami@example.com', 'nima75', 'NU2LMStf(S'),
(6001, 'Sofie Rytter', '1996-05-22', 'sofie.rytter@example.com', 'sofie48', '7WTXQmJf&A'),
(6002, 'Joakim Severinsen', '1999-11-26', 'joakim.severinsen@example.com', 'joakim65', '&8hWxSrphx');

-- ========================
-- INSERT INTO wish_list
-- ========================
INSERT INTO wish_list (id, name, description, profile_id) VALUES
(700, 'Nimas Fødselsdag', 'Mine fødselsdagsønsker', 6000),
(701, 'Nimas Træning', 'Ting jeg mangler til træning', 6000),
(702, 'Sofies Hverdag', 'Hverdags ønsker', 6001),
(703, 'Sofies Køkken', 'Ting jeg mangler til mit køkken', 6001),
(704, 'Joakims Tech', 'Kan aldrig få nok tech', 6002),
(705, 'Joakims Hjem', 'Ting jeg ønsker mig til mit hjem', 6002);

-- ========================
-- INSERT INTO wish
-- ========================
INSERT INTO wish (id, name, description, link, quantity, price, reserved, wish_list_id) VALUES
-- Nimas Fødselsdag (700)
(8000, 'Airfryer', 'Philips XXL til sund madlavning', 'https://www.elgiganten.dk/product/airfryer-philips-xxl', 1, 999.0, 0, 700),
(8001, 'Vægtløftningssko', 'Nike Romaleos str. 43', 'https://www.nike.com/dk/t/romaleos-4-weightlifting-shoes', 1, 1349.0, 0, 700),
(8002, 'Kaffekværn', 'Wilfa Svart elektrisk kværn', 'https://www.kitchentime.dk/p/svart-nymalt-kaffekvaern-cgws-130b', 1, 649.0, 0, 700),
(8003, 'Stavblender', 'Bosch stavblender sæt', 'https://www.power.dk/koekkenudstyr/stavblendere/bosch-msm66150-stavblender/p-234987', 1, 499.0, 0, 700),
(8004, 'Bluetooth-højtaler', 'JBL Flip 6 bærbar højtaler', 'https://www.elgiganten.dk/product/lyd-hi-fi/hoejtaler/jbl-flip-6-hoejtaler-sort', 1, 899.0, 0, 700),

-- Nimas Træning (701)
(8005, 'Pull-up bar', 'Til dørkarm', 'https://www.fitnessengros.dk/traeningsudstyr/pull-up-bar', 1, 299.0, 0, 701),
(8006, 'Kettlebell', '16 kg sort jern', 'https://www.fitnessguru.com/da/kettlebell-16kg', 1, 399.0, 0, 701),
(8007, 'Træningsmåtte', 'Tykkere model, 10mm', NULL, 1, 249.0, 0, 701),
(8008, 'Foam roller', 'Til restitution', 'https://www.apuls.dk/abilica-foamroller', 1, 199.0, 0, 701),
(8009, 'Træningshandsker', 'Læder, str. M', NULL, 1, 179.0, 0, 701),

-- Sofies Hverdag (702)
(8010, 'Termokop', 'Contigo 470 ml', 'https://www.kopk.dk/products/contigo-west-loop-termokrus-470-ml', 1, 199.0, 0, 702),
(8011, 'Planner', '2025 med ugevisning', 'https://www.kalenderspecialisten.dk/planner-2025', 1, 159.0, 0, 702),
(8012, 'Puslespil', '1000 brikker, landskabsmotiv', 'https://www.br.dk/produkter/eeboo-puslespil-1000-brikker-foelg-naturens-rytme/200150620/', 1, 129.0, 0, 702),
(8013, 'Lysestager', 'Sæt med 2 i glas', NULL, 1, 179.0, 0, 702),
(8014, 'Forklæde', 'Lærred med lommer', 'https://www.hay.dk/da/accessories/kitchen/textiles/apron', 1, 149.0, 0, 702),

-- Sofies Køkken (703)
(8015, 'Skærebræt', 'Træ, med saftrille', 'https://www.ikea.com/dk/da/p/proppmatt-skaerebraet-bambus-20309829/', 1, 199.0, 0, 703),
(8016, 'Køkkenvægt', 'Digital, rustfrit stål', 'https://www.wilfa.dk/produkt/koekkenvaegt-wsfb-100s/', 1, 249.0, 0, 703),
(8017, 'Morter', 'Granit, medium', NULL, 1, 229.0, 0, 703),
(8018, 'Spiseskeer', 'Rustfri, sæt af 6', NULL, 1, 179.0, 0, 703),
(8019, 'Kagefad', 'Hvid keramik', NULL, 1, 299.0, 0, 703),

-- Joakims Tech (704)
(8020, 'Powerbank', 'Anker 20000mAh', 'https://www.anker.com/eu-en/products/a1287', 1, 379.0, 0, 704),
(8021, 'Skærm', '24'' Full HD IPS', 'https://www.dustin.dk/product/5011232139/dell-24-monitor---s2421hn', 1, 1199.0, 0, 704),
(8022, 'Mekanisk tastatur', 'Keychron K2 RGB', 'https://www.keychron.com/products/keychron-k2-wireless-mechanical-keyboard', 1, 799.0, 0, 704),
(8023, 'Mussemåtte', 'XL med LED', NULL, 1, 229.0, 0, 704),
(8024, 'USB-hub', '7 porte, USB 3.0', NULL, 1, 189.0, 0, 704),

-- Joakims Hjem (705)
(8025, 'Gulvtæppe', 'Blødt, 160x230 cm', 'https://www.jysk.dk/gulve/taepper/stuetaepper/stuetaeppe-kongstrup-160x230-blaa', 1, 999.0, 0, 705),
(8026, 'Plante', 'Monstera i potte', 'https://www.plantorama.dk/planter/stueplanter/gronne-planter/monstera-deliciosa', 1, 199.0, 0, 705),
(8027, 'Bogreol', 'IKEA Billy, hvid', 'https://www.ikea.com/dk/da/p/billy-bogreol-hvid-00263850/', 1, 499.0, 0, 705),
(8028, 'Lysguirlande', 'LED, 5 meter', NULL, 1, 149.0, 0, 705),
(8029, 'Pude', 'Fløjl, grøn', NULL, 1, 99.0, 0, 705);