-- 사용자
CREATE TABLE `USER` (
	`ID`       VARCHAR(50)   NOT NULL, -- 아이디
	`PWD`      VARCHAR(1000) NOT NULL, -- 비밀번호
	`NICKNAME` VARCHAR(50)   NOT NULL, -- 닉네임
    `IMAGE`    VARCHAR(200)  default 'user.png', -- 이미지
	`ENABLE`   INT           NOT NULL default 1, -- 사용여부
	`QUIT`     VARCHAR(2)    NOT NULL default 0  -- 탈퇴여부
);

-- 사용자
ALTER TABLE `USER`
	ADD CONSTRAINT `PK_USER` -- 사용자 기본키
		PRIMARY KEY (
			`ID` -- 아이디
		);

-- 게시판
CREATE TABLE `BOARD_LIST` (
	`BOARD_NO`          INT          NOT NULL,        -- 글식별번호
	`TITLE`             VARCHAR(100) NOT NULL,        -- 글제목
	`CONTENT`           TEXT         NOT NULL,        -- 글내용
	`VIEWS`             INT          NULL default 0,  -- 조회수
	`WRITE_DATE`        DATETIME     NOT NULL,        -- 작성일
	`COMMENT_COUNT`     INT          NULL default 0,  -- 댓글개수
    `FILE_COUNT`        INT          default 0,       -- 파일 유무
	`REFER`             INT          NULL default 0,  -- 그룹번호
	`DEPTH`             INT          NULL default 0,  -- 들여쓰기
	`STEP`              INT          NULL default 0,  -- 답변정렬
    `BOARD_NOTI`        VARCHAR(2)   NULL,            -- 공지사항 여부
	`ALL_BOARD_LIST_NO` INT          NULL,            -- 게시판식별번호
	`ID`                VARCHAR(50)  NULL             -- 아이디
);

-- 게시판
ALTER TABLE `BOARD_LIST`
	ADD CONSTRAINT `PK_BOARD_LIST` -- 게시판 기본키
		PRIMARY KEY (
			`BOARD_NO` -- 글식별번호
		);
        
-- 게시판 시퀀스
ALTER TABLE `BOARD_LIST` modify `BOARD_NO` INT auto_increment;

-- 게시판종류
CREATE TABLE `BOARD_TYPE` (
	`BOARD_TYPE_NO`   INT          NOT NULL, -- 게시판종류식별번호
	`BOARD_TYPE_NAME` VARCHAR(100) NOT NULL  -- 게시판종류이름
);

-- 게시판종류
ALTER TABLE `BOARD_TYPE`
	ADD CONSTRAINT `PK_BOARD_TYPE` -- 게시판종류 기본키
		PRIMARY KEY (
			`BOARD_TYPE_NO` -- 게시판종류식별번호
		);
        
-- 게시판종류 시퀀스
ALTER TABLE `BOARD_TYPE` modify `BOARD_TYPE_NO` INT auto_increment;

-- 클라우드
CREATE TABLE `BOARD_FILE` (
	`FILE_NO`           INT          NOT NULL, -- 파일식별번호
	`FILE_NAME`         VARCHAR(300) NOT NULL, -- 파일이름
    `ORIGIN_FILE_NAME`  VARCHAR(300) NOT NULL, -- 원본파일이름
	`FILE_SIZE`         INT          NOT NULL, -- 파일크기
	`FILE_PATH`			VARCHAR(2000)NULL,	   -- 파일경로
	`ALL_BOARD_LIST_NO` INT          NULL,     -- 게시판식별번호
	`BOARD_NO`          INT          NULL,     -- 글식별번호
	`CARD_NO`           INT          NULL      -- 카드식별번호
);

-- 클라우드
ALTER TABLE `BOARD_FILE`
	ADD CONSTRAINT `PK_BOARD_FILE` -- 클라우드 기본키2
		PRIMARY KEY (
			`FILE_NO` -- 파일식별번호
		);
        
-- 클라우드 시퀀스
ALTER TABLE `BOARD_FILE` modify `FILE_NO` INT auto_increment;

-- 게시판댓글
CREATE TABLE `BOARD_COMMENT` (
	`COMMENT_NO` INT          NOT NULL, -- 댓글식별번호
	`CONTENT`    VARCHAR(600) NOT NULL, -- 댓글내용
	`WRITE_DATE` DATETIME     NOT NULL, -- 댓글작성일
	`ID`         VARCHAR(50)  NOT NULL, -- 아이디
	`BOARD_NO`   INT          NULL,     -- 글식별번호
	`CARD_NO`    INT          NULL      -- 카드식별번호
);

