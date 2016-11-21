# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table api_token (
  id                            bigserial not null,
  user_id                       bigint,
  code                          varchar(255),
  constraint uq_api_token_user_id unique (user_id),
  constraint pk_api_token primary key (id)
);

create table laundry (
  id                            bigserial not null,
  name                          varchar(255),
  address                       varchar(255),
  constraint pk_laundry primary key (id)
);

create table request_log (
  id                            bigserial not null,
  user_id                       bigint,
  ip                            varchar(255),
  uri                           varchar(255),
  date                          timestamp,
  constraint pk_request_log primary key (id)
);

create table signup_token (
  id                            bigserial not null,
  user_id                       bigint,
  code                          varchar(255),
  constraint uq_signup_token_user_id unique (user_id),
  constraint pk_signup_token primary key (id)
);

create table users (
  id                            bigserial not null,
  name                          varchar(255),
  email                         varchar(255),
  password                      varchar(255),
  verified                      boolean,
  role                          integer,
  constraint ck_users_role check (role in (0,1)),
  constraint pk_users primary key (id)
);

alter table api_token add constraint fk_api_token_user_id foreign key (user_id) references users (id) on delete restrict on update restrict;

alter table request_log add constraint fk_request_log_user_id foreign key (user_id) references users (id) on delete restrict on update restrict;
create index ix_request_log_user_id on request_log (user_id);

alter table signup_token add constraint fk_signup_token_user_id foreign key (user_id) references users (id) on delete restrict on update restrict;


# --- !Downs

alter table if exists api_token drop constraint if exists fk_api_token_user_id;

alter table if exists request_log drop constraint if exists fk_request_log_user_id;
drop index if exists ix_request_log_user_id;

alter table if exists signup_token drop constraint if exists fk_signup_token_user_id;

drop table if exists api_token cascade;

drop table if exists laundry cascade;

drop table if exists request_log cascade;

drop table if exists signup_token cascade;

drop table if exists users cascade;

