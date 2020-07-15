select c.todo_content_no, c.content, done, c.no, c.id, l.title, l.TEAM_NO
  from `todo_content` c
 right outer join `todo_list` l
    on c.no = l.no
 where l.TEAM_NO = 1;

select title, team_no, id
  from todo_list;
  
select * from team;
select * from `group`;
select g.`GROUP_NO`, `GROUP_NAME`, `ID`, `TEAM_NO`, `TEAM_NAME`, `BACKGROUND_COLOR`
  from `group` g
left outer join team t
    on g.group_no = t.group_no
 where id='h@hh.hh';
select g.`GROUP_NO`, `GROUP_NAME`, `ID`, `TEAM_NO`, `TEAM_NAME`, `BACKGROUND_COLOR`
  from `group` g
left outer join team t
    on g.group_no = t.group_no
 where id='hrin@3004.com';
 
select t.team_no, t.team_name, background_color, g.group_no, m.id, leader, group_name
  from team t
  join team_member m
    on t.team_no = m.team_no
  join `group` g
    on t.group_no = g.group_no
 where m.id = 'h@hh.hh';
    
 
select group_no from `group` where id='h@hh.hh' and `GROUP_NAME` = 'Personal';
insert into `group`(group_no) values(2);
 
select GROUP_NO, GROUP_NAME, id
  from `group`
 where id='hrin@3004.com';
 
 select max(`GROUP_NO`) from `group`;
 
update `team` 
   set `GROUP_NO` = 1
 where `GROUP_NO` = 3;
delete from `group` where `GROUP_NO` = 3;

select * from `user` where id like '%a%';

select group_no from `group` where group_name = 'Personal' and id = 'hrin@3004.com';
update `team` set `group_no` = 1 where `group_no` = 20;
update `team` set `group_no` = 23 where `team_no` = 21;

select * from `team`;
select * from `team_member`;
select * from `role_member`;
select * from `timeline`;
select * from `kanban_list`;
select * from `all_board_list`;

select `timeline_no`, `action`, `field`, `team_no`, `id`, `dml_no` 
  from `timeline`
 where `team_no` = 1;
 
select m.team_no, m.id, leader, nickname, image
  from `team_member` m
  join `user` u
    on m.id = u.id
 where m.`team_no` = 5;
 
update `user` set `image` = 'ssss.jpg' where id = 'hrin@3004.com';
select * from `ROLE_MEMBER`;

select * from `user`;
delete from `user` where id = 'hrin@3004.com';

select `all_board_list_no`, `name`, `team_no`, `board_type_no`
  from `all_board_list`
 where `team_no` = 1
 order by `all_board_list_no`;
 
select * from board_comment;

select COMMENT_NO, content, WRITE_DATE, b.id, NICKNAME, image
  from board_comment b
 inner join `user` u
    on b.id = u.id
 where board_no = 1;
 
insert into board_comment (`content`, `write_date`, `id`, `board_no`) values('gㅎㅇㅎㅇㅎㅇㅎㅇ', now(), 'hrin@3004.com', 1);
select image from `user` where id='hrin@3004.com';

select * from team;
update `team` 
set `group_no` = 1 
where `team_no` = 1 
and `id` = "";
 
select group_team_no, g.group_no, t.team_no, tm.id, leader, group_name, background_color, group_name, team_name
  from `group` g
  join `group_team` gt
    on g.group_no = gt.group_no
  join `team` t
    on gt.team_no = t.team_no
  join `team_member` tm
    on t.team_no = tm.team_no
 where tm.id='hrin@3004.com';

select * from team;

insert into `group_team`(group_no, team_no) values();

select GROUP_NO, GROUP_NAME, id
  from `group`
 where id='hrin@3004.com';
 
select * 
from `group_team` gt
join `group` g
on gt.group_no = g.group_no
where id='hrin@3004.com';

select * from group_team;

select *
  from `group_team` gt
  join `group` g
    on gt.group_no = g.group_no
  join `team` t
    on gt.team_no = t.team_no
 where g.id='hrin@3004.com';
 
update `group_team` gt 
 inner join `group` g 
	on gt.group_no = g.group_no
   set gt.`group_no` = 3
 where `team_no` = 1
   and g.id = 'a@a.aa';