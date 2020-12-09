insert into person(`id`,`name`,`age`,`blood_type`,`year`,`month`,`day`) values (1,'martin',19,'A',1991,8,1);
insert into person(`id`,`name`,`age`,`blood_type`,`year`,`month`,`day`) values (2,'benny',19,'A',1992,7,21);
insert into person(`id`,`name`,`age`,`blood_type`,`year`,`month`,`day`) values (3,'jiyoung',21,'B',1993,10,15);
insert into person(`id`,`name`,`age`,`blood_type`,`year`,`month`,`day`) values (4,'lisa',18,'A',1994,8,31);
insert into person(`id`,`name`,`age`,`blood_type`,`year`,`month`,`day`) values (5,'lilly',20,'O',1995,12,23);

insert into block(`id`,`name`) values (1,'martin');
insert into block(`id`,`name`) values (2,'jiyoung');


update person set block_id=1 where id=1;
update person set block_id=2 where id=3;



