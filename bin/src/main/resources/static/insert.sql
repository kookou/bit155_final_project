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