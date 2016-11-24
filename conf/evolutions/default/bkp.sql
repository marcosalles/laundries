# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

insert into users (name, email, password, verified, role) values ('Marco', 'm@m', '3C95BE91C0CE2240FC48FF38D0C14E9E5888E9AD', true, 'ADMIN');
insert into api_token (user_id, code) values (1, 'QWE1RTY2UIO3P4ASD5FGH6JKL7ZXC8VBN9M0');
insert into laundry (name, address) values ('Wash n go', 'Rua Vergueiro 3185');
insert into machine (id, laundry_id, type, use_price, cycle_duration) values ('a', 1, 'WASHER', 15.12, 5);
insert into machine (id, laundry_id, type, use_price, cycle_duration) values ('b', 1, 'DRYER', 18.87, 5);

# --- !Downs

