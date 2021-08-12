#!/usr/bin/env bash
psql "postgres://$POSTGRES_USER:$POSTGRES_PASSWORD@$POSTGRES_HOST/$POSTGRES_DB?sslmode=disable" <<-EOSQL


-- SCHEMA
CREATE SCHEMA IF NOT EXISTS pizzaservice AUTHORIZATION pizzaservice;


-- SEQUENCES

CREATE SEQUENCE pizzaservice.sq_order INCREMENT BY 1 MINVALUE 1 NO MAXVALUE START WITH 1 CACHE 1 NO CYCLE;
CREATE SEQUENCE pizzaservice.sq_order_history INCREMENT BY 1 MINVALUE 1 NO MAXVALUE START WITH 1 CACHE 1 NO CYCLE;


-- TABELAS

CREATE TABLE pizzaservice.orders (
    order_id NUMERIC(10, 0) NOT NULL,
	order_requested_in TIMESTAMP NOT NULL,
	order_description VARCHAR(256) NOT NULL,
	order_status VARCHAR(128) NOT NULL,
	order_last_update TIMESTAMP NOT NULL
);

CREATE TABLE pizzaservice.orders_history (
    order_history_id NUMERIC(10, 0) NOT NULL,
	order_history_status VARCHAR(128) NOT NULL,
	order_history_updated_at TIMESTAMP NOT NULL,
	order_id NUMERIC(10, 0) NOT NULL
);


-- INDICES

CREATE UNIQUE INDEX order_pk ON pizzaservice.orders (order_id ASC);

CREATE UNIQUE INDEX order_history_pk ON pizzaservice.orders_history (order_history_id ASC);


-- CONSTRAINTS PRIMARY KEY

ALTER TABLE
    pizzaservice.orders
ADD
    CONSTRAINT order_pk PRIMARY KEY USING INDEX order_pk;

ALTER TABLE
    pizzaservice.orders_history
ADD
    CONSTRAINT order_history_pk PRIMARY KEY USING INDEX order_history_pk;


-- CONSTRAINTS FORIGN KEY

ALTER TABLE pizzaservice.orders_history 
ADD CONSTRAINT order_fk FOREIGN KEY (order_id)
REFERENCES pizzaservice.orders (order_id)
NOT DEFERRABLE INITIALLY IMMEDIATE;


EOSQL