-- TRIGGER START --------- INSERT END ----------

-- 유저 가입시 유저권한주기
DELIMITER $$
CREATE TRIGGER `USER_INSERT_TRIGGER`
AFTER INSERT ON `USER`
FOR EACH ROW 
BEGIN
INSERT INTO `ROLE_MEMBER` 
SET 
`AUTHORITY` = 'ROLE_USER',  
`ID` = NEW.`ID`;
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER `USER_DELETE_TRIGGER`
AFTER DELETE ON `ROLE_MEMBER`
FOR EACH ROW 
BEGIN
DELETE
FROM `USER` 
WHERE `ID` = OLD.`ID`;
END $$
DELIMITER ;

-- 유저 가입시 default group 추가하기
DELIMITER $$
CREATE TRIGGER `DEFAULT_GROUP_TRIGGER`
AFTER INSERT ON `USER`
FOR EACH ROW 
BEGIN
INSERT INTO `GROUP` 
SET 
`GROUP_NAME` = 'Personal',  
`ID` = NEW.`ID`;
END $$
DELIMITER ;


-- 댓글 등록시 칸반카드 테이블에 댓글 갯수 자동 증가시키기
DELIMITER $$
CREATE TRIGGER `CARD_COMMENT_COUNT_TRIGGER`
AFTER INSERT ON `board_comment`
FOR EACH ROW 
BEGIN
	IF (NEW.`CARD_NO` > 0) THEN
		UPDATE `KANBAN_CARD`
		SET `COMMENT_COUNT` = `COMMENT_COUNT`+1
		WHERE `CARD_NO` = NEW.`CARD_NO`;
	END IF;
END $$
DELIMITER ;

-- 파일 등록시 칸반카드 테이블에 파일 갯수 자동 증가시키기
DELIMITER $$
CREATE TRIGGER `CARD_FILE_COUNT_TRIGGER`
AFTER INSERT ON `board_file`
FOR EACH ROW 
BEGIN
   IF (NEW.`CARD_NO` > 0) THEN
      UPDATE `KANBAN_CARD`
      SET `FILE_COUNT` = `FILE_COUNT`+1
      WHERE `CARD_NO` = NEW.`CARD_NO`;
   END IF;
END $$
DELIMITER ;

-- 댓글 삭제시 칸반카드 테이블에 댓글 갯수 자동 감소시키기
DELIMITER $$
CREATE TRIGGER `CARD_COMMENT_DELETE_COUNT_TRIGGER`
AFTER delete ON `board_comment`
FOR EACH ROW 
BEGIN
   IF (old.`comment_NO` > 0) THEN
      UPDATE `KANBAN_CARD`
      SET `comment_COUNT` = `comment_COUNT`-1
      WHERE `CARD_NO` = old.`CARD_NO`;
   END IF;
END $$
DELIMITER ;

-- 파일 삭제시 칸반카드 테이블에 파일 갯수 자동 감소시키기
DELIMITER $$
CREATE TRIGGER `CARD_FILE_DELETE_COUNT_TRIGGER`
AFTER delete ON `board_file`
FOR EACH ROW 
BEGIN
   IF (old.`FILE_NO` > 0) THEN
      UPDATE `KANBAN_CARD`
      SET `FILE_COUNT` = `FILE_COUNT`-1
      WHERE `CARD_NO` = old.`CARD_NO`;
   END IF;
END $$
DELIMITER ;

-- ---------------------------------------------------------------------타임라인 트리거------------------------------------
-- ------------------ ALL_BOARD_LIST
-- 보드 추가시 타임라인 테이블에 로그입력하기
DELIMITER $$
CREATE TRIGGER `TIMELINE_INSERT_ALLBOARDLIST_TRIGGER`
AFTER INSERT ON `ALL_BOARD_LIST`
FOR EACH ROW 
BEGIN
	INSERT INTO `TIMELINE`
	SET
		`TABLE_NAME` = 'ALL_BOARD_LIST',
		`COLUMN_NAME` = 'BOARD LIST',
		`COLUMN_NO` = NEW.`ALL_BOARD_LIST_NO`,
		`HISTORY` = NEW.`NAME`,
		`DML_KIND` = 'insert',
        `HISTORY_TIME` = now(),
		`TEAM_NO` = NEW.`TEAM_NO`,
		`ID` = NEW.`ID`;
