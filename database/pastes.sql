/*
 * Copyright (c) IInfo 2022.
 */

create table if not exists pastes
(
    id          int unsigned                           not null
        primary key,
    uid         int unsigned                           null,
    title       varchar(15)                            null,
    is_private  tinyint(1) default 0                   not null,
    expire_time timestamp  default current_timestamp() not null,
    passwd      char(32)                               null,
    paste       text                                   not null,
    create_time timestamp  default current_timestamp() not null,
    type        varchar(15)                            not null
);

create index if not exists pastes_expire_time_index
    on pastes (expire_time);

create index if not exists pastes_uid_index
    on pastes (uid);

