CREATE TABLE PUBLIC.CUSTOMER(
  CUSTOMER_ID   INTEGER       NOT NULL PRIMARY KEY,
  FIRSTNAME     VARCHAR(256)  NOT NULL,
  LASTNAME      VARCHAR(256)  NOT NULL,
  EMAIL         VARCHAR(64)   NOT NULL
);