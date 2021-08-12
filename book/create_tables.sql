create table author (id bigint not null auto_increment, address varchar(255), name varchar(255), phone varchar(255), book_id bigint, primary key (id)) engine=InnoDB;
create table book (id bigint not null auto_increment, image longblob, price varchar(255), title varchar(255), year varchar(255), author_id bigint, category_id bigint, publisher_id bigint, primary key (id)) engine=InnoDB;
create table category (id bigint not null auto_increment, title varchar(255), primary key (id)) engine=InnoDB;
create table publisher (id bigint not null auto_increment, address varchar(255), name varchar(255), phone varchar(255), book_id bigint, primary key (id)) engine=InnoDB;
create table quantity (id bigint not null auto_increment, amount varchar(255), primary key (id)) engine=InnoDB;
create table shop (id bigint not null auto_increment, address varchar(255), name varchar(255), phone varchar(255), book_id bigint, quantity_id bigint, primary key (id)) engine=InnoDB;
alter table author add constraint FKqi5nll4mal57ohohlv5g0qlv2 foreign key (book_id) references book (id);
alter table book add constraint FKklnrv3weler2ftkweewlky958 foreign key (author_id) references author (id);
alter table book add constraint FKam9riv8y6rjwkua1gapdfew4j foreign key (category_id) references category (id);
alter table book add constraint FKgtvt7p649s4x80y6f4842pnfq foreign key (publisher_id) references publisher (id);
alter table publisher add constraint FKnl0jbqw2v1vtnikc5u2uxrmmu foreign key (book_id) references book (id);
alter table shop add constraint FK3ystvlh74rsr5ra89h63ohpvj foreign key (book_id) references book (id);
alter table shop add constraint FKnuhy6jle5wtdxxq3bjtslylt4 foreign key (quantity_id) references quantity (id);
create table author (id bigint not null auto_increment, address varchar(255), name varchar(255), phone varchar(255), book_id bigint, primary key (id)) engine=InnoDB
create table book (id bigint not null auto_increment, image longblob, price varchar(255), title varchar(255), year varchar(255), author_id bigint, category_id bigint, publisher_id bigint, primary key (id)) engine=InnoDB
create table category (id bigint not null auto_increment, title varchar(255), primary key (id)) engine=InnoDB
create table publisher (id bigint not null auto_increment, address varchar(255), name varchar(255), phone varchar(255), book_id bigint, primary key (id)) engine=InnoDB
create table quantity (id bigint not null auto_increment, amount varchar(255), primary key (id)) engine=InnoDB
create table shop (id bigint not null auto_increment, address varchar(255), name varchar(255), phone varchar(255), book_id bigint, quantity_id bigint, primary key (id)) engine=InnoDB
alter table author add constraint FKqi5nll4mal57ohohlv5g0qlv2 foreign key (book_id) references book (id)
alter table book add constraint FKklnrv3weler2ftkweewlky958 foreign key (author_id) references author (id)
alter table book add constraint FKam9riv8y6rjwkua1gapdfew4j foreign key (category_id) references category (id)
alter table book add constraint FKgtvt7p649s4x80y6f4842pnfq foreign key (publisher_id) references publisher (id)
alter table publisher add constraint FKnl0jbqw2v1vtnikc5u2uxrmmu foreign key (book_id) references book (id)
alter table shop add constraint FK3ystvlh74rsr5ra89h63ohpvj foreign key (book_id) references book (id)
alter table shop add constraint FKnuhy6jle5wtdxxq3bjtslylt4 foreign key (quantity_id) references quantity (id)
