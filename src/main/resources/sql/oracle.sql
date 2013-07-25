-- Create table
create table ECV_XML
(
  ID                   NUMBER(10) not null,
  FNAME                VARCHAR2(30),
  LNAME                VARCHAR2(30),
  ADDRESS              VARCHAR2(50),
  MUNIC                VARCHAR2(50),
  POSTAL_CODE          VARCHAR2(10),
  CODE_COUNTRY         VARCHAR2(5),
  COUNTRY              VARCHAR2(30),
  PHONE                VARCHAR2(30),
  FAX                  VARCHAR2(30),
  MOBILE               VARCHAR2(30),
  EMAIL                VARCHAR2(50),
  GENDER               VARCHAR2(2),
  BIRTHDATE            VARCHAR2(10),
  PHOTO_TYPE           VARCHAR2(10),
  PHOTO                BLOB,
  CODE_APPLICATION     VARCHAR2(10),
  APPLICATION          VARCHAR2(50),
  CODE_MOTHER_LANGUAGE VARCHAR2(5),
  MOTHER_LANGUAGE      VARCHAR2(100),
  SOCIAL               VARCHAR2(1024),
  ORGANISATIONAL       VARCHAR2(1024),
  TECHNICAL            VARCHAR2(1024),
  COMPUTER             VARCHAR2(1024),
  ARTISTIC             VARCHAR2(1024),
  OTHER                VARCHAR2(1024),
  ADDITIONAL           VARCHAR2(1024),
  ANNEXES              VARCHAR2(1024)
)
tablespace TBS_ED
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate primary, unique and foreign key constraints 
alter table ECV_XML
  add constraint ECV_XML_PK primary key (ID)
  using index 
  tablespace TBS_ED
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

-- Create table
create table ECV_WORK_EXPERIENCE
(
  ID               NUMBER(10) not null,
  XML_ID           NUMBER(10),
  DAY_FROM         VARCHAR2(2),
  MONTH_FROM       VARCHAR2(2),
  YEAR_FROM        VARCHAR2(4),
  DAY_TO           VARCHAR2(2),
  MONTH_TO         VARCHAR2(2),
  YEAR_TO          VARCHAR2(4),
  CODE_POSITION    VARCHAR2(6),
  WPOSITION        VARCHAR2(30),
  ACTIVITIES       VARCHAR2(100),
  EMPLOYER_NAME    VARCHAR2(50),
  EMPLOYER_ADDRESS VARCHAR2(50),
  EMPLOYER_MUNIC   VARCHAR2(50),
  EMPLOYER_ZCODE   VARCHAR2(10),
  CODE_COUNTRY     VARCHAR2(3),
  COUNTRY          VARCHAR2(30),
  CODE_SECTOR      VARCHAR2(3),
  SECTOR           VARCHAR2(50)
)
tablespace TBS_ED
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate primary, unique and foreign key constraints 
alter table ECV_WORK_EXPERIENCE
  add constraint ECV_WORK_EXPERIENCE_PK primary key (ID)
  using index 
  tablespace TBS_ED
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table ECV_WORK_EXPERIENCE
  add constraint ECV_WORK_EXPERIENCE_IBFK_1 foreign key (XML_ID)
  references ECV_XML (ID)
  disable;

-- Create table
create table ECV_NATIONALITY
(
  ID          NUMBER(10),
  XML_ID      NUMBER(10),
  CODE        VARCHAR2(10),
  NATIONALITY VARCHAR2(32)
)
tablespace TBS_ED
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate primary, unique and foreign key constraints 
alter table ECV_NATIONALITY
  add constraint ECV_NATIONALITY_IBFK_1 foreign key (XML_ID)
  references ECV_XML (ID)
  disable;

-- Create table
create table ECV_LANGUAGE
(
  ID                 NUMBER(10) not null,
  XML_ID             NUMBER(10),
  CODE_LANGUAGE      VARCHAR2(3),
  OLANGUAGE          VARCHAR2(30),
  LISTENING          VARCHAR2(2),
  READING            VARCHAR2(2),
  SPOKEN_INTERACTION VARCHAR2(2),
  SPOKEN_PRODUCTION  VARCHAR2(2),
  WRITING            VARCHAR2(2) not null
)
tablespace TBS_ED
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate primary, unique and foreign key constraints 
alter table ECV_LANGUAGE
  add constraint ECV_LANGAUGE_PK primary key (ID)
  using index 
  tablespace TBS_ED
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table ECV_LANGUAGE
  add constraint ECV_LANGAUGE_IBFK_1 foreign key (XML_ID)
  references ECV_XML (ID)
  disable;

-- Create table
create table ECV_EDUCATION
(
  ID             NUMBER(10) not null,
  XML_ID         NUMBER(10),
  TITLE          VARCHAR2(50),
  SUBJECT        VARCHAR2(200),
  ORG_NAME       VARCHAR2(100),
  ORG_TYPE       VARCHAR2(50),
  ORG_ADDRESS    VARCHAR2(100),
  ORG_MUNIC      VARCHAR2(50),
  ORG_ZCODE      VARCHAR2(10),
  CODE_COUNTRY   VARCHAR2(3),
  COUNTRY        VARCHAR2(50),
  CODE_LEVEL     VARCHAR2(2),
  EDULEVEL       VARCHAR2(30),
  CODE_EDU_FIELD VARCHAR2(5),
  EDU_FIELD      VARCHAR2(50),
  DAY_FROM       VARCHAR2(2),
  MONTH_FROM     VARCHAR2(2),
  YEAR_FROM      VARCHAR2(4),
  DAY_TO         VARCHAR2(2),
  MONTH_TO       VARCHAR2(2),
  YEAR_TO        VARCHAR2(4)
)
tablespace TBS_ED
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate primary, unique and foreign key constraints 
alter table ECV_EDUCATION
  add constraint ECV_EDUCATION_PK primary key (ID)
  using index 
  tablespace TBS_ED
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table ECV_EDUCATION
  add constraint ECV_EDUCATION_IBFK_1 foreign key (XML_ID)
  references ECV_XML (ID)
  disable;

-- Create table
create table ECV_DRIVING_LICENCE
(
  ID            NUMBER(10) not null,
  XML_ID        NUMBER(10),
  DRIVING_SKILL VARCHAR2(3)
)
tablespace TBS_ED
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate primary, unique and foreign key constraints 
alter table ECV_DRIVING_LICENCE
  add constraint ECV_DRIVING_LICENCE_PK primary key (ID)
  using index 
  tablespace TBS_ED
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table ECV_DRIVING_LICENCE
  add constraint ECV_DRIVING_LICENCE_IBFK_1 foreign key (XML_ID)
  references ECV_XML (ID)
  disable;
  