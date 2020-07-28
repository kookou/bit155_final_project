-- 권한
insert into `role`(`AUTHORITY`, `DESCRIPTION`) values('ROLE_ADMIN', '관리자');
insert into `role`(`AUTHORITY`, `DESCRIPTION`) values('ROLE_USER', '일반 사용자');

-- 게시판 종류
insert into `BOARD_TYPE`(`BOARD_TYPE_NAME`) values('common board');
insert into `BOARD_TYPE`(`BOARD_TYPE_NAME`) values('kanban board');

-- 여기까지는 필수로 넣어줘야하는 데이터입니다. 밑에는 홈페이지에서 직접 가입 후 필요에 따라서 넣으세요.. 
-- 참고로 가입하면 그룹은 자동으로 하나 들어가게 되고 팀은 페이지에서 만들 수 있습니당. 
-- hrin@3004.com, cho@3004.com로 가입하시고 인증번호는 자바 콘솔창에도 뜨니까..확인가능 -------------
select * from `group`;

-- 팀
insert into `TEAM`(`TEAM_NAME`) values('bit final project');
insert into `TEAM`(`TEAM_NAME`) values('bit 1st project');
select * from `TEAM`;

-- 팀구성원
insert into `TEAM_MEMBER`(`TEAM_NO`, `ID`, `LEADER`) values(1, 'hrin@3004.com', 'Y');
insert into `TEAM_MEMBER`(`TEAM_NO`, `ID`) values(1, 'cho@3004.com');
select * from `TEAM_MEMBER`;

-- 그룹-팀 매핑
insert into `GROUP_TEAM`(`GROUP_NO`, `TEAM_NO`, `ID`) values(1, 1, 'hrin@3004.com');
insert into `GROUP_TEAM`(`GROUP_NO`, `TEAM_NO`, `ID`) values(2, 1, 'cho@3004.com');
select * from `GROUP_TEAM`;

-- 게시판 목록
insert into `ALL_BOARD_LIST`(`NAME`, `TEAM_NO`, `ID`, `BOARD_TYPE_NO`) values('자유게시판', 1, 'cho@3004.com', 1);
insert into `ALL_BOARD_LIST`(`NAME`, `TEAM_NO`, `ID`, `BOARD_TYPE_NO`) values('회의칸반게시판', 1, 'hrin@3004.com', 2);
select * from `ALL_BOARD_LIST`;

-- 게시판
insert into `BOARD_LIST`(`TITLE`, `CONTENT`, `WRITE_DATE`, `all_board_list_no`, `ID`) values('안녕', '안녕하세요저는조진원입니다 판교에살고 90년생입니다 ㅎㅎ', now(), 1, 'hrin@3004.com');
insert into `BOARD_LIST`(`TITLE`, `CONTENT`, `WRITE_DATE`, `all_board_list_no`, `ID`) values('반가워', '나는 효자동 불효자야~^^^', now(), 1, 'cho@3004.com');
insert into `BOARD_LIST`(`TITLE`, `CONTENT`, `WRITE_DATE`, `all_board_list_no`, `ID`) values('월요일좋아*^^*', '월요일좋아~~ 최고로좋아~~ ', now(), 1, 'hrin@3004.com');

-- 투두리스트 목록
insert into `TODO_LIST`(`title`, `team_no`, `id`) values('팀원 혜린의 투두리스트', 1, 'hrin@3004.com');
insert into `TODO_LIST`(`title`, `team_no`, `id`) values('오늘 할 일', 1, 'hrin@3004.com');

-- 투두리스트 내용
insert into `TODO_CONTENT`(`content`, `no`, `id`) values('회원가입 UI 구현하기', 1, 'hrin@3004.com');
insert into `TODO_CONTENT`(`content`, `no`, `id`) values('회원가입 기능 구현하기', 1, 'hrin@3004.com');
insert into `TODO_CONTENT`(`content`, `no`, `id`) values('투두리스트 UI 구현하기', 1, 'hrin@3004.com');
insert into `TODO_CONTENT`(`content`, `no`, `id`) values('투두리스트 기능 구현하기', 1, 'hrin@3004.com');
insert into `TODO_CONTENT`(`content`, `no`, `id`) values('타임라인 UI 구현하기', 1, 'hrin@3004.com');
insert into `TODO_CONTENT`(`content`, `no`, `id`, `state`) values('타임라인 기능 구현하기', 1, 'hrin@3004.com', 'done');

-- 칸반 리스트
INSERT INTO `kanban_list` (`KANBAN_LIST_INDEX`, `LIST_TITLE`, `ALL_BOARD_LIST_NO`, `ID`) 
VALUES ('0', '프로젝트 회의', '2', 'hrin@3004.com');

-- 칸반 카드
INSERT INTO `kanban_card` (`ID`, `TITLE`, `WRITE_DATE`, `FILE_COUNT`, `COMMENT_COUNT`, `CARD_INDEX`, `KANBAN_LIST_NO`) 
VALUES ('hrin@3004.com', '6/16', '2020-07-28 15:12:42', '0', '0', '0', '1');
