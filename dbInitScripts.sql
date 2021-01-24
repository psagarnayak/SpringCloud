TRUNCATE TABLE customerdb.customers;
TRUNCATE TABLE productdb.products;
TRUNCATE TABLE orderdb.orders;
TRUNCATE TABLE orderdb.order_items;

update orderdb.order_item_seq set next_val = 1;
update orderdb.order_seq set next_val = 1;
update customerdb.customer_seq set next_val = 6;
update productdb.product_seq set next_val = 6;

INSERT INTO customerdb.customers (id,balance,name,signup_date) VALUES
	 (1,1231.9,'Aaron','2021-01-25'),
	 (2,38245.8,'Billy','2021-01-25'),
	 (3,643.12,'Cindy','2021-01-25'),
	 (4,461.25,'Dexter','2021-01-25'),
	 (5,27.65,'Emily','2021-01-25');
	
INSERT INTO productdb.products (id,quant_avail,name,price) VALUES
	 (1,13,'Biscuits',32.5),
	 (2,26,'Chocolate',55.0),
	 (3,5,'Shoes',270.0),
	 (4,2,'Necklace',26517.0),
	 (5,6,'Flowers',98.0);