END $$
DELIMITER ;

-- 보드 삭제시 타임라인 테이블에 로그입력하기
DELIMITER $$
CREATE TRIGGER `TIMELINE_DELETE_ALLBOARDLIST_TRIGGER`
AFTER delete ON `ALL_BOARD_LIST`
FOR EACH ROW 
BEGIN
	INSERT INTO `TIMELINE`
	SET
		`TABLE_NAME` = 'ALL_BOARD_LIST',
		`COLUMN_NAME` = 'BOARD LIST',
		`COLUMN_NO` = OLD.`ALL_BOARD_LIST_NO`,
		`HISTORY` = OLD.`NAME`,
		`DML_KIND` = 'delete',
        `HISTORY_TIME` = now(),
		`TEAM_NO` = OLD.`TEAM_NO`,
		`ID` = OLD.`ID`;
END $$
DELIMITER ;

-- 보드 수정시 타임라인 테이블에 로그입력하기
DELIMITER $$
CREATE TRIGGER `TIMELINE_UPDATE_ALLBOARDLIST_TRIGGER`
AFTER update ON `ALL_BOARD_LIST`
FOR EACH ROW 
BEGIN
	INSERT INTO `TIMELINE`
	SET
		`TABLE_NAME` = 'ALL_BOARD_LIST',
		`COLUMN_NAME` = 'BOARD LIST',
		`COLUMN_NO` = NEW.`ALL_BOARD_LIST_NO`,
        `OLD_HISTORY` = OLD.`NAME`,
		`HISTORY` = NEW.`NAME`,
		`DML_KIND` = 'update',
        `HISTORY_TIME` = now(),
		`TEAM_NO` = OLD.`TEAM_NO`,
		`ID` = NEW.`ID`;
END $$
DELIMITER ;

-- ------------------- TEAM_MEMBER
-- 팀구성원 추가시 타임라인 테이블에 로그입력하기
DELIMITER $$
CREATE TRIGGER `TIMELINE_INSERT_TEAMMEMBER_TRIGGER`
AFTER INSERT ON `TEAM_MEMBER`
FOR EACH ROW 
BEGIN
	INSERT INTO `TIMELINE`
	SET
		`TABLE_NAME` = 'TEAM_MEMBER',
		`COLUMN_NAME` = 'TEAM_NO',
		`COLUMN_NO` = NEW.`TEAM_NO`,
		`HISTORY` = NEW.`ID`,
		`DML_KIND` = 'join team',
        `HISTORY_TIME` = now(),
		`TEAM_NO` = NEW.`TEAM_NO`,
		`ID` = NEW.`ID`;
END $$
DELIMITER ;

-- 팀구성원 탈퇴시 타임라인 테이블에 로그입력하기
DELIMITER $$
CREATE TRIGGER `TIMELINE_DELETE_TEAMMEMBER_TRIGGER`
before delete ON `TEAM_MEMBER`
FOR EACH ROW 
BEGIN
	INSERT INTO `TIMELINE`
	SET
    	`TABLE_NAME` = 'TEAM_MEMBER',
		`COLUMN_NAME` = 'TEAM_NO',
		`COLUMN_NO` = OLD.`TEAM_NO`,
		`HISTORY` = OLD.`ID`,
		`DML_KIND` = 'wthdr team',
        `HISTORY_TIME` = now(),
		`TEAM_NO` = OLD.`TEAM_NO`,
		`ID` = OLD.`ID`;
END $$
DELIMITER ;

