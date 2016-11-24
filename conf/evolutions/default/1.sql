# --- !Ups

create table activation (
  id                            bigserial not null,
  user_id                       bigint,
  machine_id                    varchar(255),
  date                          timestamp,
  constraint uq_activation_machine_id unique (machine_id),
  constraint pk_activation primary key (id)
);

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

create table machine (
  id                            varchar(255) not null,
  laundry_id                    bigint,
  type                          varchar(6),
  use_price                     decimal(10,2),
  cycle_duration                integer,
  active_until                  timestamp,
  constraint ck_machine_type check (type in ('WASHER','DRYER','COMBO')),
  constraint pk_machine primary key (id)
);

create table payment (
  id                            bigserial not null,
  user_id                       bigint,
  value                         decimal(20,2),
  date                          timestamp,
  constraint uq_payment_user_id unique (user_id),
  constraint pk_payment primary key (id)
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
  role                          varchar(8),
  constraint ck_users_role check (role in ('CUSTOMER','ADMIN')),
  constraint pk_users primary key (id)
);

alter table activation add constraint fk_activation_user_id foreign key (user_id) references users (id) on delete restrict on update restrict;
create index ix_activation_user_id on activation (user_id);

alter table activation add constraint fk_activation_machine_id foreign key (machine_id) references machine (id) on delete restrict on update restrict;

alter table api_token add constraint fk_api_token_user_id foreign key (user_id) references users (id) on delete restrict on update restrict;

alter table machine add constraint fk_machine_laundry_id foreign key (laundry_id) references laundry (id) on delete restrict on update restrict;
create index ix_machine_laundry_id on machine (laundry_id);

alter table payment add constraint fk_payment_user_id foreign key (user_id) references users (id) on delete restrict on update restrict;

alter table request_log add constraint fk_request_log_user_id foreign key (user_id) references users (id) on delete restrict on update restrict;
create index ix_request_log_user_id on request_log (user_id);

alter table signup_token add constraint fk_signup_token_user_id foreign key (user_id) references users (id) on delete restrict on update restrict;


# --- !Downs

alter table if exists activation drop constraint if exists fk_activation_user_id;
drop index if exists ix_activation_user_id;

alter table if exists activation drop constraint if exists fk_activation_machine_id;

alter table if exists api_token drop constraint if exists fk_api_token_user_id;

alter table if exists machine drop constraint if exists fk_machine_laundry_id;
drop index if exists ix_machine_laundry_id;

alter table if exists payment drop constraint if exists fk_payment_user_id;

alter table if exists request_log drop constraint if exists fk_request_log_user_id;
drop index if exists ix_request_log_user_id;

alter table if exists signup_token drop constraint if exists fk_signup_token_user_id;

drop table if exists activation cascade;

drop table if exists api_token cascade;

drop table if exists laundry cascade;

drop table if exists machine cascade;

drop table if exists payment cascade;

drop table if exists request_log cascade;

drop table if exists signup_token cascade;

drop table if exists users cascade;

