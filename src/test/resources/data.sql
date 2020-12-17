insert into person(`id`,`name`,`blood_type`,`year`,`month`,`day`,`job`) values (1,'martin','A',1991,8,1,'programmer');
insert into person(`id`,`name`,`blood_type`,`year`,`month`,`day`) values (2,'benny','A',1992,7,21);
insert into person(`id`,`name`,`blood_type`,`year`,`month`,`day`) values (3,'jiyoung','B',1993,10,15);
insert into person(`id`,`name`,`blood_type`,`year`,`month`,`day`) values (4,'lisa','A',1994,8,31);
insert into person(`id`,`name`,`blood_type`,`year`,`month`,`day`) values (5,'lilly','O',1995,12,23);

insert into block(`id`,`name`) values (1,'martin');
insert into block(`id`,`name`) values (2,'jiyoung');


update person set block_id=1 where id=1;
update person set block_id=2 where id=3;



