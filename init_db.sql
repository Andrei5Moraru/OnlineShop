-- C:/Users/spafi/IdeaProjects/codecool-shop-2-java-Spafi/init_db.sql

DROP TABLE IF EXISTS public.supplier CASCADE;
create table supplier
(
    id          serial  not null
        constraint supplier_pk
            primary key,
    name        varchar not null,
    description varchar not null
);

alter table supplier
    owner to postgres;

create
    unique index supplier_id_uindex
    on supplier (id);

DROP TABLE IF EXISTS public.product_category CASCADE;
create table product_category
(
    id          serial  not null
        constraint product_category_pk
            primary key,
    name        varchar,
    department varchar,
    description varchar not null
);

alter table product_category
    owner to postgres;

create
    unique index product_category_id_uindex
    on product_category (id);



DROP TABLE IF EXISTS public.product CASCADE;
create table product
(
    id                  serial           not null
        constraint product_pk
            primary key,
    name                varchar          not null,
    description         varchar          not null,
    default_price       double precision not null,
    currency            varchar          not null,
    product_category_id integer          not null
        constraint product_product_category_id_fk
            references product_category
            on update cascade on delete cascade,
    supplier_id         integer          not null
        constraint product_supplier_id_fk
            references supplier
            on update cascade on delete cascade,
    amount              integer          not null
);

alter table product
    owner to postgres;

create
    unique index product_id_uindex
    on product (id);

DROP TABLE IF EXISTS public.users CASCADE;
create table users
(
    id       serial  not null
        constraint user_pk
            primary key,
    name     varchar not null,
    email    varchar not null,
    password varchar not null
);

alter table users
    owner to postgres;

create
    unique index user_id_uindex
    on users (id);

DROP TABLE IF EXISTS public.cart CASCADE;
create table cart
(
    id      serial  not null
        constraint cart_pk
            primary key,
    user_id integer not null
        constraint cart_user_id_fk
            references users
            on delete cascade
);

create
    unique index cart_id_uindex
    on cart (id);


DROP TABLE IF EXISTS public.cart_items CASCADE;
create table cart_items
(
    cart_id    integer not null
        constraint cart_items_cart_id_fk
            references cart
            on delete cascade,
    product_id integer
        constraint cart_items_product_id_fk
            references product
            on delete cascade,
    quantity   integer
);


alter table cart_items
    owner to postgres;

DROP TABLE IF EXISTS public.country CASCADE;
create table country
(
    id   serial  not null
        constraint country_pk
            primary key,
    name varchar not null
);

alter table country
    owner to postgres;

create
    unique index country_id_uindex
    on country (id);

DROP TABLE IF EXISTS public.state CASCADE;
create table state
(
    id   serial not null
        constraint state_pk
            primary key,
    name varchar
);

alter table state
    owner to postgres;

create
    unique index state_id_uindex
    on state (id);

DROP TABLE IF EXISTS public.billing_address CASCADE;
create table billing_address
(
    id         serial  not null
        constraint billing_address_pk
            primary key,
    user_id    integer not null
        constraint billing_address_user_id_fk
            references users
            on update cascade on delete cascade,
    first_name varchar not null,
    last_name  varchar not null,
    email      varchar,
    address    varchar not null,
    country_id integer not null
        constraint billing_address_country_id_fk
            references country
            on update cascade on delete cascade,
    state_id   integer not null
        constraint billing_address_state_id_fk
            references state
            on update cascade on delete cascade,
    zip_code   varchar not null
);


create
    unique index billing_address_id_uindex
    on billing_address (id);

DROP TABLE IF EXISTS public."order" CASCADE;
create table "order"
(
    id                  serial  not null
        constraint order_pk
            primary key,
    billing_address_id  integer not null
        constraint order_billing_address_id_fk
            references billing_address
            on update cascade on delete cascade,
    user_id             integer
        constraint order_user_id_fk
            references users
            on delete cascade,
    total_price         double precision,
    order_date          timestamp,
    shipping_address_id integer not null
        constraint order_shipping_address_id_fk
            references shipping_address
            on delete cascade
);



create
    unique index order_id_uindex
    on "order" (id);


DROP TABLE IF EXISTS public.order_products CASCADE;
create table order_products
(
    order_id   int not null
        constraint order_products_order_id_fk
            references "order"
            on delete cascade,
    product_id int not null
        constraint order_products_product_id_fk
            references product
            on delete cascade,
    quantity   int not null
);

DROP TABLE IF EXISTS public.shipping_address CASCADE;
create table shipping_address
(
    id         serial  not null
        constraint shipping_address_pk
            primary key,
    user_id    int
        constraint shipping_address_user_id_fk
            references users
            on delete cascade,
    first_name varchar,
    last_name  varchar,
    email      varchar,
    address    varchar,
    country_id int     not null
        constraint shipping_address_country_id_fk
            references country
            on delete cascade,
    state_id   int     not null
        constraint shipping_address_state_id_fk
            references state
            on delete cascade,
    zip_code   varchar not null
);
