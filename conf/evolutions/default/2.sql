# --- !Ups

alter table activation add column price_paid decimal(10,2);
update activation a set a.price_paid = (select m.use_price from machine m where m.id = a.machine_id);

# --- !Downs

alter table activation drop column price_paid;