-- ---------------------- BOARD_LIST
-- 일반게시판 게시물 추가시 타임라인 테이블에 로그입력하기
DELIMITER $$
CREATE TRIGGER `TIMELINE_INSERT_BOARDLIST_TRIGGER`
AFTER INSERT ON `BOARD_LIST`
FOR EACH ROW 
BEGIN
	DECLARE board_name varchar(50);
    DECLARE team_no1 int;
	select `NAME`, `team_no` into board_name, team_no1
	  from `BOARD_LIST` b
	  join `ALL_BOARD_LIST` a
		on b.`ALL_BOARD_LIST_NO` = a.`ALL_BOARD_LIST_NO`
	 where b.`ALL_BOARD_LIST_NO` = NEW.`ALL_BOARD_LIST_NO`
	   and `BOARD_NO` = NEW.`BOARD_NO`;
	INSERT INTO `TIMELINE`
	SET
    	`TABLE_NAME` = 'BOARD_LIST',
		`COLUMN_NAME` = board_name,
		`COLUMN_NO` = NEW.`BOARD_NO`,
        `ALL_BOARD_NO` = NEW.`ALL_BOARD_LIST_NO`,
		`HISTORY` = NEW.`TITLE`,
		`DML_KIND` = 'insert',
        `HISTORY_TIME` = now(),
		`TEAM_NO` = team_no1,
		`ID` = NEW.`ID`;
END $$
DELIMITER ;

-- 일반게시판 게시물 삭제시 타임라인 테이블에 로그입력하기
DELIMITER $$
CREATE TRIGGER `TIMELINE_DELETE_BOARDLIST_TRIGGER`
before delete ON `BOARD_LIST`
FOR EACH ROW 
BEGIN
	DECLARE board_name1 varchar(50);
    DECLARE team_no1 int;
	select `NAME`, `team_no` into board_name1, team_no1
	  from `BOARD_LIST` b
	  join `ALL_BOARD_LIST` a
		on b.`ALL_BOARD_LIST_NO` = a.`ALL_BOARD_LIST_NO`
	 where `BOARD_NO` = OLD.`BOARD_NO`;
	INSERT INTO `TIMELINE`
	SET
    	`TABLE_NAME` = 'BOARD_LIST',
		`COLUMN_NAME` = board_name1,
		`COLUMN_NO` = OLD.`BOARD_NO`,
        `ALL_BOARD_NO` = OLD.`ALL_BOARD_LIST_NO`,
		`HISTORY` = OLD.`TITLE`,
		`DML_KIND` = 'delete',
        `HISTORY_TIME` = now(),
		`TEAM_NO` = team_no1,
		`ID` = OLD.`ID`;
END $$
DELIMITER ;

-- 일반게시판 게시물 수정시 타임라인 테이블에 로그입력하기
DELIMITER $$
CREATE TRIGGER `TIMELINE_UPDATE_BOARDLIST_TRIGGER`
AFTER update ON `BOARD_LIST`
FOR EACH ROW 
BEGIN
	DECLARE board_name varchar(50);
    DECLARE team_no1 int;
	select `NAME`, `team_no` into board_name, team_no1
	  from `BOARD_LIST` b
	  join `ALL_BOARD_LIST` a
		on b.`ALL_BOARD_LIST_NO` = a.`ALL_BOARD_LIST_NO`
	 where b.`ALL_BOARD_LIST_NO` = OLD.`ALL_BOARD_LIST_NO`
	   and `BOARD_NO` = OLD.`BOARD_NO`;
	INSERT INTO `TIMELINE`
	SET
    	`TABLE_NAME` = 'BOARD_LIST',
		`COLUMN_NAME` = board_name,
		`COLUMN_NO` = NEW.`BOARD_NO`,
        `ALL_BOARD_NO` = OLD.`ALL_BOARD_LIST_NO`,
        `OLD_HISTORY` = OLD.`TITLE`,
		`HISTORY` = NEW.`TITLE`,
		`DML_KIND` = 'update',
        `HISTORY_TIME` = now(),
		`TEAM_NO` = team_no1,
		`ID` = NEW.`ID`;
