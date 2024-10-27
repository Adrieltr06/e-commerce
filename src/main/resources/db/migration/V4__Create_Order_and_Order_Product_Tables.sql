-- V4__Create_Order_and_Order_Product_Tables.sql

-- Create the order table
CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    account_id BIGINT,
    status VARCHAR(50) DEFAULT 'Pending',
    total_amount DOUBLE PRECISION,
    total_quantity INT,
    FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE
);

-- Create the order_product join table to represent the many-to-many relationship
CREATE TABLE order_products (
    order_id BIGINT,
    product_id BIGINT,
    quantity INT NOT NULL,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);
