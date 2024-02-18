create table users_passports
(
    id   serial primary key,
    user_id int references users(id) unique,
    passport_id int references passports(id) unique
);
