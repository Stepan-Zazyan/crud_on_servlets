create table users
(
    id   serial primary key,
    name varchar,
    login varchar unique,
    password varchar
);