/*
 * Copyright (c) IInfo 2022.
 */

create table if not exists users
(
    id              int unsigned auto_increment
        primary key,
    name            varchar(15)                           not null,
    passwd          char(32)                              not null,
    email           varchar(40)                           not null,
    last_login_time timestamp default current_timestamp() not null,
    create_time     timestamp default current_timestamp() not null,
    constraint users_email_uindex
        unique (email),
    constraint users_name_uindex
        unique (name)
);

