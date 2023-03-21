create table request_archive
(
    id serial primary key,
    creation_date timestamp not null,
    latitude decimal(5,2) not null,
    longitude decimal(6,2) not null
)