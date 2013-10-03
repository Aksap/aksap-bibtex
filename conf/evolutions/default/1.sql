# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table article (
  id                        varchar(255) not null,
  author                    varchar(255),
  title                     varchar(255),
  journal                   varchar(255),
  year                      integer,
  constraint pk_article primary key (id))
;

create table book (
  id                        varchar(255) not null,
  author                    varchar(255),
  title                     varchar(255),
  publisher                 varchar(255),
  year                      integer,
  constraint pk_book primary key (id))
;

create table inproceedings (
  id                        varchar(255) not null,
  author                    varchar(255),
  title                     varchar(255),
  booktitle                 varchar(255),
  year                      integer,
  constraint pk_inproceedings primary key (id))
;

create table misc (
  id                        varchar(255) not null,
  author                    varchar(255),
  title                     varchar(255),
  journal                   varchar(255),
  year                      integer,
  constraint pk_misc primary key (id))
;

create sequence article_seq;

create sequence book_seq;

create sequence inproceedings_seq;

create sequence misc_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists article;

drop table if exists book;

drop table if exists inproceedings;

drop table if exists misc;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists article_seq;

drop sequence if exists book_seq;

drop sequence if exists inproceedings_seq;

drop sequence if exists misc_seq;

