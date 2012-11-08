drop database newhanicka;
create database newhanicka character set 'UTF8';
grant all privileges on newhanicka.* to newhanicka@localhost identified by 'katdav11';
grant select on hanicka.* to newhanicka@localhost;
use newhanicka;

create table users(
  id int primary key,
  username varchar(255)
) ENGINE='InnoDB' DEFAULT character set=utf8;

create table klice(
  klic int primary key,
  title varchar(1000)
) ENGINE='InnoDB' DEFAULT character set=utf8;

create table klice_groups(
  klicid int references klice,
  groupid int references groups
) ENGINE='InnoDB' DEFAULT character set=utf8;


create table user_group_data(
  userid int references users,
  groupid int references groups,
  klic int references klice,
  hodnota varchar(1000),
  primary key (userid,groupid,klic)
) ENGINE='InnoDB' DEFAULT character set=utf8;


create table types(
  id int primary key,
  title varchar(255)
) ENGINE='InnoDB' DEFAULT character set=utf8;

create table groups(
  id int primary key,
  title varchar(255)
) ENGINE='InnoDB' DEFAULT character set=utf8;

create table types_groups(
  typeid int references types,
  groupid int references groups
) ENGINE='InnoDB' DEFAULT character set=utf8;

create table users_groups(
  userid int references users,
  groupid int references groups,
  primary key (userid,groupid)
) ENGINE='InnoDB' DEFAULT character set=utf8;

create table files(
  userid int references users,
  typeid int references types,
  version int,
  current int not null,
  filename varchar(255),
  content longblob,
  primary key (userid,typeid,version)
) ENGINE='InnoDB' DEFAULT character set=utf8;


insert into groups values (1,'Metodologie pedagogického výzkumu I');
insert into types values (1,'Návrh výzkumu (Z)');
insert into types values (2,'Článek (Zk)');
insert into types values (3,'Příloha článku (Zk)');
insert into types_groups values (1,1);
insert into types_groups values (2,1);
insert into types_groups values (3,1);

insert into groups values (2,'Seminář k diplomové práci z pedagogiky');
insert into types values (4,'Části diplomové práce (Z)');
insert into types_groups values (4,2);

insert into groups values (3,'Tvorba a využití didaktických testů');
insert into types values (5,'Článek (KZ)');
insert into types values (6,'Příloha článku (KZ)');
insert into types_groups values (5,3);
insert into types_groups values (6,3);

insert into users values ('1','Markéta Adamcová'); insert into users_groups values ('1',3);
insert into users values ('2','Anna Balážová');  insert into users_groups values ('2',3);
insert into users values ('3','Monika Bandasová');  insert into users_groups values ('3',3);
insert into users values ('4','Martin Bělohoubek');  insert into users_groups values ('4',3);
insert into users values ('5','Anna Benešová');  insert into users_groups values ('5',3);
insert into users values ('6','Michaela Dlasková'); insert into users_groups values ('6',3);
insert into users values ('7','Jana Dvořáková'); insert into users_groups values ('7',3);
insert into users values ('8','Tomáš Ficza');  insert into users_groups values ('8',3);
insert into users values ('9','Michaela Fořtová');  insert into users_groups values ('9',3);
insert into users values ('10','Ondřej Ganev');  insert into users_groups values ('10',3);
insert into users values ('11','Tomáš Gaudník');  insert into users_groups values ('11',3);
insert into users values ('13','David Goldstein');  insert into users_groups values ('13',3);
insert into users values ('12','Jan Havel');  insert into users_groups values ('12',3);
insert into users values ('14','Zuzana Helceletová');  insert into users_groups values ('14',3);
insert into users values ('15','Jiří Chrobok');  insert into users_groups values ('15',3);
insert into users values ('16','Iveta Jeřábková');  insert into users_groups values ('16',3);
insert into users values ('17','Markéta Julišová');  insert into users_groups values ('17',3);
insert into users values ('18','Kateřina Kaderová');  insert into users_groups values ('18',3);
insert into users values ('19','Alena Kašparová');  insert into users_groups values ('19',3);
insert into users values ('21','Michaela Klepáčková');  insert into users_groups values ('21',3);
insert into users values ('20','Kateřina Kocáková');  insert into users_groups values ('20',3);
insert into users values ('22','Eliška Kudláčková');  insert into users_groups values ('22',3);
insert into users values ('23','Jakub Kulhánek');  insert into users_groups values ('23',3);
insert into users values ('24','Zdeňka Kulhavá');  insert into users_groups values ('24',3);
insert into users values ('25','Daniela Kulíková');  insert into users_groups values ('25',3);
insert into users values ('26','Slavomír Mácha');  insert into users_groups values ('26',3);
insert into users values ('28','Klára Maratová'); insert into users_groups values ('28',3);
insert into users values ('27','Martin Mejzr');  insert into users_groups values ('27',3);
insert into users values ('29','Michaela Mikulová');  insert into users_groups values ('29',3);
insert into users values ('30','Marie Mrázková');  insert into users_groups values ('30',3);
insert into users values ('31','Eva Němcová');  insert into users_groups values ('31',3);
insert into users values ('32','Lucie Neradová');  insert into users_groups values ('32',3);
insert into users values ('33','Radka Ondrová');  insert into users_groups values ('33',3);
insert into users values ('34','Zdeňka Podlipná');  insert into users_groups values ('34',3);
insert into users values ('35','Karel Prášek');  insert into users_groups values ('35',3);
insert into users values ('36','Tereza Pražská');  insert into users_groups values ('36',3);
insert into users values ('37','Ondřej Solnička');  insert into users_groups values ('37',3);
insert into users values ('38','Iveta Strnadová');  insert into users_groups values ('38',3);
insert into users values ('40','Lenka Stříbrská');  insert into users_groups values ('40',3);
insert into users values ('39','Lucie Valášková');  insert into users_groups values ('39',3);
insert into users values ('41','Edita Vošická');  insert into users_groups values ('41',3);
insert into users values ('42','Pavlína Vožická');  insert into users_groups values ('42',3);
insert into users values ('43','Karolína Zemánková');  insert into users_groups values ('43',3);
insert into users values ('44','Hana Zemanová');  insert into users_groups values ('44',3);
insert into users values ('45','Lucie Zimová');  insert into users_groups values ('45',3);
insert into users values ('46','Martin Zítka');  insert into users_groups values ('46',3);

