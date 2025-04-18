INSERT INTO users (user_name, email, password, phone) VALUES ('Ann', 'ann@gmail.com', '$2a$10$9teQIvuui0beXwbF/471he21dNZWXBvDLtJtTpF4QRQITWW2LWQAG', '1234567890');
INSERT INTO users (user_name, email, password, phone) VALUES ('Milana', 'milana@gmail.com', '$2a$10$/5XG65.Q2XiBt7NgJxMuV.4KQ.uANMtWzpifAcKJf0Wuq8RuZZhJG', '0987654321');

INSERT INTO brand (name, country) VALUES ('Clinique', 'France');
INSERT INTO brand (name, country) VALUES ('Loreal', 'France');
INSERT INTO brand (name, country) VALUES ('Art Visage', 'Russia');
INSERT INTO brand (name, country) VALUES ('Holika holika', 'Korea');
INSERT INTO brand (name, country) VALUES ('Tom Ford', 'USA');

INSERT INTO product (name, description, category, brand_id, price) VALUES ('lipstick', 'matt', 'makeup', 1, 2000);
INSERT INTO product (name, description, category, brand_id, price) VALUES ('cream', 'hand cream', 'skincare', 2, 300);
INSERT INTO product (name, description, category, brand_id, price) VALUES ('mascara', 'waterproof', 'makeup', 1, 1700);
INSERT INTO product (name, description, category, brand_id, price) VALUES ('eyeliner', 'blue neon', 'makeup', 3, 500);
INSERT INTO product (name, description, category, brand_id, price) VALUES ('cream', 'spf', 'sun_care', 4, 1800);
INSERT INTO product (name, description, category, brand_id, price) VALUES ('Lost cherry', 'sweet', 'perfume', 5, 31500);

INSERT INTO cart (total_price, user_id) VALUES (0, 1);
INSERT INTO cart (total_price, user_id) VALUES (0, 2);

INSERT INTO cart_item (cart_id, product_id, quantity) VALUES (1, 2, 5);
INSERT INTO cart_item (cart_id, product_id, quantity) VALUES (1, 1, 2);
INSERT INTO cart_item (cart_id, product_id, quantity) VALUES (2, 3, 3);

INSERT INTO orders (user_id, total_price, date, status) VALUES (1, 2300, '2024-01-05', 'completed');
INSERT INTO orders (user_id, total_price, date, status) VALUES (2, 5600, '2023-10-05', 'in_transit');
INSERT INTO orders (user_id, total_price, date, status) VALUES (1, 1990, '2024-03-27', 'delivered');

INSERT INTO order_item (order_id, product_id, quantity) VALUES (1, 3, 10);
INSERT INTO order_item (order_id, product_id, quantity) VALUES (1, 2, 4);
INSERT INTO order_item (order_id, product_id, quantity) VALUES (2, 2, 2);
INSERT INTO order_item (order_id, product_id, quantity) VALUES (3, 1, 1);