END $$
DELIMITER ;

-- ------------------- KANBAN_LIST
-- 칸반보드리스트 insert시 타임라인 테이블에 로그입력하기
DELIMITER $$
CREATE TRIGGER `TIMELINE_INSERT_KANBANLIST_TRIGGER`
AFTER INSERT ON `KANBAN_LIST`
FOR EACH ROW 
BEGIN
	DECLARE board_name varchar(50);
    DECLARE team_no1 int;
	select `NAME`, `team_no` into board_name, team_no1
	  from `KANBAN_LIST` k
	  join `ALL_BOARD_LIST` a
		on k.`ALL_BOARD_LIST_NO` = a.`ALL_BOARD_LIST_NO`
	 where k.`ALL_BOARD_LIST_NO` = NEW.`ALL_BOARD_LIST_NO`
	   and `KANBAN_LIST_NO` = NEW.`KANBAN_LIST_NO`;
	INSERT INTO `TIMELINE`
	SET
    	`TABLE_NAME` = 'KANBAN_LIST',
		`COLUMN_NAME` = board_name,
		`COLUMN_NO` = NEW.`KANBAN_LIST_NO`,
        `ALL_BOARD_NO` = NEW.`ALL_BOARD_LIST_NO`,
		`HISTORY` = NEW.`LIST_TITLE`,
		`DML_KIND` = 'insert',
        `HISTORY_TIME` = now(),
		`TEAM_NO` = team_no1,
		`ID` = NEW.`ID`;
END $$
DELIMITER ;

-- 칸반보드리스트 delete시 타임라인 테이블에 로그입력하기
DELIMITER $$
CREATE TRIGGER `TIMELINE_DELETE_KANBANLIST_TRIGGER`
before delete ON `KANBAN_LIST`
FOR EACH ROW 
BEGIN
	DECLARE board_name varchar(50);
    DECLARE team_no1 int;
	select `NAME`, `team_no` into board_name, team_no1
	  from `KANBAN_LIST` k
	  join `ALL_BOARD_LIST` a
		on k.`ALL_BOARD_LIST_NO` = a.`ALL_BOARD_LIST_NO`
	 where k.`ALL_BOARD_LIST_NO` = OLD.`ALL_BOARD_LIST_NO`
	   and `KANBAN_LIST_NO` = OLD.`KANBAN_LIST_NO`;
	INSERT INTO `TIMELINE`
	SET
    	`TABLE_NAME` = 'KANBAN_LIST',
		`COLUMN_NAME` = board_name,
		`COLUMN_NO` = OLD.`KANBAN_LIST_NO`,
        `ALL_BOARD_NO` = OLD.`ALL_BOARD_LIST_NO`,
		`HISTORY` = OLD.`LIST_TITLE`,
		`DML_KIND` = 'delete',
        `HISTORY_TIME` = now(),
		`TEAM_NO` = team_no1,
		`ID` = OLD.`ID`;
END $$
DELIMITER ;