-- 게시판댓글
ALTER TABLE `BOARD_COMMENT`
	ADD CONSTRAINT `PK_BOARD_COMMENT` -- 게시판댓글 기본키
		PRIMARY KEY (
			`COMMENT_NO` -- 댓글식별번호
		);
        
-- 자유게시판댓글 시퀀스
ALTER TABLE `BOARD_COMMENT` modify `COMMENT_NO` INT auto_increment;

-- 칸반보드카드
CREATE TABLE `KANBAN_CARD` (
	`CARD_NO`        INT           NOT NULL,   -- 카드식별번호
    `ID`			 VARCHAR(50)   NOT NULL,   -- 카드 작성자
	`TITLE`          VARCHAR(100)  NOT NULL,   -- 글제목
	`CONTENT`        VARCHAR(2000) NULL,       -- 글내용
	`WRITE_DATE`     DATETIME      NOT NULL,   -- 작성일
	`FILE_COUNT`     INT           default 0,  -- 파일개수
	`COMMENT_COUNT`  INT           default 0,  -- 댓글개수
    `CARD_INDEX`     INT           NULL,   	   -- 카드순서
	`KANBAN_LIST_NO` INT           NOT NULL    -- 칸반리스트 식별번호
);

-- 칸반보드카드
ALTER TABLE `KANBAN_CARD`
	ADD CONSTRAINT `PK_KANBAN_CARD` -- 칸반보드카드 기본키
		PRIMARY KEY (
			`CARD_NO` -- 카드식별번호
		);
        
ALTER TABLE `KANBAN_CARD`
	ADD CONSTRAINT `FK_USER_TO_KANBAN_CARD` -- 사용자 -> 게시판
		FOREIGN KEY (
			`ID` -- 아이디
		)
		REFERENCES `USER` ( -- 사용자
			`ID` -- 아이디
		) ON DELETE CASCADE;
        
-- 칸반보드카드 시퀀스
ALTER TABLE `KANBAN_CARD` modify `CARD_NO` INT auto_increment;

-- 칸반보드리스트
CREATE TABLE `KANBAN_LIST` (
	`KANBAN_LIST_NO`    INT          NOT NULL, -- 칸반리스트 식별번호
    `KANBAN_LIST_INDEX` INT			 NOT NULL, -- 칸반리스트 인덱스
	`LIST_TITLE`        VARCHAR(100) NOT NULL, -- 칸반리스트 이름
	`ALL_BOARD_LIST_NO` INT          NULL,     -- 게시판식별번호
	`ID`                VARCHAR(50)  NULL      -- 아이디
);

-- 칸반보드리스트
ALTER TABLE `KANBAN_LIST`
	ADD CONSTRAINT `PK_KANBAN_LIST` -- 칸반보드리스트 기본키
		PRIMARY KEY (
			`KANBAN_LIST_NO` -- 칸반리스트 식별번호
		);
        
-- 칸반보드리스트 시퀀스
ALTER TABLE `KANBAN_LIST` modify `KANBAN_LIST_NO` INT auto_increment;

-- 팀
CREATE TABLE `TEAM` (
	`TEAM_NO`          INT         NOT NULL, -- 팀식별번호
	`TEAM_NAME`        VARCHAR(50) NOT NULL, -- 팀명
    `BACKGROUND_COLOR` VARCHAR(50) default 'ffffff' -- 배경색
);

-- 팀
ALTER TABLE `TEAM`
	ADD CONSTRAINT `PK_TEAM` -- 팀 기본키
		PRIMARY KEY (
			`TEAM_NO` -- 팀식별번호
		);
        
-- 팀 시퀀스
ALTER TABLE `TEAM` modify `TEAM_NO` INT auto_increment;

-- 그룹
CREATE TABLE `GROUP` (
	`GROUP_NO`   INT         NOT NULL, -- 그룹식별번호
	`ID`         VARCHAR(50) NOT NULL, -- 아이디
	`GROUP_NAME` VARCHAR(50) NOT NULL  -- 그룹명
);

-- 그룹
ALTER TABLE `GROUP`
	ADD CONSTRAINT `PK_GROUP` -- 그룹 기본키
		PRIMARY KEY (
			`GROUP_NO`, -- 그룹식별번호
			`ID`        -- 아이디
		);
        
