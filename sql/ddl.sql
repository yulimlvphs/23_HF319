CREATE TABLE SCHOOL_TB
(
    id    bigint generated by default as identity,
    SCHUL_RDNMA	VARCHAR(512),
    SCHUL_FOND_TYP_CODE	VARCHAR(512),
    FOAS_MEMRD	LONG,
    DGHT_SC_CODE	VARCHAR(512),
    FOND_SC_CODE	VARCHAR(512),
    USER_TELNO_SW	VARCHAR(512),
    LTTUD	DOUBLE,
    ZIP_CODE LONG NULL ,
    SCHUL_KND_SC_CODE	LONG,
    LGTUD	DOUBLE,
    SCHUL_RDNZC	LONG,
    DTLAD_BRKDN	VARCHAR(512),
    USER_TELNO	VARCHAR(512),
    ADRCD_NM	VARCHAR(512),
    COEDU_SC_CODE	VARCHAR(512),
    PERC_FAXNO	VARCHAR(512),
    JU_ORG_NM	VARCHAR(512),
    ATPT_OFCDC_ORG_NM	VARCHAR(512),
    JU_ORG_CODE	VARCHAR(512),
    HMPG_ADRES	VARCHAR(512),
    ADRES_BRKDN	VARCHAR(512),
    SCHUL_CODE	VARCHAR(512),
    FOND_YMD	LONG,
    ATPT_OFCDC_ORG_CODE	VARCHAR(512),
    LCTN_SC_CODE	LONG,
    ADRCD_CD	LONG NULL,
    ADRCD_ID	LONG,
    HS_KND_SC_NM	VARCHAR(512),
    SCHUL_RDNDA	VARCHAR(512),
    BNHH_YN	VARCHAR(512),
    SCHUL_NM	VARCHAR(512),
    USER_TELNO_GA	VARCHAR(512),
    primary key (id)
);

/*회원가입 테이블 create문*/
CREATE TABLE user_TB
(
    id    bigint generated by default as identity,
    uid    varchar(30),
    pwd    varchar(100),
    name  varchar(30),
    email varchar(30),
    gender   varchar(10),
    age   Int,
    role   bigint,
    primary key (id)
);

/* 게시판 테이블 */
CREATE TABLE board_TB
(
    id    bigint generated by default as identity,
    title varchar(1000),
    content  varchar(1000),
    category bigint,
    schNo bigint,
    userNo     bigint,
    userName   varchar(30),
    createDt   datetime,
    updateDt   datetime,
    PRIMARY KEY (id)
);
/* 댓글 테이블 */
CREATE TABLE comment_TB
(
    id   bigint generated by default as identity,
    cmt varchar(1000),
    boardNo     bigint,
    userNo     bigint,
    userName   varchar(30),
    createDt   datetime,
    updateDt   datetime,
    PRIMARY KEY (id)
);

ALTER TABLE USER_TB
    ALTER COLUMN id RESTART WITH 1;