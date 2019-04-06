create table dialog (
id  bigserial not null,
address_id int8, sender_id int8,
primary key (id));

create table filedb (
id  bigserial not null,
data oid, file_name varchar(255),
file_type varchar(255),
primary key (id));

create table info (
id  bigserial not null,
text varchar(2048),
primary key (id));

create table message (
id  bigserial not null,
text varchar(255),
author_id int8,
dialog_mes_id int8,
primary key (id));

create table tel (
id  bigserial not null,
text varchar(255),
primary key (id));

create table trip (
id  bigserial not null,
beginning varchar(255),
date varchar(255),
finish varchar(255),
price varchar(255),
primary key (id));

create table user_role (
user_id int8 not null,
roles varchar(255));

create table users_trips (
user_id int8 not null,
trip_id int8 not null);

create table usr (
id  bigserial not null,
activation_code varchar(255),
active boolean not null,
avatar int8,
email varchar(255),
first_name varchar(255),
last_name varchar(255),
new_mes int4 not null,
password varchar(255),
telephone varchar(255),
username varchar(255),
primary key (id));

alter table if exists dialog add constraint FKsji9jdrqla9mxyeb175x05qgy foreign key (address_id) references usr;

alter table if exists dialog add constraint FKi8sp2hksjismwdmy8uhe3yxuq foreign key (sender_id) references usr;

alter table if exists message add constraint FKqhhiq2fjqs0a1cgrg9bueu7ab foreign key (author_id) references usr;

alter table if exists message add constraint FK25rebgkwcds4aivjllfk3n8e7 foreign key (dialog_mes_id) references dialog;

alter table if exists user_role add constraint FKfpm8swft53ulq2hl11yplpr5 foreign key (user_id) references usr;

alter table if exists users_trips add constraint FK1gtpsypr70c77rxd4n9y3d30h foreign key (trip_id) references trip;

alter table if exists users_trips add constraint FK9j1b7v5xi7jlwoxsxy57l2vuv foreign key (user_id) references usr;