-- 그룹 시퀀스
ALTER TABLE `GROUP` modify `GROUP_NO` INT auto_increment;

-- 팀구성원
CREATE TABLE `TEAM_MEMBER` (
	`TEAM_NO`  INT         NOT NULL, -- 팀식별번호
	`ID`       VARCHAR(50) NOT NULL, -- 아이디
	`LEADER`   VARCHAR(2)  NOT NULL default 'N',  -- 팀장구분
    `TEAM_OUT` VARCHAR(2)  default 'N' -- 팀탈퇴여부
);

-- 팀구성원
ALTER TABLE `TEAM_MEMBER`
	ADD CONSTRAINT `PK_TEAM_MEMBER` -- 팀구성원 기본키
		PRIMARY KEY (
			`TEAM_NO`, -- 팀식별번호
			`ID`       -- 아이디
		);
        
-- 팀구성원 시퀀스
ALTER TABLE `TEAM_MEMBER` modify `TEAM_NO` INT auto_increment;

-- 사용자-권한 매핑
CREATE TABLE `ROLE_MEMBER` (
	`AUTHORITY` VARCHAR(50) NOT NULL, -- 권한코드
	`ID`        VARCHAR(50) NOT NULL  -- 아이디
);

-- 사용자-권한 매핑
ALTER TABLE `ROLE_MEMBER`
	ADD CONSTRAINT `PK_ROLE_MEMBER` -- 사용자-권한 매핑 기본키
		PRIMARY KEY (
			`AUTHORITY`, -- 권한코드
			`ID`         -- 아이디
		);

-- 권한
CREATE TABLE `ROLE` (
	`AUTHORITY`   VARCHAR(50) NOT NULL, -- 권한코드
	`DESCRIPTION` VARCHAR(50) NOT NULL  -- 역할명
);

-- 권한
ALTER TABLE `ROLE`
	ADD CONSTRAINT `PK_ROLE` -- 권한 기본키
		PRIMARY KEY (
			`AUTHORITY` -- 권한코드
		);

-- 게시판목록
CREATE TABLE `ALL_BOARD_LIST` (
	`ALL_BOARD_LIST_NO`  INT          NOT NULL, -- 게시판식별번호
	`NAME`               VARCHAR(100) NULL,     -- 게시판이름
	`TEAM_NO`            INT          NULL,     -- 팀식별번호
	`ID`                 VARCHAR(50)  NULL,     -- 아이디
	`BOARD_TYPE_NO`      INT          NULL      -- 게시판종류식별번호
);

-- 게시판목록
ALTER TABLE `ALL_BOARD_LIST`
	ADD CONSTRAINT `PK_ALL_BOARD_LIST` -- 게시판목록 기본키
		PRIMARY KEY (
			`ALL_BOARD_LIST_NO` -- 게시판식별번호
		);
        
-- 게시판목록 시퀀스
ALTER TABLE `ALL_BOARD_LIST` modify `ALL_BOARD_LIST_NO` INT auto_increment;

-- 투두리스트내용
CREATE TABLE `TODO_CONTENT` (
	`TODO_CONTENT_NO` INT         NOT NULL,         -- 투두리스트내용식별번호
	`CONTENT`         VARCHAR(50) NOT NULL,         -- 투두리스트내용
	`STATE`           VARCHAR(20) default 'active', -- 완료여부
	`NO`              INT         NOT NULL,         -- 투두리스트목록식별번호
	`ID`              VARCHAR(50) NOT NULL          -- 아이디
);

-- 투두리스트내용
ALTER TABLE `TODO_CONTENT`
	ADD CONSTRAINT `PK_TODO_CONTENT` -- 투두리스트내용 기본키
		PRIMARY KEY (
			`TODO_CONTENT_NO` -- 투두리스트내용식별번호
		);
        
-- 투두리스트내용 시퀀스
ALTER TABLE `TODO_CONTENT` modify `TODO_CONTENT_NO` INT auto_increment;

-- 투두리스트목록
CREATE TABLE `TODO_LIST` (
	`NO`      INT          NOT NULL, -- 투두리스트목록식별번호
	`TITLE`   VARCHAR(100) NOT NULL, -- 투두리스트제목
	`TEAM_NO` INT          NOT NULL, -- 팀식별번호
	`ID`      VARCHAR(50)  NOT NULL  -- 아이디
);