-- 칸반보드리스트 update시 타임라인 테이블에 로그입력하기
DELIMITER $$
CREATE TRIGGER `TIMELINE_UPDATE_KANBANLIST_TRIGGER`
AFTER update ON `KANBAN_LIST`
FOR EACH ROW 
BEGIN
	DECLARE board_name varchar(50);
    DECLARE team_no1 int;
	select `NAME`, `team_no` into board_name, team_no1
	  from `KANBAN_LIST` k
	  join `ALL_BOARD_LIST` a
		on k.`ALL_BOARD_LIST_NO` = a.`ALL_BOARD_LIST_NO`
	 where k.`ALL_BOARD_LIST_NO` = OLD.`ALL_BOARD_LIST_NO`
	   and `KANBAN_LIST_NO` = OLD.`KANBAN_LIST_NO`;
	INSERT INTO `TIMELINE`
	SET
    	`TABLE_NAME` = 'KANBAN_LIST',
		`COLUMN_NAME` = board_name,
		`COLUMN_NO` = NEW.`KANBAN_LIST_NO`,
        `ALL_BOARD_NO` = OLD.`ALL_BOARD_LIST_NO`,
        `OLD_HISTORY` = OLD.`LIST_TITLE`,
		`HISTORY` = NEW.`LIST_TITLE`,
		`DML_KIND` = 'update',
        `HISTORY_TIME` = now(),
		`TEAM_NO` = team_no1,
		`ID` = NEW.`ID`;
END $$
DELIMITER ;

-- ------------------- KANBAN_CARD
-- 칸반보드카드 insert시 타임라인 테이블에 로그입력하기
DELIMITER $$
CREATE TRIGGER `TIMELINE_INSERT_KANBANCARD_TRIGGER`
AFTER INSERT ON `KANBAN_CARD`
FOR EACH ROW 
BEGIN
	DECLARE kanban_list_name varchar(50);
    DECLARE team_no1 int;
    DECLARE all_board_list_no1 varchar(50);
	select `list_title`, `team_no`, l.`all_board_list_no` into kanban_list_name, team_no1, all_board_list_no1
	  from `KANBAN_CARD` c
	  join `KANBAN_LIST` l
		on c.`kanban_list_no` = l.`kanban_list_no`
	  join `all_board_list` a
		on l.`all_board_list_no` = a.`all_board_list_no`
	 where `card_no` = new.`card_no`;
	INSERT INTO `TIMELINE`
	SET
    	`TABLE_NAME` = 'KANBAN_CARD',
		`COLUMN_NAME` = kanban_list_name,
		`COLUMN_NO` = NEW.`CARD_NO`,
        `ALL_BOARD_NO` = all_board_list_no1,
		`HISTORY` = NEW.`TITLE`,
		`DML_KIND` = 'insert',
        `HISTORY_TIME` = now(),
		`TEAM_NO` = team_no1,
		`ID` = NEW.ID;
END $$
DELIMITER ;

-- 칸반보드카드 update시 타임라인 테이블에 로그입력하기
DELIMITER $$
CREATE TRIGGER `TIMELINE_UPDATE_KANBANCARD_TRIGGER`
AFTER update ON `KANBAN_CARD`
FOR EACH ROW 
BEGIN
	DECLARE kanban_list_name varchar(50);
    DECLARE team_no1 int;
    DECLARE all_board_list_no1 varchar(50);
	select `list_title`, `team_no`, l.`all_board_list_no` into kanban_list_name, team_no1, all_board_list_no1
	  from `KANBAN_CARD` c
	  join `KANBAN_LIST` l
		on c.`kanban_list_no` = l.`kanban_list_no`
	  join `all_board_list` a
		on l.`all_board_list_no` = a.`all_board_list_no`
	 where `card_no` = old.`card_no`;
	INSERT INTO `TIMELINE`
	SET
    	`TABLE_NAME` = 'KANBAN_CARD',
		`COLUMN_NAME` = kanban_list_name,
		`COLUMN_NO` = NEW.`CARD_NO`,
        `ALL_BOARD_NO` = all_board_list_no1,
        `OLD_HISTORY` = OLD.`TITLE`,
		`HISTORY` = NEW.`TITLE`,
		`DML_KIND` = 'update',
        `HISTORY_TIME` = now(),
		`TEAM_NO` = team_no1,
		`ID` = NEW.ID;
END $$
DELIMITER ;

