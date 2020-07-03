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
insert into `GROUP`(`GROUP_NAME`) values('personal');
commit;

-- 팀
insert into `TEAM`(`TEAM_NAME`, `GROUP_NO`) values('bit final project', 1);
commit;

-- 팀구성원
insert into `TEAM_MEMBER`(`TEAM_NO`, `ID`, `LEADER`) values(1, 'jinwon', 'Y');
insert into `TEAM_MEMBER`(`TEAM_NO`, `ID`) values(1, 'hyerin');
commit;

-- 게시판 목록
insert into `ALL_BOARD_LIST`(`NAME`, `TEAM_NO`, `ID`, `BOARD_TYPE_NO`) values('자유게시판', 1, 'jinwon', 1);

-- 게시판
insert into `BOARD_LIST`(`TITLE`, `CONTENT`, `WRITE_DATE`, `NO`, `ID`) values('안녕', '안녕하세요저는조진원입니다 판교에살고 90년생입니다 ㅎㅎ', now(), 2, 'jinwon');
insert into `BOARD_LIST`(`TITLE`, `CONTENT`, `WRITE_DATE`, `NO`, `ID`) values('반가워', '나는 효자동 불효자야~^^^', now(), 2, 'hyerin');
insert into `BOARD_LIST`(`TITLE`, `CONTENT`, `WRITE_DATE`, `NO`, `ID`) values('월요일좋아*^^*', '월요일좋아~~ 최고로좋아~~ ', now(), 2, 'seoyoung');
commit;