-- 투두리스트목록
ALTER TABLE `TODO_LIST`
	ADD CONSTRAINT `PK_TODO_LIST` -- 투두리스트목록 기본키
		PRIMARY KEY (
			`NO` -- 투두리스트목록식별번호
		);
        
-- 투두리스트목록 시퀀스
ALTER TABLE `TODO_LIST` modify `NO` INT auto_increment;

-- 일정관리
CREATE TABLE `PLAN` (
	`NO`          INT           NOT NULL, -- 일정관리식별번호
	`TITLE`       VARCHAR(100)  DEFAULT NULL, -- 일정이름
	`DESCRIPTION` VARCHAR(1000) DEFAULT NULL,     -- 설명
	`START`       timestamp     DEFAULT NULL, -- 시작
	`END`         timestamp     DEFAULT NULL, -- 끝
    `ALLDAY`      VARCHAR(10)   NULL, -- 끝
	`COLOR`       VARCHAR(50)   DEFAULT NULL,     -- 글자색상
	`TEAM_NO`     INT           NOT NULL, -- 팀식별번호
	`ID`          VARCHAR(50)   NOT NULL  -- 아이디
);

-- 일정관리
ALTER TABLE `PLAN`
	ADD CONSTRAINT `PK_PLAN` -- 일정관리 기본키
		PRIMARY KEY (
			`NO` -- 일정관리식별번호
		);
        
-- 일정관리 시퀀스
ALTER TABLE `PLAN` modify `NO` INT auto_increment;

-- 타임라인
CREATE TABLE `TIMELINE` (
	`TIMELINE_NO`  INT          NOT NULL, -- 타임라인식별번호
    `TABLE_NAME`   VARCHAR(100) NOT NULL, -- 해당테이블 이름
	`COLUMN_NAME`  VARCHAR(100) NOT NULL, -- 해당테이블의 기본키 컬럼명
	`COLUMN_NO`    INT          NOT NULL, -- 해당테이블의 식별번호
    `ALL_BOARD_NO` INT          NULL,     -- 보드일경우 all_board_list_no
    `OLD_HISTORY`  VARCHAR(100) NULL,     -- 원래 내용
    `HISTORY`      VARCHAR(100) NOT NULL, -- 작업내용
    `DML_KIND`     varchar(20)  NOT NULL, -- 작업구분
    `HISTORY_TIME` DATETIME NOT NULL,     -- 작업시간
	`TEAM_NO`      INT          NOT NULL, -- 팀식별번호
	`ID`           VARCHAR(50)  NOT NULL  -- 아이디
);

-- 타임라인
ALTER TABLE `TIMELINE`
	ADD CONSTRAINT `PK_TIMELINE` -- 타임라인 기본키
		PRIMARY KEY (
			`TIMELINE_NO` -- 타임라인식별번호
		);
        
-- 타임라인 시퀀스
ALTER TABLE `TIMELINE` modify `TIMELINE_NO` INT auto_increment;

-- 그룹-팀 매핑
CREATE TABLE `GROUP_TEAM` (
	`GROUP_TEAM_NO` INT         NOT NULL, -- 그룹-팀식별번호
	`GROUP_NO`      INT         NULL,     -- 그룹식별번호
	`TEAM_NO`       INT         NULL,     -- 팀식별번호
	`ID`            VARCHAR(50) NULL      -- 아이디
);

-- 그룹-팀 매핑
ALTER TABLE `GROUP_TEAM`
	ADD CONSTRAINT `PK_GROUP_TEAM` -- 그룹-팀 매핑 기본키
		PRIMARY KEY (
			`GROUP_TEAM_NO` -- 그룹-팀식별번호
		);

-- 그룹-팀 매핑 시퀀스
ALTER TABLE `GROUP_TEAM` modify `GROUP_TEAM_NO` INT auto_increment;

-- 게시판
ALTER TABLE `BOARD_LIST`
	ADD CONSTRAINT `FK_ALL_BOARD_LIST_TO_BOARD_LIST` -- 게시판목록 -> 게시판
		FOREIGN KEY (
			`ALL_BOARD_LIST_NO` -- 게시판식별번호
		)
		REFERENCES `ALL_BOARD_LIST` ( -- 게시판목록
			`ALL_BOARD_LIST_NO` -- 게시판식별번호
		) ON DELETE CASCADE;