-- 칸반보드카드 delete시 타임라인 테이블에 로그입력하기
DELIMITER $$
CREATE TRIGGER `TIMELINE_DELETE_KANBANCARD_TRIGGER`
before delete ON `KANBAN_CARD`
FOR EACH ROW 
BEGIN
	DECLARE kanban_list_name varchar(50);
    DECLARE team_no1 int;
    DECLARE all_board_list_no1 varchar(50);
	select `list_title`, `team_no`, l.`all_board_list_no` into kanban_list_name, team_no1, all_board_list_no1
	  from `KANBAN_CARD` c
	  join `KANBAN_LIST` l
		on c.`kanban_list_no` = l.`kanban_list_no`
	  join `all_board_list` a
		on l.`all_board_list_no` = a.`all_board_list_no`
	 where `card_no` = old.`card_no`;
	INSERT INTO `TIMELINE`
	SET
    	`TABLE_NAME` = 'KANBAN_CARD',
		`COLUMN_NAME` = kanban_list_name,
		`COLUMN_NO` = OLD.`KANBAN_LIST_NO`,
        `ALL_BOARD_NO` = all_board_list_no1,
		`HISTORY` = OLD.`TITLE`,
		`DML_KIND` = 'delete',
        `HISTORY_TIME` = now(),
		`TEAM_NO` = team_no1,
		`ID` = OLD.`ID`;
END $$
DELIMITER ;

-- ---------------------- BOARD_COMMENT
-- 댓글 추가시 타임라인 테이블에 로그입력하기
DELIMITER $$
CREATE TRIGGER `TIMELINE_INSERT_BOARDCOMMENT_TRIGGER`
AFTER INSERT ON `BOARD_COMMENT`
FOR EACH ROW 
BEGIN
	DECLARE titl1 varchar(50);
    DECLARE board_card_no1 int;
    DECLARE team_no1 int;
	SELECT IF(b.board_no is null, kc.title, bl.title) AS title, IF(b.board_no is null, b.card_no, b.board_no) AS board_card_no, 
		   IF(b.board_no is null, abl2.team_no, abl.team_no) AS team_no into titl1, board_card_no1, team_no1
	  FROM board_comment b
	  LEFT JOIN board_list AS bl ON (b.board_no is not null AND b.board_no = bl.board_no)
	  LEFT JOIN kanban_card AS kc ON (b.board_no is null AND b.card_no = kc.card_no)
	  LEFT JOIN all_board_list AS abl ON (b.board_no is not null AND bl.all_board_list_no = abl.all_board_list_no)
	  LEFT JOIN kanban_list AS kl ON (b.board_no is null AND kc.kanban_list_no = kl.kanban_list_no)
	  LEFT JOIN all_board_list AS abl2 ON (b.board_no is null AND kl.all_board_list_no = abl2.all_board_list_no)
	 where comment_no = new.comment_no;
	INSERT INTO `TIMELINE`
	SET
    	`TABLE_NAME` = 'BOARD_COMMENT',
		`COLUMN_NAME` = titl1,
		`COLUMN_NO` = NEW.`COMMENT_NO`,
		`HISTORY` = NEW.`CONTENT`,
		`DML_KIND` = 'insert',
        `HISTORY_TIME` = now(),
		`TEAM_NO` = team_no1,
		`ID` = NEW.`ID`;
END $$
DELIMITER ;

