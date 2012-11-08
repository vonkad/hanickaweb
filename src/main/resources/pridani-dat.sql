create table klice(
  klic int primary key,
  title varchar(1000)
) ENGINE='InnoDB';

create table klice_groups(
  klicid int references klice,
  groupid int references groups
) ENGINE='InnoDB';

create table user_group_data(
  userid int references users,
  groupid int references groups,
  klic int references klice,
  hodnota varchar(1000),
  primary key (userid,groupid,klic)
) ENGINE='InnoDB';

insert into klice values (1,'Diskutant');
insert into groups values (4,'Metodologie pedagogického výzkumu 2 (sobota 28.4. v 13:30)');
insert into groups values (5,'Metodologie pedagogického výzkumu 2 (sobota 28.4. v 17:30)');

insert into klice_groups values (1,4);
insert into klice_groups values (1,5);


insert into types values (9,'Revidovaný článek (Zk)');
insert into types values (10,'Příloha k RČ (Zk)');
insert into types values (11,'Rešerše (Z)');
insert into types values (12,'Revidovaný článek (Zk)');
insert into types values (13,'Příloha k RČ (Zk)');
insert into types values (14,'Rešerše (Z)');

insert into types_groups values (9,4);
insert into types_groups values (10,4);
insert into types_groups values (11,4);
insert into types_groups values (12,5);
insert into types_groups values (13,5);
insert into types_groups values (14,5);

insert into users_groups values  ('47',4);
insert into users_groups values  ('50',4);
insert into users_groups values  ('51',4);
insert into users_groups values  ('54',4);
insert into users_groups values  ('66',4);
insert into users_groups values  ('70',4);
insert into users_groups values  ('71',4);
insert into users_groups values  ('58',4);
insert into users_groups values  ('59',4);
insert into users_groups values  ('63',4);
insert into users_groups values  ('68',4);

insert into users_groups values ('48',5);
insert into users_groups values ('49',5);
insert into users_groups values ('72',5);
insert into users_groups values ('52',5);
insert into users_groups values ('53',5);
insert into users_groups values ('55',5);
insert into users_groups values ('56',5);
insert into users_groups values ('62',5);
insert into users_groups values ('64',5);
insert into users_groups values ('67',5);
insert into users_groups values ('69',5);
insert into users_groups values ('72',5);
insert into users_groups values ('100',5);
