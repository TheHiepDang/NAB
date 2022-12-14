CREATE TABLE PRODUCT (
    ID LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
    CATEGORY VARCHAR(255) NOT NULL,
    NAME VARCHAR(255) NOT NULL,
    PRICE DECIMAL(15,2) NOT NULL,
    BRAND VARCHAR(255) NOT NULL,
    COLOUR VARCHAR(25) NULL,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IDX_PROD_NAME ON PRODUCT(NAME);
CREATE VIEW V_PRODUCT_PRICE
AS
SELECT ID, PRICE FROM PRODUCT;

CREATE TABLE INVENTORY (
    ID LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
    PRODUCT_ID LONG NOT NULL,
    QUANTITY INT NOT NULL DEFAULT 0,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    foreign key (PRODUCT_ID) references PRODUCT(ID)
);