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

-- -------------------
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
AFTER delete ON `TEAM_MEMBER`
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
AFTER delete ON `BOARD_LIST`
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
		`COLUMN_NO` = OLD.`BOARD_NO`,
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
        `OLD_HISTORY` = OLD.`TITLE`,
		`HISTORY` = NEW.`TITLE`,
		`DML_KIND` = 'update',
        `HISTORY_TIME` = now(),
		`TEAM_NO` = team_no1,
		`ID` = NEW.`ID`;
END $$
DELIMITER ;

-- ------------------- 여기부터 만들어야댐 위에 보드 수정, 삭제는 확인 못해봄
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
		`COLUMN_NAME` = board_name,
		`COLUMN_NO` = NEW.`KANBAN_LIST_NO`,
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
AFTER delete ON `KANBAN_LIST`
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
		`COLUMN_NAME` = board_name,
		`COLUMN_NO` = OLD.`KANBAN_LIST_NO`,
		`HISTORY` = OLD.`LIST_TITLE`,
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
		`COLUMN_NAME` = board_name,
		`COLUMN_NO` = NEW.`BOARD_NO`,
        `OLD_HISTORY` = OLD.`TITLE`,
		`HISTORY` = NEW.`TITLE`,
		`DML_KIND` = 'update',
        `HISTORY_TIME` = now(),
		`TEAM_NO` = team_no1,
		`ID` = NEW.`ID`;
END $$
DELIMITER ;



-- 트리거가 만들어졌는지 확인
SHOW TRIGGERS;