-- 일반게시판 게시물 수정시 타임라인 테이블에 로그입력하기
DELIMITER $$
CREATE TRIGGER `TIMELINE_UPDATE_BOARDCOMMENT_TRIGGER`
AFTER update ON `BOARD_COMMENT`
FOR EACH ROW 
BEGIN
	DECLARE titl1 varchar(50);
    DECLARE board_card_no1 int;
    DECLARE team_no1 int;
	SELECT IF(b.board_no is null, kc.title, bl.title) AS title, IF(b.board_no is null, b.card_no, b.board_no) AS board_card_no, 
		   IF(b.board_no is null, abl2.team_no, abl.team_no) AS team_no into titl1, board_card_no1, team_no1
	  FROM board_comment b
	  LEFT JOIN board_list AS bl ON (b.board_no is not null AND b.board_no = bl.board_no)
	  LEFT JOIN kanban_card AS kc ON (b.board_no is null AND b.card_no = kc.card_no)
	  LEFT JOIN all_board_list AS abl ON (b.board_no is not null AND bl.all_board_list_no = abl.all_board_list_no)
	  LEFT JOIN kanban_list AS kl ON (b.board_no is null AND kc.kanban_list_no = kl.kanban_list_no)
	  LEFT JOIN all_board_list AS abl2 ON (b.board_no is null AND kl.all_board_list_no = abl2.all_board_list_no)
	 where comment_no = old.comment_no;
	INSERT INTO `TIMELINE`
	SET
    	`TABLE_NAME` = 'BOARD_COMMENT',
		`COLUMN_NAME` = titl1,
		`COLUMN_NO` = NEW.`COMMENT_NO`,
        `OLD_HISTORY` = OLD.`CONTENT`,
		`HISTORY` = NEW.`CONTENT`,
		`DML_KIND` = 'update',
        `HISTORY_TIME` = now(),
		`TEAM_NO` = team_no1,
		`ID` = NEW.`ID`;
END $$
DELIMITER ;

-- 일반게시판 게시물 삭제시 타임라인 테이블에 로그입력하기
DELIMITER $$
CREATE TRIGGER `TIMELINE_DELETE_BOARDCOMMENT_TRIGGER`
before delete ON `BOARD_COMMENT`
FOR EACH ROW 
BEGIN
	DECLARE titl1 varchar(50);
    DECLARE board_card_no1 int;
    DECLARE team_no1 int;
	SELECT IF(b.board_no is null, kc.title, bl.title) AS title, IF(b.board_no is null, b.card_no, b.board_no) AS board_card_no, 
		   IF(b.board_no is null, abl2.team_no, abl.team_no) AS team_no into titl1, board_card_no1, team_no1
	  FROM board_comment b
	  LEFT JOIN board_list AS bl ON (b.board_no is not null AND b.board_no = bl.board_no)
	  LEFT JOIN kanban_card AS kc ON (b.board_no is null AND b.card_no = kc.card_no)
	  LEFT JOIN all_board_list AS abl ON (b.board_no is not null AND bl.all_board_list_no = abl.all_board_list_no)
	  LEFT JOIN kanban_list AS kl ON (b.board_no is null AND kc.kanban_list_no = kl.kanban_list_no)
	  LEFT JOIN all_board_list AS abl2 ON (b.board_no is null AND kl.all_board_list_no = abl2.all_board_list_no)
	 where comment_no = old.comment_no;
	INSERT INTO `TIMELINE`
	SET
    	`TABLE_NAME` = 'BOARD_COMMENT',
		`COLUMN_NAME` = titl1,
		`COLUMN_NO` = OLD.`COMMENT_NO`,
		`HISTORY` = OLD.`CONTENT`,
		`DML_KIND` = 'delete',
        `HISTORY_TIME` = now(),
		`TEAM_NO` = team_no1,
		`ID` = OLD.`ID`;
END $$
DELIMITER ;

-- ------------------- TODO_LIST
-- 투두리스트 insert시 타임라인 테이블에 로그입력하기
DELIMITER $$
CREATE TRIGGER `TIMELINE_INSERT_TODOLIST_TRIGGER`
AFTER INSERT ON `TODO_LIST`
FOR EACH ROW 
BEGIN
	INSERT INTO `TIMELINE`
	SET
    	`TABLE_NAME` = 'TODO_LIST',
		`COLUMN_NAME` = NEW.`TITLE`,
		`COLUMN_NO` = NEW.`NO`,
		`HISTORY` = NEW.`TITLE`,
		`DML_KIND` = 'insert',
        `HISTORY_TIME` = now(),
		`TEAM_NO` = NEW.`TEAM_NO`,
		`ID` = NEW.`ID`;
