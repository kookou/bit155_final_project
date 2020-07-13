-- 권한
insert into `role`(`AUTHORITY`, `DESCRIPTION`) values('ROLE_ADMIN', '관리자');
insert into `role`(`AUTHORITY`, `DESCRIPTION`) values('ROLE_USER', '일반 사용자');

-- 타임라인 타입
insert into `TIMELINE_TYPE`(`DML_NAME`) values('insert');
insert into `TIMELINE_TYPE`(`DML_NAME`) values('update');
insert into `TIMELINE_TYPE`(`DML_NAME`) values('delete');
select * from TIMELINE_TYPE;

-- 게시판 종류
insert into `BOARD_TYPE`(`BOARD_TYPE_NAME`) values('common board');
insert into `BOARD_TYPE`(`BOARD_TYPE_NAME`) values('kanban board');

-- 여기까지는 필수로 넣어줘야하는 데이터입니다. 밑에는 홈페이지에서 직접 가입 후 필요에 따라서 넣으세요.. 
-- 참고로 가입하면 그룹은 자동으로 하나 들어가게 되고 팀은 페이지에서 만들 수 있습니당. -------------

-- 그룹
insert into `GROUP`(`GROUP_NAME`, `id`) values('Personal', 'hyerin');
insert into `GROUP`(`GROUP_NAME`, `id`) values('Bit155', 'hyerin');
insert into `GROUP`(`GROUP_NAME`, `id`) values('MyProject', 'hyerin');
select * from `GROUP`;

-- 팀
insert into `TEAM`(`TEAM_NAME`, `GROUP_NO`) values('bit final project', 4);
insert into `TEAM`(`TEAM_NAME`, `GROUP_NO`) values('bit 2nd project', 5);
insert into `TEAM`(`TEAM_NAME`, `GROUP_NO`) values('bit 1st project', 5);
insert into `TEAM`(`TEAM_NAME`, `GROUP_NO`) values('simple project', 8);
select * from `TEAM`;

-- 팀구성원
insert into `TEAM_MEMBER`(`TEAM_NO`, `ID`, `LEADER`) values(2, 'cho@naver.com', 'Y');
insert into `TEAM_MEMBER`(`TEAM_NO`, `ID`) values(6, 'hye@3004.com');
insert into `TEAM_MEMBER`(`TEAM_NO`, `ID`) values(2, 'hyerin');
insert into `TEAM_MEMBER`(`TEAM_NO`, `ID`) values(3, 'hyerin');
insert into `TEAM_MEMBER`(`TEAM_NO`, `ID`) values(4, 'hyerin');
insert into `TEAM_MEMBER`(`TEAM_NO`, `ID`) values(13, 'hrin@3004.com');
select * from `TEAM_MEMBER`;

-- 게시판 목록
<<<<<<< HEAD
insert into `ALL_BOARD_LIST`(`NAME`, `TEAM_NO`, `ID`, `BOARD_TYPE_NO`) values('자유게시판', 1, 'jinwon', 1);
=======
insert into `ALL_BOARD_LIST`(`NAME`, `TEAM_NO`, `ID`, `BOARD_TYPE_NO`) values('자유게시판', 1, 'hrin@3004.com', 1);
>>>>>>> master
insert into `ALL_BOARD_LIST`(`NAME`, `TEAM_NO`, `ID`, `BOARD_TYPE_NO`) values('장부게시판', 1, 'hrin@3004.com', 2);
select * from `ALL_BOARD_LIST`;

-- 게시판
insert into `BOARD_LIST`(`TITLE`, `CONTENT`, `WRITE_DATE`, `all_board_list_no`, `ID`) values('안녕', '안녕하세요저는조진원입니다 판교에살고 90년생입니다 ㅎㅎ', now(), 1, 'hrin@3004.com');
insert into `BOARD_LIST`(`TITLE`, `CONTENT`, `WRITE_DATE`, `all_board_list_no`, `ID`) values('반가워', '나는 효자동 불효자야~^^^', now(), 5, 'hyerin');
insert into `BOARD_LIST`(`TITLE`, `CONTENT`, `WRITE_DATE`, `all_board_list_no`, `ID`) values('월요일좋아*^^*', '월요일좋아~~ 최고로좋아~~ ', now(), 5, 'seoyoung');

-- 투두리스트 목록
insert into `TODO_LIST`(`title`, `team_no`, `id`) values('팀원 혜린의 투두리스트', 1, 'hyerin');
insert into `TODO_LIST`(`title`, `team_no`, `id`) values('오늘 할 일', 1, 'hyerin');

-- 투두리스트 내용
insert into `TODO_CONTENT`(`content`, `no`, `id`) values('회원가입 UI 구현하기', 1, 'hyerin');
insert into `TODO_CONTENT`(`content`, `no`, `id`) values('회원가입 기능 구현하기', 1, 'hyerin');
insert into `TODO_CONTENT`(`content`, `no`, `id`) values('투두리스트 UI 구현하기', 1, 'hyerin');
insert into `TODO_CONTENT`(`content`, `no`, `id`) values('투두리스트 기능 구현하기', 1, 'hyerin');
insert into `TODO_CONTENT`(`content`, `no`, `id`) values('타임라인 UI 구현하기', 1, 'hyerin');
insert into `TODO_CONTENT`(`content`, `no`, `id`, `done`) values('타임라인 기능 구현하기', 1, 'hyerin', 'Y');