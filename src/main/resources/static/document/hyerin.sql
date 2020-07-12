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
 inner join team t
    on g.group_no = t.group_no
 where id='hrin@3004.com';
 
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