END $$
DELIMITER ;

-- TODOLIST delete시 타임라인 테이블에 로그입력하기
DELIMITER $$
CREATE TRIGGER `TIMELINE_DELETE_TODOLIST_TRIGGER`
before delete ON `TODO_LIST`
FOR EACH ROW 
BEGIN
	INSERT INTO `TIMELINE`
	SET
    	`TABLE_NAME` = 'TODO_LIST',
		`COLUMN_NAME` = OLD.`TITLE`,
		`COLUMN_NO` = OLD.`NO`,
		`HISTORY` = OLD.`TITLE`,
		`DML_KIND` = 'delete',
        `HISTORY_TIME` = now(),
		`TEAM_NO` = OLD.`TEAM_NO`,
		`ID` = OLD.`ID`;
END $$
DELIMITER ;

-- TODOLIST update시 타임라인 테이블에 로그입력하기
DELIMITER $$
CREATE TRIGGER `TIMELINE_UPDATE_TODOLIST_TRIGGER`
AFTER update ON `TODO_LIST`
FOR EACH ROW 
BEGIN
	INSERT INTO `TIMELINE`
	SET
    	`TABLE_NAME` = 'KANBAN_LIST',
		`COLUMN_NAME` = NEW.`TITLE`,
		`COLUMN_NO` = NEW.`NO`,
        `OLD_HISTORY` = OLD.`TITLE`,
		`HISTORY` = NEW.`TITLE`,
		`DML_KIND` = 'update',
        `HISTORY_TIME` = now(),
		`TEAM_NO` = NEW.`TEAM_NO`,
		`ID` = NEW.`ID`;
END $$
DELIMITER ;

-- ------------------- KANBAN_CARD
-- TODOCONTENT insert시 타임라인 테이블에 로그입력하기
DELIMITER $$
CREATE TRIGGER `TIMELINE_INSERT_TODOCONTENT_TRIGGER`
AFTER INSERT ON `TODO_CONTENT`
FOR EACH ROW 
BEGIN
	DECLARE todo_list_title varchar(50);
    DECLARE team_no1 int;
	select title, team_no into todo_list_title, team_no1
	  from todo_content c
	  join todo_list l
		on c.no = l.no
	 where todo_content_no = NEW.todo_content_no;
	INSERT INTO `TIMELINE`
	SET
    	`TABLE_NAME` = 'TODO_CONTENT',
		`COLUMN_NAME` = todo_list_title,
		`COLUMN_NO` = NEW.`todo_content_no`,
		`HISTORY` = NEW.`content`,
		`DML_KIND` = 'insert',
        `HISTORY_TIME` = now(),
		`TEAM_NO` = team_no1,
		`ID` = NEW.`ID`;
END $$
DELIMITER ;

-- TODOCONTENT delete시 타임라인 테이블에 로그입력하기
DELIMITER $$
CREATE TRIGGER `TIMELINE_DELETE_TODOCONTENT_TRIGGER`
before delete ON `TODO_CONTENT`
FOR EACH ROW 
BEGIN
	DECLARE todo_list_title varchar(50);
    DECLARE team_no1 int;
	select title, team_no into todo_list_title, team_no1
	  from todo_content c
	  join todo_list l
		on c.no = l.no
	 where todo_content_no = OLD.todo_content_no;
	INSERT INTO `TIMELINE`
	SET
    	`TABLE_NAME` = 'TODO_CONTENT',
		`COLUMN_NAME` = todo_list_title,
		`COLUMN_NO` = OLD.`todo_content_no`,
		`HISTORY` = OLD.`content`,
		`DML_KIND` = 'delete',
        `HISTORY_TIME` = now(),
		`TEAM_NO` = team_no1,
		`ID` = OLD.`ID`;
END $$
DELIMITER ;

-- 트리거가 만들어졌는지 확인
SHOW TRIGGERS;