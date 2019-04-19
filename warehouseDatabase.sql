drop database if exists warehouse;
CREATE DATABASE IF NOT EXISTS WAREHOUSE;

USE WAREHOUSE;

CREATE TABLE IF NOT EXISTS CLIENT_ 
(
	clientId int not null unique auto_increment,
    clientName varchar(50) not null,
    PRIMARY KEY (clientId)
);

CREATE TABLE IF NOT EXISTS PRODUCT
(
	productId int not null unique auto_increment,
    productName varchar(50) not null,
    price decimal(8, 2) not null,
    stock int not null,
    PRIMARY KEY (productId)
);

CREATE TABLE IF NOT EXISTS ORDER_
(
	orderId int not null unique auto_increment,
    productId int not null,
    clientId int not null,
    quantity int not null,
    PRIMARY KEY (orderId),
    FOREIGN KEY (clientId) REFERENCES CLIENT_(clientId),
    FOREIGN KEY (productId) REFERENCES PRODUCT(productId)
);