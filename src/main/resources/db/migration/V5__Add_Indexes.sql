-- V5__Add_Indexes.sql

-- Add index on account name
CREATE INDEX idx_account_name ON account(name);

-- Add index on product name
CREATE INDEX idx_product_name ON product(name);

-- Add index on cart account_id
CREATE INDEX idx_cart_account_id ON cart(account_id);

-- Add index on orders account_id
CREATE INDEX idx_orders_account_id ON orders(account_id);
