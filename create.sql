create table answers (answer_id bigint not null auto_increment, question_id bigint, content_id bigint, primary key (answer_id)) engine=InnoDB;
create table content (content_id bigint not null auto_increment, created_at datetime(6), description varchar(255), updated_at datetime(6), user_id bigint, primary key (content_id)) engine=InnoDB;
create table questions (question_id bigint not null auto_increment, title varchar(255), content_id bigint, primary key (question_id)) engine=InnoDB;
create table questions_tags (question_id bigint not null, tag_id bigint not null, primary key (question_id, tag_id)) engine=InnoDB;
create table tags (tag_id bigint not null auto_increment, name varchar(255), primary key (tag_id)) engine=InnoDB;
create table users (user_id bigint not null auto_increment, e_mail varchar(255), f_name varchar(255), l_name varchar(255), password varchar(255), role varchar(255), primary key (user_id)) engine=InnoDB;
alter table answers add constraint FKqolg9rmukcj8w7j2j6f0os2wr foreign key (content_id) references content (content_id);
alter table content add constraint FK8ha3wmua0lw7fp1km9t8deutx foreign key (user_id) references users (user_id);
alter table questions add constraint FK8aq2mp5u9col4b9wm5m9kc5un foreign key (content_id) references content (content_id);
alter table questions_tags add constraint FK7yq8xf1pqv8katljm8v8j8w3c foreign key (tag_id) references tags (tag_id);
alter table questions_tags add constraint FK4u5xv906wfevngoe973bec6u0 foreign key (question_id) references questions (question_id);
create table answers (answer_id bigint not null auto_increment, question_id bigint, content_id bigint, primary key (answer_id)) engine=InnoDB;
create table content (content_id bigint not null auto_increment, created_at datetime(6), description varchar(255), updated_at datetime(6), user_id bigint, primary key (content_id)) engine=InnoDB;
create table questions (question_id bigint not null auto_increment, title varchar(255), content_id bigint, primary key (question_id)) engine=InnoDB;
create table questions_tags (question_id bigint not null, tag_id bigint not null, primary key (question_id, tag_id)) engine=InnoDB;
create table tags (tag_id bigint not null auto_increment, name varchar(255), primary key (tag_id)) engine=InnoDB;
create table users (user_id bigint not null auto_increment, e_mail varchar(255), f_name varchar(255), l_name varchar(255), password varchar(255), role varchar(255), primary key (user_id)) engine=InnoDB;
alter table answers add constraint FKqolg9rmukcj8w7j2j6f0os2wr foreign key (content_id) references content (content_id);
alter table content add constraint FK8ha3wmua0lw7fp1km9t8deutx foreign key (user_id) references users (user_id);
alter table questions add constraint FK8aq2mp5u9col4b9wm5m9kc5un foreign key (content_id) references content (content_id);
alter table questions_tags add constraint FK7yq8xf1pqv8katljm8v8j8w3c foreign key (tag_id) references tags (tag_id);
alter table questions_tags add constraint FK4u5xv906wfevngoe973bec6u0 foreign key (question_id) references questions (question_id);
create table answers (answer_id bigint not null auto_increment, question_id bigint, content_id bigint, primary key (answer_id)) engine=InnoDB;
create table content (content_id bigint not null auto_increment, created_at datetime(6), description varchar(255), updated_at datetime(6), user_id bigint, primary key (content_id)) engine=InnoDB;
create table questions (question_id bigint not null auto_increment, title varchar(255), content_id bigint, primary key (question_id)) engine=InnoDB;
create table questions_tags (question_id bigint not null, tag_id bigint not null, primary key (question_id, tag_id)) engine=InnoDB;
create table tags (tag_id bigint not null auto_increment, name varchar(255), primary key (tag_id)) engine=InnoDB;
create table users (user_id bigint not null auto_increment, e_mail varchar(255), f_name varchar(255), l_name varchar(255), password varchar(255), role varchar(255), primary key (user_id)) engine=InnoDB;
alter table answers add constraint FKqolg9rmukcj8w7j2j6f0os2wr foreign key (content_id) references content (content_id);
alter table content add constraint FK8ha3wmua0lw7fp1km9t8deutx foreign key (user_id) references users (user_id);
alter table questions add constraint FK8aq2mp5u9col4b9wm5m9kc5un foreign key (content_id) references content (content_id);
alter table questions_tags add constraint FK7yq8xf1pqv8katljm8v8j8w3c foreign key (tag_id) references tags (tag_id);
alter table questions_tags add constraint FK4u5xv906wfevngoe973bec6u0 foreign key (question_id) references questions (question_id);