-- 게시판
ALTER TABLE `BOARD_LIST`
	ADD CONSTRAINT `FK_USER_TO_BOARD_LIST` -- 사용자 -> 게시판
		FOREIGN KEY (
			`ID` -- 아이디
		)
		REFERENCES `USER` ( -- 사용자
			`ID` -- 아이디
		) ON DELETE CASCADE;

-- 클라우드
ALTER TABLE `BOARD_FILE`
	ADD CONSTRAINT `FK_ALL_BOARD_LIST_TO_BOARD_FILE` -- 게시판목록 -> 클라우드2
		FOREIGN KEY (
			`ALL_BOARD_LIST_NO` -- 게시판식별번호
		)
		REFERENCES `ALL_BOARD_LIST` ( -- 게시판목록
			`ALL_BOARD_LIST_NO` -- 게시판식별번호
		) ON DELETE CASCADE;

-- 클라우드
ALTER TABLE `BOARD_FILE`
	ADD CONSTRAINT `FK_BOARD_LIST_TO_BOARD_FILE` -- 게시판 -> 클라우드
		FOREIGN KEY (
			`BOARD_NO` -- 글식별번호
		)
		REFERENCES `BOARD_LIST` ( -- 게시판
			`BOARD_NO` -- 글식별번호
		) ON DELETE CASCADE;

-- 클라우드
ALTER TABLE `BOARD_FILE`
	ADD CONSTRAINT `FK_KANBAN_CARD_TO_BOARD_FILE` -- 칸반보드카드 -> 클라우드
		FOREIGN KEY (
			`CARD_NO` -- 카드식별번호
		)
		REFERENCES `KANBAN_CARD` ( -- 칸반보드카드
			`CARD_NO` -- 카드식별번호
		) ON DELETE CASCADE;

-- 게시판댓글
ALTER TABLE `BOARD_COMMENT`
	ADD CONSTRAINT `FK_USER_TO_BOARD_COMMENT` -- 사용자 -> 게시판댓글
		FOREIGN KEY (
			`ID` -- 아이디
		)
		REFERENCES `USER` ( -- 사용자
			`ID` -- 아이디
		) ON DELETE CASCADE;

-- 게시판댓글
ALTER TABLE `BOARD_COMMENT`
	ADD CONSTRAINT `FK_BOARD_LIST_TO_BOARD_COMMENT` -- 게시판 -> 게시판댓글
		FOREIGN KEY (
			`BOARD_NO` -- 글식별번호
		)
		REFERENCES `BOARD_LIST` ( -- 게시판
			`BOARD_NO` -- 글식별번호
		) ON DELETE CASCADE;

-- 게시판댓글
ALTER TABLE `BOARD_COMMENT`
	ADD CONSTRAINT `FK_KANBAN_CARD_TO_BOARD_COMMENT` -- 칸반보드카드 -> 게시판댓글
		FOREIGN KEY (
			`CARD_NO` -- 카드식별번호
		)
		REFERENCES `KANBAN_CARD` ( -- 칸반보드카드
			`CARD_NO` -- 카드식별번호
		) ON DELETE CASCADE;

-- 칸반보드카드
ALTER TABLE `KANBAN_CARD`
	ADD CONSTRAINT `FK_KANBAN_LIST_TO_KANBAN_CARD` -- 칸반보드리스트 -> 칸반보드카드
		FOREIGN KEY (
			`KANBAN_LIST_NO` -- 칸반리스트 식별번호
		)
		REFERENCES `KANBAN_LIST` ( -- 칸반보드리스트
			`KANBAN_LIST_NO` -- 칸반리스트 식별번호
		) ON DELETE CASCADE;

-- 칸반보드리스트
ALTER TABLE `KANBAN_LIST`
	ADD CONSTRAINT `FK_ALL_BOARD_LIST_TO_KANBAN_LIST` -- 게시판목록 -> 칸반보드리스트
		FOREIGN KEY (
			`ALL_BOARD_LIST_NO` -- 게시판식별번호
		)
		REFERENCES `ALL_BOARD_LIST` ( -- 게시판목록
			`ALL_BOARD_LIST_NO` -- 게시판식별번호
		) ON DELETE CASCADE;

-- 칸반보드리스트
ALTER TABLE `KANBAN_LIST`
	ADD CONSTRAINT `FK_USER_TO_KANBAN_LIST` -- 사용자 -> 칸반보드리스트
		FOREIGN KEY (
			`ID` -- 아이디
		)
		REFERENCES `USER` ( -- 사용자
			`ID` -- 아이디
		) ON DELETE CASCADE;

