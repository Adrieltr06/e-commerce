-- V3__Create_Cart_and_Cart_Product_Tables.sql

-- Create the cart table
CREATE TABLE carts (
    id SERIAL PRIMARY KEY,
    account_id BIGINT,
    FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE
);

-- Create the cart_product join table to represent the many-to-many relationship
CREATE TABLE cart_products (
    cart_id BIGINT,
    product_id BIGINT,
    quantity INT NOT NULL,
    PRIMARY KEY (cart_id, product_id),
    FOREIGN KEY (cart_id) REFERENCES cart(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);
