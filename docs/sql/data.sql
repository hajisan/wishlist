-- ========================
-- INSERT INTO profile
-- ========================
INSERT INTO profile (id, name, birthday, email, user_name, password) VALUES
(6000, 'Nimaa Salami', '1992-06-30', 'nima.salami@example.com', 'nima75', 'NU2LMStf(S'),
(6001, 'Sofie Rytter', '1996-05-22', 'sofie.rytter@example.com', 'sofie48', '7WTXQmJf&A'),
(6002, 'Joakim Severinsen', '1999-11-26', 'joakim.severinsen@example.com', 'joakim65', '&8hWxSrphx');

-- ========================
-- INSERT INTO wish_list
-- ========================
-- Nima's ønskelister
INSERT INTO wish_list (id, name, description, profile_id) VALUES
(700, 'Nimas Fødselsdag', 'Mine fødselsdagsønsker', 6000),
(701, 'Nimas Træning', 'Ting jeg mangler til træning', 6000),

-- Sofie's ønskelister
(702, 'Sofies Hverdag', 'Hverdags ønsker', 6001),
(703, 'Sofies Køkken', 'Ting jeg mangler til mit køkken', 6001),

-- Joakim's ønskelister
(704, 'Joakims Tech', 'Kan aldrig få nok tech', 6002),
(705, 'Joakims Hjem', 'Ting jeg ønsker mig til mit hjem', 6002);

-- ========================
-- INSERT INTO wish
-- ========================

-- Nimas Fødselsdag (700)
INSERT INTO wish (id, name, description, price, quantity, reserved, wish_list_id) VALUES
(8000, 'Airfryer', 'Philips XXL til sund madlavning', 999.0, 1, 0, 700),
(8001, 'Vægtløftningssko', 'Nike Romaleos str. 43', 1349.0, 1, 0, 700),
(8002, 'Kaffekværn', 'Wilfa Svart elektrisk kværn', 649.0, 1, 0, 700),
(8003, 'Stavblender', 'Bosch stavblender sæt', 499.0, 1, 0, 700),
(8004, 'Bluetooth-højtaler', 'JBL Flip 6 bærbar højtaler', 899.0, 1, 0, 700),

-- Nimas Træning (701)
(8005, 'Pull-up bar', 'Til dørkarm', 299.0, 1, 0, 701),
(8006, 'Kettlebell', '16 kg sort jern', 399.0, 1, 0, 701),
(8007, 'Træningsmåtte', 'Tykkere model, 10mm', 249.0, 1, 0, 701),
(8008, 'Foam roller', 'Til restitution', 199.0, 1, 0, 701),
(8009, 'Træningshandsker', 'Læder, str. M', 179.0, 1, 0, 701),

-- Sofies Hverdag (702)
(8010, 'Termokop', 'Contigo 470 ml', 199.0, 1, 0, 702),
(8011, 'Planner', '2025 med ugevisning', 159.0, 1, 0, 702),
(8012, 'Puslespil', '1000 brikker, landskabsmotiv', 129.0, 1, 0, 702),
(8013, 'Lysestager', 'Sæt med 2 i glas', 179.0, 1, 0, 702),
(8014, 'Forklæde', 'Lærred med lommer', 149.0, 1, 0, 702),

-- Sofies Køkken (703)
(8015, 'Skærebræt', 'Træ, med saftrille', 199.0, 1, 0, 703),
(8016, 'Køkkenvægt', 'Digital, rustfrit stål', 249.0, 1, 0, 703),
(8017, 'Morter', 'Granit, medium', 229.0, 1, 0, 703),
(8018, 'Spiseskeer', 'Rustfri, sæt af 6', 179.0, 1, 0, 703),
(8019, 'Kagefad', 'Hvid keramik', 299.0, 1, 0, 703),

-- Joakims Tech (704)
(8020, 'Powerbank', 'Anker 20000mAh', 379.0, 1, 0, 704),
(8021, 'Skærm', '24'' Full HD IPS', 1199.0, 1, 0, 704),
(8022, 'Mekanisk tastatur', 'Keychron K2 RGB', 799.0, 1, 0, 704),
(8023, 'Mussemåtte', 'XL med LED', 229.0, 1, 0, 704),
(8024, 'USB-hub', '7 porte, USB 3.0', 189.0, 1, 0, 704),

-- Joakims Hjem (705)
(8025, 'Gulvtæppe', 'Blødt, 160x230 cm', 999.0, 1, 0, 705),
(8026, 'Plante', 'Monstera i potte', 199.0, 1, 0, 705),
(8027, 'Bogreol', 'IKEA Billy, hvid', 499.0, 1, 0, 705),
(8028, 'Lysguirlande', 'LED, 5 meter', 149.0, 1, 0, 705),
(8029, 'Pude', 'Fløjl, grøn', 99.0, 1, 0, 705);

-- ========================
-- INSERT INTO reservation
-- ========================

-- Joakim reserverer 2 ønsker fra Nima
INSERT INTO reservation (name, wish_id, wish_list_id, profile_id) VALUES
('Joakim', 8000, 700, 6000), -- Airfryer
('Joakim', 8005, 701, 6000); -- Pull-up bar

-- Nima reserverer 2 ønsker fra Sofie
INSERT INTO reservation (name, wish_id, wish_list_id, profile_id) VALUES
('Nima', 8010, 702, 6001), -- Termokop
('Nima', 8014, 703, 6001); -- Skærebræt

-- Sofie reserverer 2 ønsker fra Joakim
INSERT INTO reservation (name, wish_id, wish_list_id, profile_id) VALUES
('Sofie', 8020, 704, 6002), -- Powerbank
('Sofie', 8025, 705, 6002); -- Gulvtæppe

-- Anonyme reservationer
INSERT INTO reservation (name, wish_id, wish_list_id, profile_id) VALUES
(NULL, 8002, 700, 6000),  -- Kaffekværn (Nima)
(NULL, 8017, 703, 6001),  -- Morter (Sofie)
(NULL, 8022, 704, 6002);  -- Mekanisk tastatur (Joakim)