create extension if not exists "uuid-ossp";

CREATE TABLE menu_items (
    menu_id        UUID PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    restaurant_id  UUID             NOT NULL,
    menu_name      VARCHAR(255)     NOT NULL,
    description    TEXT,
    price          NUMERIC(10, 2)   NOT NULL,
    category       VARCHAR(255),
    available      BOOLEAN          NOT NULL DEFAULT TRUE,
    menu_image_url VARCHAR(255)
);


CREATE TABLE group_food_orders (
    group_food_order_id            UUID PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    group_food_order_create_time   TIMESTAMP        NOT NULL default current_timestamp,
    group_food_order_delivery_time TIMESTAMP,
    delivery_location              VARCHAR(50),
    delivery_fee                   float,
    status                         VARCHAR(50)      NOT NULL
);

CREATE TABLE order_items (
    order_item_id       UUID PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    group_food_order_id UUID             NOT NULL references group_food_orders,
    user_id             UUID             NOT NULL,
    restaurant_id       UUID             NOT NULL,
    payment_status      VARCHAR(50)      NOT NULL,
    order_created_time  TIMESTAMP        NOT NULL default current_timestamp
);

CREATE TABLE order_details (
    order_detail_id     UUID NOT NULL PRIMARY KEY,
    order_item_id       UUID NOT NULL REFERENCES order_items,
    menu_id             UUID NOT NULL REFERENCES menu_items,
    order_item_quantity NUMERIC
);