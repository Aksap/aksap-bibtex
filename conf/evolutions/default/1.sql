# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table inproceedings (
  id                        integer not null,
  author                    varchar(255),
  title                     varchar(255),
  booktitle                 varchar(255),
  year                      integer,
  constraint pk_inproceedings primary key (id))
;

create sequence inproceedings_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists inproceedings;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists inproceedings_seq;

