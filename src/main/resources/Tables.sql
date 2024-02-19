
DROP TABLE PRODUCT IF EXISTS;
DROP TABLE SELLERS IF EXISTS;

CREATE TABLE SELLERS (name varchar(255) primary key);

CREATE TABLE PRODUCT (
productId long,
productName varchar(255),
 productPrice double,
 sellerName varchar(255) references SELLERS(name));