insert into users values ('47','Klára Brůžková'); insert into users_groups values  ('47',1); insert into users_groups values  ('47',2);
insert into users values ('48','Šárka Čapková'); insert into users_groups values  ('48',1); insert into users_groups values  ('48',2);
insert into users values ('49','Michaela Černá'); insert into users_groups values  ('49',1);insert into users_groups values  ('49',2);
insert into users values ('50','Martina Grófová'); insert into users_groups values  ('50',1);insert into users_groups values  ('50',2);
insert into users values ('51','Kateřina Harazimová'); insert into users_groups values  ('51',1);insert into users_groups values  ('51',2);
insert into users values ('52','Jiří Hauser'); insert into users_groups values  ('52',1);insert into users_groups values  ('52',2);
insert into users values ('53','Robert Homolka'); insert into users_groups values  ('53',1);insert into users_groups values  ('53',2);
insert into users values ('54','Dominika Jírová'); insert into users_groups values  ('54',1);insert into users_groups values  ('54',2);
insert into users values ('55','Jana Kábrtová'); insert into users_groups values  ('55',1);insert into users_groups values  ('55',2);
insert into users values ('56','Eva Kadlecová'); insert into users_groups values  ('56',1);insert into users_groups values  ('56',2);
insert into users values ('57','Martin Klán'); insert into users_groups values  ('57',1);insert into users_groups values  ('57',2);
insert into users values ('58','Martin Krotil'); insert into users_groups values  ('58',1);insert into users_groups values  ('58',2);
insert into users values ('59','Daniel Kříž'); insert into users_groups values  ('59',1);insert into users_groups values  ('59',2);
insert into users values ('60','Petra Matějková'); insert into users_groups values  ('60',1);insert into users_groups values  ('60',2);
insert into users values ('61','Marie Obrová'); insert into users_groups values  ('61',1);insert into users_groups values  ('61',2);
insert into users values ('62','Jan Princlík'); insert into users_groups values  ('62',1);insert into users_groups values  ('62',2);
insert into users values ('63','Jitka Rothanzlová'); insert into users_groups values  ('63',1);insert into users_groups values  ('63',2);
insert into users values ('64','Petr Sál'); insert into users_groups values  ('64',1);insert into users_groups values  ('64',2);
insert into users values ('65','Lenka Sochanová'); insert into users_groups values  ('65',1);insert into users_groups values  ('65',2);
insert into users values ('66','Věra Stránská'); insert into users_groups values  ('66',1);insert into users_groups values  ('66',2);
insert into users values ('67','Hana Škubníková'); insert into users_groups values  ('67',1);insert into users_groups values  ('67',2);
insert into users values ('68','Anna Štefíková'); insert into users_groups values  ('68',1);insert into users_groups values  ('68',2);
insert into users values ('69','Žaneta Švidroňová'); insert into users_groups values  ('69',1);insert into users_groups values  ('69',2);
insert into users values ('70','Markéta Venderová'); insert into users_groups values  ('70',1);insert into users_groups values  ('70',2);
insert into users values ('71','Zuzana Vízková'); insert into users_groups values  ('71',1);insert into users_groups values  ('71',2);
insert into users values ('72','Zdeňka Vološínová'); insert into users_groups values  ('72',1);insert into users_groups values  ('72',2);
insert into users values ('73','Petra Voříšková'); insert into users_groups values  ('73',1);insert into users_groups values  ('73',2);
insert into users values ('74','Klára Zoubková'); insert into users_groups values  ('74',1);insert into users_groups values  ('74',2);

insert into files select users.id,hanicka.files.type,hanicka.files.version,hanicka.files.current,hanicka.files.filename,hanicka.files.content from hanicka.files inner join users on (hanicka.files.user=users.username);
