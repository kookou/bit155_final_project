-- 권한
insert into `role`(`AUTHORITY`, `DESCRIPTION`) values('ROLE_ADMIN', '관리자');
insert into `role`(`AUTHORITY`, `DESCRIPTION`) values('ROLE_USER', '일반 사용자');
insert into `role`(`AUTHORITY`, `DESCRIPTION`) values('ROLE_TEAM_MEMBER', '팀에 가입된 사람');
insert into `role`(`AUTHORITY`, `DESCRIPTION`) values('ROLE_TEAM_LEADER', '팀 리더');
commit;

-- 타임라인 타입
insert into `TIMELINE_TYPE`(`DML_NAME`) values('insert');
insert into `TIMELINE_TYPE`(`DML_NAME`) values('update');
insert into `TIMELINE_TYPE`(`DML_NAME`) values('delete');
select * from TIMELINE_TYPE;
commit;

-- 게시판 종류
insert into `BOARD_TYPE`(`BOARD_TYPE_NAME`) values('common board');
insert into `BOARD_TYPE`(`BOARD_TYPE_NAME`) values('kanban board');
commit;

-- 사용자
insert into `USER`(`ID`, `PWD`, `NICKNAME`) values('jinwon', '1004', '판교90');
insert into `USER`(`ID`, `PWD`, `NICKNAME`) values('hyerin', '1004', '효자동불효자');
insert into `USER`(`ID`, `PWD`, `NICKNAME`) values('seoyoung', '1004', '스폰지밥');
commit;

-- 그룹
insert into `GROUP`(`GROUP_NAME`, `id`) values('personal', 'hyerin');
insert into `GROUP`(`GROUP_NAME`, `id`) values('Bit155', 'hyerin');
insert into `GROUP`(`GROUP_NAME`, `id`) values('MyProject', 'hyerin');
commit;
select * from `GROUP`;
-- 팀
insert into `TEAM`(`TEAM_NAME`, `GROUP_NO`) values('bit final project', 4);
insert into `TEAM`(`TEAM_NAME`, `GROUP_NO`) values('bit 2nd project', 5);
insert into `TEAM`(`TEAM_NAME`, `GROUP_NO`) values('bit 1st project', 5);
insert into `TEAM`(`TEAM_NAME`, `GROUP_NO`) values('simple project', 6);
commit;
select * from `TEAM`;
-- 팀구성원
insert into `TEAM_MEMBER`(`TEAM_NO`, `ID`, `LEADER`) values(5, 'jinwon', 'Y');
insert into `TEAM_MEMBER`(`TEAM_NO`, `ID`) values(5, 'hyerin');
insert into `TEAM_MEMBER`(`TEAM_NO`, `ID`) values(6, 'hyerin');
insert into `TEAM_MEMBER`(`TEAM_NO`, `ID`) values(7, 'hyerin');
insert into `TEAM_MEMBER`(`TEAM_NO`, `ID`) values(8, 'hyerin');
commit;
select * from `TEAM_MEMBER`;
-- 게시판 목록
insert into `ALL_BOARD_LIST`(`NAME`, `TEAM_NO`, `ID`, `BOARD_TYPE_NO`) values('자유게시판', 5, 'jinwon', 1);
commit;
select * from `ALL_BOARD_LIST`;
-- 게시판
insert into `BOARD_LIST`(`TITLE`, `CONTENT`, `WRITE_DATE`, `NO`, `ID`) values('안녕', '안녕하세요저는조진원입니다 판교에살고 90년생입니다 ㅎㅎ', now(), 5, 'jinwon');
insert into `BOARD_LIST`(`TITLE`, `CONTENT`, `WRITE_DATE`, `NO`, `ID`) values('반가워', '나는 효자동 불효자야~^^^', now(), 5, 'hyerin');
insert into `BOARD_LIST`(`TITLE`, `CONTENT`, `WRITE_DATE`, `NO`, `ID`) values('월요일좋아*^^*', '월요일좋아~~ 최고로좋아~~ ', now(), 5, 'seoyoung');
commit;

-- 투두리스트 목록
insert into `TODO_LIST`(`title`, `team_no`, `id`) values('팀원 혜린의 투두리스트', 1, 'hyerin');
insert into `TODO_LIST`(`title`, `team_no`, `id`) values('오늘 할 일', 1, 'hyerin');
commit;

-- 투두리스트 내용
insert into `TODO_CONTENT`(`content`, `no`, `id`) values('회원가입 UI 구현하기', 1, 'hyerin');
insert into `TODO_CONTENT`(`content`, `no`, `id`) values('회원가입 기능 구현하기', 1, 'hyerin');
insert into `TODO_CONTENT`(`content`, `no`, `id`) values('투두리스트 UI 구현하기', 1, 'hyerin');
insert into `TODO_CONTENT`(`content`, `no`, `id`) values('투두리스트 기능 구현하기', 1, 'hyerin');
insert into `TODO_CONTENT`(`content`, `no`, `id`) values('타임라인 UI 구현하기', 1, 'hyerin');
insert into `TODO_CONTENT`(`content`, `no`, `id`, `done`) values('타임라인 기능 구현하기', 1, 'hyerin', 'Y');
commit;

select c.todo_content_no, c.content, done, c.no, c.id, l.title, l.TEAM_NO
  from `todo_content` c
 right outer join `todo_list` l
    on c.no = l.no
 where l.TEAM_NO = 1;

select title, team_no, id
  from todo_list;
  
select * from team;
select * from `group`;
select g.`GROUP_NO`, `GROUP_NAME`, `ID`, `TEAM_NO`, `TEAM_NAME`
  from `group` g
 inner join team t
    on g.group_no = t.group_no 
 where id='hyerin';