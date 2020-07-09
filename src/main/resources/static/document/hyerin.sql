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