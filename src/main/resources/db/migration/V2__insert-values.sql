INSERT INTO users (user_name, email, password, phone) VALUES ('Ann', 'ann@gmail.com', '$2a$10$9teQIvuui0beXwbF/471he21dNZWXBvDLtJtTpF4QRQITWW2LWQAG', '+71111111111');
INSERT INTO users (user_name, email, password, phone) VALUES ('Milana', 'milana@gmail.com', '$2a$10$/5XG65.Q2XiBt7NgJxMuV.4KQ.uANMtWzpifAcKJf0Wuq8RuZZhJG', '+72222222222');

INSERT INTO brand (name, country) VALUES ('Clinique', 'France');
INSERT INTO brand (name, country) VALUES ('Loreal', 'France');
INSERT INTO brand (name, country) VALUES ('Art Visage', 'Russia');
INSERT INTO brand (name, country) VALUES ('Holika holika', 'Korea');
INSERT INTO brand (name, country) VALUES ('Tom Ford', 'USA');
INSERT INTO brand (name, country) VALUES ('Rad', 'USA');
INSERT INTO brand (name, country) VALUES ('Nivea', 'Italy');
INSERT INTO brand (name, country) VALUES ('Organic shop', 'Russia');

INSERT INTO product (name, description, category, brand_id, price) VALUES ('Almost lipstick', 'Black Honey', 'makeup', 1, 2000);
INSERT INTO product (name, description, category, brand_id, price) VALUES ('Vitamino Color', 'Shampoo for colored hair, 300 мл', 'haircare', 2, 1170);
INSERT INTO product (name, description, category, brand_id, price) VALUES ('Lash Power Mascara', 'Dark Chocolate', 'makeup', 1, 1700);
INSERT INTO product (name, description, category, brand_id, price) VALUES ('Eyeshadow palette', 'Cool Universe', 'makeup', 3, 1100);
INSERT INTO product (name, description, category, brand_id, price) VALUES ('Waterproof Sun Cream', 'Aloe SPF50+/ РА ++++ 70 мл', 'sun_care', 4, 1800);
INSERT INTO product (name, description, category, brand_id, price) VALUES ('Lost cherry', 'Sweetness. The temptation. 50 мл', 'perfume', 5, 31500);
INSERT INTO product (name, description, category, brand_id, price) VALUES ('Miracle Touch', 'Оттенок 101, золотисто-бежевый', 'makeup', 3, 230);
INSERT INTO product (name, description, category, brand_id, price) VALUES ('Eye Eye Eye! Vinyl Eyeliner', 'Оттенок 004, Acid blue', 'makeup', 6, 450);
INSERT INTO product (name, description, category, brand_id, price) VALUES ('Cleansing Balm', 'Take The Day Off Cleansing Balm, 125 мл', 'skincare', 1, 2700);
INSERT INTO product (name, description, category, brand_id, price) VALUES ('KOSMA white plastic base', 'Набор пилок для ногтей, 5 шт', 'nail_care', 3, 150);
INSERT INTO product (name, description, category, brand_id, price) VALUES ('NIVEA Men', 'Крем для лица мужской интенсивно увлажняющий', 'men_cosmetics', 7, 235);
INSERT INTO product (name, description, category, brand_id, price) VALUES ('Sweet Vanilla', 'Гель для душа, 500 мл', 'organic', 8, 200);
INSERT INTO product (name, description, category, brand_id, price) VALUES ('#3 by Sergey Turchaninov', 'Кисть круглая крупная', 'other', 3, 1600);

INSERT INTO cart (total_price, user_id) VALUES (9850, 1);
INSERT INTO cart (total_price, user_id) VALUES (5100, 2);

INSERT INTO cart_item (cart_id, product_id, quantity) VALUES (1, 2, 5);
INSERT INTO cart_item (cart_id, product_id, quantity) VALUES (1, 1, 2);
INSERT INTO cart_item (cart_id, product_id, quantity) VALUES (2, 3, 3);

INSERT INTO orders (user_id, total_price, date, status) VALUES (1, 21680, '2024-01-05', 'completed');
INSERT INTO orders (user_id, total_price, date, status) VALUES (2, 2340, '2023-10-05', 'in_transit');
INSERT INTO orders (user_id, total_price, date, status) VALUES (1, 2000, '2024-03-27', 'delivered');

--INSERT INTO order_item (order_id, product_id, quantity) VALUES (1, 3, 10);
--INSERT INTO order_item (order_id, product_id, quantity) VALUES (1, 2, 4);
--INSERT INTO order_item (order_id, product_id, quantity) VALUES (2, 2, 2);
--INSERT INTO order_item (order_id, product_id, quantity) VALUES (3, 1, 1);

INSERT INTO order_item (order_id, product_name, total_price, quantity) VALUES (1, 'Lash Power Mascara', 17000, 10);
INSERT INTO order_item (order_id, product_name, total_price, quantity) VALUES (1, 'Vitamino Color', 4680, 4);
INSERT INTO order_item (order_id, product_name, total_price, quantity) VALUES (2, 'Vitamino Color', 2340, 2);
INSERT INTO order_item (order_id, product_name, total_price, quantity) VALUES (3, 'Almost lipstick', 2000, 1);
