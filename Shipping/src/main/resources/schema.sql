CREATE TABLE SHIPPING ( 
ShippingId BIGINT AUTO_INCREMENT,  
OrderId BIGINT NOT NULL ,
OrderCreationDate DATE,
UserAddress VARCHAR(100),
OrderPrice FLOAT
);  