DROP TABLE IF EXISTS `POST`;

CREATE TABLE `POST` (
	`POST_NO`	INT	NOT NULL	COMMENT 'POST_NO',
	`WRITER`	VARCHAR(50)	NOT NULL	COMMENT 'POST_WRITER',
	`TITLE`	VARCHAR(100)	NOT NULL	COMMENT 'POST_TITLE',
	`CONTENT_AS_MARKDOWN`	TEXT	NOT NULL	COMMENT 'POST_CONTENT WRITTEN USING MARKDOWN',
	`CONTENT_AS_HTML`	TEXT	NOT NULL	COMMENT 'POAST_CONTENT CONVERTED FROM MARKDOWN TO HTML',
	`VIEW_COUNT`	INT	NOT NULL	COMMENT 'HOW MANY TIMES USERS READ THIS POST',
	`VISIBLE_YN`	CHAR(1)	NOT NULL	COMMENT 'IF IT IS "N", THE POST CAN BE SEEN AND ONLY BY ADMIN',
	`REG_DATE`	DATE	NOT NULL	COMMENT 'THE DATE WHEN THE POST GOT POSTED',
	`UPD_DATE`	DATE	NULL	COMMENT 'THE LAST DATE WHEN THE POST GOT UPDATED',
	`LAST_CHG_USER_ID`	VARCHAR(50)	NOT NULL	COMMENT 'COMMON INFORMATION FOR ALL TABLES',
	`LAST_CHG_DATETIME`	DATETIME	NOT NULL	COMMENT 'COMMON INFORMATION FOR ALL TABLES',
	`LAST_CHG_IP`	VARCHAR(50)	NOT NULL	COMMENT 'COMMON INFORMATION FOR ALL TABLES'
);

ALTER TABLE `POST` ADD CONSTRAINT `PK_POST` PRIMARY KEY (
	`POST_NO`
);

COMMIT;
