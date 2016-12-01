# --- !Ups

alter table activation add column price_paid decimal(10,2);

# --- !Downs

alter table activation drop column price_paid;