-- 그룹
ALTER TABLE `GROUP`
	ADD CONSTRAINT `FK_USER_TO_GROUP` -- 사용자 -> 그룹
		FOREIGN KEY (
			`ID` -- 아이디
		)
		REFERENCES `USER` ( -- 사용자
			`ID` -- 아이디
		) ON DELETE CASCADE;

-- 팀구성원
ALTER TABLE `TEAM_MEMBER`
	ADD CONSTRAINT `FK_TEAM_TO_TEAM_MEMBER` -- 팀 -> 팀구성원
		FOREIGN KEY (
			`TEAM_NO` -- 팀식별번호
		)
		REFERENCES `TEAM` ( -- 팀
			`TEAM_NO` -- 팀식별번호
		) ON DELETE CASCADE;

-- 팀구성원
ALTER TABLE `TEAM_MEMBER`
	ADD CONSTRAINT `FK_USER_TO_TEAM_MEMBER` -- 사용자 -> 팀구성원
		FOREIGN KEY (
			`ID` -- 아이디
		)
		REFERENCES `USER` ( -- 사용자
			`ID` -- 아이디
		) ON DELETE CASCADE;

-- 사용자-권한 매핑
ALTER TABLE `ROLE_MEMBER`
	ADD CONSTRAINT `FK_ROLE_TO_ROLE_MEMBER` -- 권한 -> 사용자-권한 매핑
		FOREIGN KEY (
			`AUTHORITY` -- 권한코드
		)
		REFERENCES `ROLE` ( -- 권한
			`AUTHORITY` -- 권한코드
		) ON DELETE CASCADE;

-- 사용자-권한 매핑
ALTER TABLE `ROLE_MEMBER`
	ADD CONSTRAINT `FK_USER_TO_ROLE_MEMBER` -- 사용자 -> 사용자-권한 매핑
		FOREIGN KEY (
			`ID` -- 아이디
		)
		REFERENCES `USER` ( -- 사용자
			`ID` -- 아이디
		) ON DELETE CASCADE;

-- 게시판목록
ALTER TABLE `ALL_BOARD_LIST`
	ADD CONSTRAINT `FK_TEAM_MEMBER_TO_ALL_BOARD_LIST` -- 팀구성원 -> 게시판목록
		FOREIGN KEY (
			`TEAM_NO`, -- 팀식별번호
			`ID`       -- 아이디
		)
		REFERENCES `TEAM_MEMBER` ( -- 팀구성원
			`TEAM_NO`, -- 팀식별번호
			`ID`       -- 아이디
		) ON DELETE CASCADE;

-- 게시판목록
ALTER TABLE `ALL_BOARD_LIST`
	ADD CONSTRAINT `FK_BOARD_TYPE_TO_ALL_BOARD_LIST` -- 게시판종류 -> 게시판목록
		FOREIGN KEY (
			`BOARD_TYPE_NO` -- 게시판종류식별번호
		)
		REFERENCES `BOARD_TYPE` ( -- 게시판종류
			`BOARD_TYPE_NO` -- 게시판종류식별번호
		) ON DELETE CASCADE;

-- 투두리스트내용
ALTER TABLE `TODO_CONTENT`
	ADD CONSTRAINT `FK_TODO_LIST_TO_TODO_CONTENT` -- 투두리스트목록 -> 투두리스트내용
		FOREIGN KEY (
			`NO` -- 투두리스트목록식별번호
		)
		REFERENCES `TODO_LIST` ( -- 투두리스트목록
			`NO` -- 투두리스트목록식별번호
		) ON DELETE CASCADE;

-- 투두리스트내용
ALTER TABLE `TODO_CONTENT`
	ADD CONSTRAINT `FK_USER_TO_TODO_CONTENT` -- 사용자 -> 투두리스트내용
		FOREIGN KEY (
			`ID` -- 아이디
		)
		REFERENCES `USER` ( -- 사용자
			`ID` -- 아이디
		) ON DELETE CASCADE;

-- 투두리스트목록
ALTER TABLE `TODO_LIST`
	ADD CONSTRAINT `FK_TEAM_MEMBER_TO_TODO_LIST` -- 팀구성원 -> 투두리스트목록
		FOREIGN KEY (
			`TEAM_NO`, -- 팀식별번호
			`ID`       -- 아이디
		)
		REFERENCES `TEAM_MEMBER` ( -- 팀구성원
			`TEAM_NO`, -- 팀식별번호
			`ID`       -- 아이디
		) ON DELETE CASCADE;

