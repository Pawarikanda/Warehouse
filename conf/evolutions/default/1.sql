# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table address (
  id                            bigint not null,
  warehouse_id                  bigint,
  street                        varchar(255),
  number                        varchar(255),
  postal_code                   varchar(255),
  city                          varchar(255),
  country                       varchar(255),
  constraint uq_address_warehouse_id unique (warehouse_id),
  constraint pk_address primary key (id)
);
create sequence address_seq;

create table product (
  id                            bigint not null,
  ean                           varchar(255),
  name                          varchar(255),
  description                   varchar(255),
  constraint pk_product primary key (id)
);
create sequence product_seq;

create table product_tag (
  product_id                    bigint not null,
  tag_tid                       bigint not null,
  constraint pk_product_tag primary key (product_id,tag_tid)
);

create table security_role (
  id                            bigint not null,
  name                          varchar(255),
  constraint pk_security_role primary key (id)
);
create sequence security_role_seq;

create table stock_item (
  id                            bigint not null,
  warehouse_id                  bigint,
  product_id                    bigint,
  quantity                      bigint,
  constraint pk_stock_item primary key (id)
);
create sequence stock_item_seq;

create table tag (
  tid                           bigint not null,
  name                          varchar(255),
  constraint pk_tag primary key (tid)
);
create sequence tag_seq;

create table user (
  id                            bigint not null,
  password                      varchar(255),
  username                      varchar(255),
  constraint pk_user primary key (id)
);
create sequence user_seq;

create table user_security_role (
  user_id                       bigint not null,
  security_role_id              bigint not null,
  constraint pk_user_security_role primary key (user_id,security_role_id)
);

create table user_user_permission (
  user_id                       bigint not null,
  user_permission_id            bigint not null,
  constraint pk_user_user_permission primary key (user_id,user_permission_id)
);

create table user_permission (
  id                            bigint not null,
  permission_value              varchar(255),
  constraint pk_user_permission primary key (id)
);
create sequence user_permission_seq;

create table warehouse (
  id                            bigint not null,
  address_id                    bigint,
  name                          varchar(255),
  constraint uq_warehouse_address_id unique (address_id),
  constraint pk_warehouse primary key (id)
);
create sequence warehouse_seq;

alter table address add constraint fk_address_warehouse_id foreign key (warehouse_id) references warehouse (id) on delete restrict on update restrict;

alter table product_tag add constraint fk_product_tag_product foreign key (product_id) references product (id) on delete restrict on update restrict;
create index ix_product_tag_product on product_tag (product_id);

alter table product_tag add constraint fk_product_tag_tag foreign key (tag_tid) references tag (tid) on delete restrict on update restrict;
create index ix_product_tag_tag on product_tag (tag_tid);

alter table stock_item add constraint fk_stock_item_warehouse_id foreign key (warehouse_id) references warehouse (id) on delete restrict on update restrict;
create index ix_stock_item_warehouse_id on stock_item (warehouse_id);

alter table stock_item add constraint fk_stock_item_product_id foreign key (product_id) references product (id) on delete restrict on update restrict;
create index ix_stock_item_product_id on stock_item (product_id);

alter table user_security_role add constraint fk_user_security_role_user foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_user_security_role_user on user_security_role (user_id);

alter table user_security_role add constraint fk_user_security_role_security_role foreign key (security_role_id) references security_role (id) on delete restrict on update restrict;
create index ix_user_security_role_security_role on user_security_role (security_role_id);

alter table user_user_permission add constraint fk_user_user_permission_user foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_user_user_permission_user on user_user_permission (user_id);

alter table user_user_permission add constraint fk_user_user_permission_user_permission foreign key (user_permission_id) references user_permission (id) on delete restrict on update restrict;
create index ix_user_user_permission_user_permission on user_user_permission (user_permission_id);

alter table warehouse add constraint fk_warehouse_address_id foreign key (address_id) references address (id) on delete restrict on update restrict;


# --- !Downs

alter table address drop constraint if exists fk_address_warehouse_id;

alter table product_tag drop constraint if exists fk_product_tag_product;
drop index if exists ix_product_tag_product;

alter table product_tag drop constraint if exists fk_product_tag_tag;
drop index if exists ix_product_tag_tag;

alter table stock_item drop constraint if exists fk_stock_item_warehouse_id;
drop index if exists ix_stock_item_warehouse_id;

alter table stock_item drop constraint if exists fk_stock_item_product_id;
drop index if exists ix_stock_item_product_id;

alter table user_security_role drop constraint if exists fk_user_security_role_user;
drop index if exists ix_user_security_role_user;

alter table user_security_role drop constraint if exists fk_user_security_role_security_role;
drop index if exists ix_user_security_role_security_role;

alter table user_user_permission drop constraint if exists fk_user_user_permission_user;
drop index if exists ix_user_user_permission_user;

alter table user_user_permission drop constraint if exists fk_user_user_permission_user_permission;
drop index if exists ix_user_user_permission_user_permission;

alter table warehouse drop constraint if exists fk_warehouse_address_id;

drop table if exists address;
drop sequence if exists address_seq;

drop table if exists product;
drop sequence if exists product_seq;

drop table if exists product_tag;

drop table if exists security_role;
drop sequence if exists security_role_seq;

drop table if exists stock_item;
drop sequence if exists stock_item_seq;

drop table if exists tag;
drop sequence if exists tag_seq;

drop table if exists user;
drop sequence if exists user_seq;

drop table if exists user_security_role;

drop table if exists user_user_permission;

drop table if exists user_permission;
drop sequence if exists user_permission_seq;

drop table if exists warehouse;
drop sequence if exists warehouse_seq;

