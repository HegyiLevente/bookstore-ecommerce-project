CREATE SEQUENCE IF NOT EXISTS product_seq;

CREATE TABLE IF NOT EXISTS product (
	id BIGINT NOT NULL DEFAULT NEXTVAL('product_seq'),
	name VARCHAR(255) NOT NULL,
	description VARCHAR(255) DEFAULT NULL,
	unit_price DECIMAL(13,2) NOT NULL,
	image_url VARCHAR(255) DEFAULT NULL,
	active BOOLEAN DEFAULT TRUE,
	units_in_stock BIGINT NOT NULL,
	date_created TIMESTAMPTZ NOT NULL,
	last_updated TIMESTAMPTZ NOT NULL,
	
	CONSTRAINT PK_product PRIMARY KEY(id)
);

CREATE SEQUENCE IF NOT EXISTS product_category_seq;

CREATE TABLE IF NOT EXISTS product_category (
	id BIGINT NOT NULL DEFAULT NEXTVAL('product_category_seq'),
	name VARCHAR(255) NOT NULL,
	
	CONSTRAINT PK_product_category PRIMARY KEY(id)
);

ALTER TABLE product 
ADD COLUMN product_category_id BIGINT NOT NULL;

ALTER TABLE product
ADD CONSTRAINT FK_product_productcategory FOREIGN KEY(product_category_id) REFERENCES product_category(id);

--list all type of constraints
SELECT
    tc.constraint_name, tc.table_name, kcu.column_name, 
    ccu.table_name AS foreign_table_name,
    ccu.column_name AS foreign_column_name 
FROM 
    information_schema.table_constraints AS tc 
    JOIN information_schema.key_column_usage AS kcu
      ON tc.constraint_name = kcu.constraint_name
    JOIN information_schema.constraint_column_usage AS ccu
      ON ccu.constraint_name = tc.constraint_name
WHERE constraint_type = 'FOREIGN KEY';





