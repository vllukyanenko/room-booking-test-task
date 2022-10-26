create table booking
(
    id        varchar(28)  not null primary key,
    created   datetime     not null,
    creator   varchar(255) not null,
    check_in  datetime     not null,
    check_out datetime     not null,
    room_id   varchar(255) not null,
    version   int          null
);

create table room
(
    id           varchar(28)  not null primary key,
    created      datetime     not null,
    creator      varchar(50)  not null,
    description  varchar(255) null,
    floor        varchar(50)  null,
    name         varchar(100) null,
    seats_amount int          null
);

create table user
(
    id         varchar(28)  not null primary key,
    created    datetime     not null,
    creator    varchar(100) not null,
    email      varchar(100) null,
    first_name varchar(100) null,
    last_name  varchar(100) null,
    password   varchar(255) null,
    status     varchar(20)  null
);