-- 일정관리
ALTER TABLE `PLAN`
	ADD CONSTRAINT `FK_TEAM_MEMBER_TO_PLAN` -- 팀구성원 -> 일정관리
		FOREIGN KEY (
			`TEAM_NO`, -- 팀식별번호
			`ID`       -- 아이디
		)
		REFERENCES `TEAM_MEMBER` ( -- 팀구성원
			`TEAM_NO`, -- 팀식별번호
			`ID`       -- 아이디
		) ON DELETE CASCADE;

-- 타임라인
ALTER TABLE `TIMELINE`
	ADD CONSTRAINT `FK_TEAM_MEMBER_TO_TIMELINE` -- 팀구성원 -> 타임라인
		FOREIGN KEY (
			`TEAM_NO`, -- 팀식별번호
			`ID`       -- 아이디
		)
		REFERENCES `TEAM_MEMBER` ( -- 팀구성원
			`TEAM_NO`, -- 팀식별번호
			`ID`       -- 아이디
		) ON DELETE CASCADE;

-- 그룹-팀 매핑
ALTER TABLE `GROUP_TEAM`
	ADD CONSTRAINT `FK_GROUP_TO_GROUP_TEAM` -- 그룹 -> 그룹-팀 매핑
		FOREIGN KEY (
			`GROUP_NO`, -- 그룹식별번호
			`ID`        -- 아이디
		)
		REFERENCES `GROUP` ( -- 그룹
			`GROUP_NO`, -- 그룹식별번호
			`ID`        -- 아이디
		) ON DELETE CASCADE;

-- 그룹-팀 매핑
ALTER TABLE `GROUP_TEAM`
	ADD CONSTRAINT `FK_TEAM_TO_GROUP_TEAM` -- 팀 -> 그룹-팀 매핑
		FOREIGN KEY (
			`TEAM_NO` -- 팀식별번호
		)
		REFERENCES `TEAM` ( -- 팀
			`TEAM_NO` -- 팀식별번호
		) ON DELETE CASCADE;
        
        
-- remember me
CREATE TABLE `persistent_logins` (
  `username` varchar(64) DEFAULT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) DEFAULT NULL,
  `last_used` timestamp,
  PRIMARY KEY (`series`)
);

-- 칸반 카드 정보 한번에 보기(카드번호, 작성자, 리스트번호, 게시판번호, 팀번호) 뷰        
CREATE VIEW `CARDS_INFO` AS
	SELECT KC.`CARD_NO`, KC.`ID`, U.`NICKNAME`, KL.`KANBAN_LIST_NO`, AL.`ALL_BOARD_LIST_NO`, AL.`TEAM_NO`
	FROM `KANBAN_CARD` AS KC
		JOIN `KANBAN_LIST` AS KL
			ON KC.`KANBAN_LIST_NO` = KL.`KANBAN_LIST_NO`
		JOIN `ALL_BOARD_LIST` AS AL
			ON KL.`ALL_BOARD_LIST_NO` = AL.`ALL_BOARD_LIST_NO`
		JOIN `USER` AS U
			ON KC.`ID` = U.`ID`;
            
            
-- 게시글 정보 한눈에 보기 (게시글 번호, 작성자, 게시판번호, 팀번호) 뷰       
CREATE VIEW `BOARDS_INFO` AS
	SELECT BL.`BOARD_NO`, BL.`ID`, U.`NICKNAME`, AL.`ALL_BOARD_LIST_NO`, AL.`TEAM_NO`
	FROM `BOARD_LIST` AS BL
		JOIN `ALL_BOARD_LIST` AS AL
			ON BL.`ALL_BOARD_LIST_NO` = AL.`ALL_BOARD_LIST_NO`		
		JOIN `USER` AS U
			ON BL.`ID` = U.`ID`;
            

-- 전체 게시물(칸반+게시글) 요약 정보(작성자, 닉네임, 팀번호)
CREATE VIEW `TOTAL_POSTS_SUMMARY` AS
	select `card_no` as `no`, `id`, `NICKNAME`, `team_no` from `CARDS_INFO`
	UNION ALL
	select `BOARD_NO` as `no`, `id`, `NICKNAME`, `team_no` from `BOARDS_INFO`;
