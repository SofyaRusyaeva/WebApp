CREATE TABLE IF NOT EXISTS users (
    user_id SERIAL PRIMARY KEY,
    user_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(15),
    role VARCHAR(255) DEFAULT 'ROLE_USER' CHECK (role IN ('ROLE_USER', 'ROLE_ADMIN')),
    CONSTRAINT unique_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS brand (
    brand_id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    country VARCHAR(255) CHECK (country IN ('Russia', 'Belarus',
    'USA', 'China', 'France', 'Italy', 'Korea'))
);

  CREATE TABLE IF NOT EXISTS product (
      product_id SERIAL PRIMARY KEY,
      name VARCHAR(50) NOT NULL,
      description TEXT,
      category VARCHAR(255) CHECK (category IN ('skincare', 'makeup', 'haircare', 'perfume',
      'nail_care', 'sun_care', 'men_cosmetics', 'organic', 'other')),
      brand_id INT NOT NULL,
      price NUMERIC(10,2) NOT NULL,
      CONSTRAINT fk_brand_product FOREIGN KEY (brand_id) REFERENCES brand(brand_id)
  );

CREATE TABLE IF NOT EXISTS cart (
    cart_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL UNIQUE,
    total_price NUMERIC(10,2),
    CONSTRAINT fk_user_cart FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS orders (
    order_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    total_price NUMERIC(10,2) NOT NULL,
    date DATE NOT NULL,
    status VARCHAR(255) NOT NULL CHECK (status IN ('completed',
    'canceled', 'in_progress', 'in_transit', 'delivered', 'failed')),
    CONSTRAINT fk_user_order FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS order_item (
    order_item_id SERIAL PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT fk_order_orderitem FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    CONSTRAINT fk_product_orderitem FOREIGN KEY (product_id) REFERENCES product(product_id)
);

CREATE TABLE IF NOT EXISTS cart_item (
    cart_item_id SERIAL PRIMARY KEY,
    cart_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT fk_cart_cartitem FOREIGN KEY (cart_id) REFERENCES cart(cart_id) ON DELETE CASCADE,
    CONSTRAINT fk_product_cartitem FOREIGN KEY (product_id) REFERENCES product(product_id)
);

CREATE TABLE IF NOT EXISTS refresh_token (
    token_id SERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
)