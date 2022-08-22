insert into PRODUCT (ID, CATEGORY,
    NAME,
    PRICE,
    BRAND,
    COLOUR)
values
(1,'CAT1', 'PROD1', 152.23, 'BRAND1', 'RED'),
(2,'CAT1', 'PROD2', 43.23, 'BRAND2', 'GREEN'),
(3,'CAT2', 'PROD3', 55.3, 'BRAND1', 'BLUE'),
(4,'CAT2', 'PROD4', 2.23, 'BRAND2', 'WHITE');

insert into INVENTORY (product_id, quantity) VALUES (1,10),(2,10),(3,10),(4,10);