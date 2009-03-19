/*==============================================================*/
/* DBMS name:      ORACLE Version 10g                           */
/* Created on:     7/28/2008 1:46:11 PM                         */
/*==============================================================*/


create sequence VT_OWNER.ATOMIC_POSITION_SEQ
increment by 1
start with 1
 minvalue 1
nocycle
 nocache
noorder;

create sequence VT_OWNER.BACKOFFICE_ERROR_MESSAGE_SEQ
increment by 1
start with 1;

create sequence VT_OWNER.COUNTERPARTY_SEQ
increment by 1
start with 309
 minvalue 1
nocycle
 nocache
noorder;

create sequence VT_OWNER.DEAL_SEQ
increment by 1
start with 1
 minvalue 1
nocycle
 nocache
noorder;

create sequence VT_OWNER.HISTORY_SEQ
increment by 1
start with 1
 minvalue 1
nocycle
 nocache
noorder;

create sequence VT_OWNER.NOSTRO_ACCOUNT_SEQ
increment by 1
start with 1
 minvalue 1;

create sequence VT_OWNER.POSITION_CORRECTION_SEQ
increment by 1
start with 1
 minvalue 1
nocycle
 nocache
noorder;

create sequence VT_OWNER.POSITION_SEQ
start with 1000;

create sequence VT_OWNER.PROFIT_LOSS_SEQ
increment by 1
start with 1;

create sequence VT_OWNER.REFERENCE_POSITION_SEQ
increment by 1
start with 1
 minvalue 1
nocycle
 nocache
noorder;

create sequence VT_OWNER.ROLE_SEQ
increment by 1
start with 26
 minvalue 1
nocycle;

create sequence VT_OWNER.TOF_ERROR_MESSAGE_SEQ
increment by 1
start with 1;

create sequence VT_OWNER.USER_SEQ
increment by 1
start with 31
 minvalue 1
nocycle;

/*==============================================================*/
/* Table: ACCOUNTING_DEAL                                       */
/*==============================================================*/
create table VT_OWNER.ACCOUNTING_DEAL  (
   ID                   INTEGER                         not null,
   TYPE                 VARCHAR(12)                     not null
)
tablespace OFMP_DATA;

alter table VT_OWNER.ACCOUNTING_DEAL
   add constraint CKC_TYPE_ACCOUNTI check (TYPE in ('CUSTODY_FEES','BILLING','LOAN_DEPOSIT_INTEREST','OVERDRAFT'));

alter table VT_OWNER.ACCOUNTING_DEAL
   add constraint PK_ACCOUNTING_DEAL primary key (ID)
      using index tablespace OFMP_IDX;

grant UPDATE,SELECT,INSERT,DELETE on VT_OWNER.ACCOUNTING_DEAL to VTUSER;

/*==============================================================*/
/* Table: ATOMIC_POSITION                                       */
/*==============================================================*/
create table VT_OWNER.ATOMIC_POSITION  (
   ID                   INTEGER                         not null,
   DEAL                 INTEGER,
   POSITION_CORRECTION  INTEGER,
   DESK                 VARCHAR2(3)                     not null,
   PRODUCT              VARCHAR(15),
   COUNTERPARTY         INTEGER,
   TRADE_DATE           DATE                            not null,
   VALUE_DATE           DATE                            not null,
   BUSINESS_LINE        VARCHAR(15)                     not null,
   HOLDER               INTEGER,
   STATUS               VARCHAR(12),
   AMOUNT               NUMBER(26,8)                    not null,
   CURRENCY             VARCHAR(3),
   SECOND_CURRENCY      VARCHAR2(3)
)
tablespace OFMP_DATA;

comment on column VT_OWNER.ATOMIC_POSITION.STATUS is
'Deal Status';

alter table VT_OWNER.ATOMIC_POSITION
   add constraint CKC_DESK_ATOMIC_P check (DESK in ('FX','MM','CM'));

alter table VT_OWNER.ATOMIC_POSITION
   add constraint CKC_PRODUCT_ATOMIC_P check (PRODUCT is null or (PRODUCT in ('SPOT','FORWARD','SWAP','LOAN','DEPOSIT','TRANSFER')));

alter table VT_OWNER.ATOMIC_POSITION
   add constraint CKC_BUSINESS_LINE_ATOMIC_P check (BUSINESS_LINE in ('DEFAULT','SALES','ACCOUNTING','PROP','DISCREPANCIES'));

alter table VT_OWNER.ATOMIC_POSITION
   add constraint CKC_STATUS_ATOMIC_P check (STATUS is null or (STATUS in ('NEW','VALIDATED','CLOSED','CANCELLED','REVERTED')));

alter table VT_OWNER.ATOMIC_POSITION
   add constraint PK_ATOMIC_POSITION primary key (ID);

alter table VT_OWNER.ATOMIC_POSITION
   add constraint AK_UNIQUE_CORRECTION__ATOMIC_P unique (POSITION_CORRECTION);

/*==============================================================*/
/* Table: BACKOFFICE_DEAL                                       */
/*==============================================================*/
create table VT_OWNER.BACKOFFICE_DEAL  (
   ID                   INTEGER                         not null,
   TYPE                 VARCHAR2(15),
   ORDER_REF            VARCHAR2(30),
   TRANSACTION_REF      VARCHAR2(30)                    not null,
   EXECUTION_DATE       DATE                            not null,
   ACCOUNT_MANAGER_ID   VARCHAR2(20),
   ACCOUNT_MANAGER_FEE  NUMBER
)
tablespace OFMP_DATA;

alter table VT_OWNER.BACKOFFICE_DEAL
   add constraint CKC_TYPE_BACKOFFI check (TYPE is null or (TYPE in ('DEALER','ACCOUNT_MANAGER')));

alter table VT_OWNER.BACKOFFICE_DEAL
   add constraint PK_BACKOFFICE_DEAL primary key (ID)
      using index tablespace OFMP_IDX;

/*==============================================================*/
/* Index: TRANSACTION_REF_IDX                                   */
/*==============================================================*/
create unique index VT_OWNER.TRANSACTION_REF_IDX on VT_OWNER.BACKOFFICE_DEAL (
   TRANSACTION_REF ASC
);

grant UPDATE,SELECT,INSERT,DELETE on VT_OWNER.BACKOFFICE_DEAL to VTUSER;

/*==============================================================*/
/* Table: BACKOFFICE_ERROR_MESSAGE                              */
/*==============================================================*/
create table VT_OWNER.BACKOFFICE_ERROR_MESSAGE  (
   ID                   INTEGER                         not null,
   DEAL                 INTEGER,
   ERROR_TYPE           INTEGER,
   ERROR_CODE           VARCHAR2(20),
   LABEL                VARCHAR2(256)
)
tablespace OFMP_DATA;

alter table VT_OWNER.BACKOFFICE_ERROR_MESSAGE
   add constraint PK_BACKOFFICE_ERROR_MESSAGE primary key (ID)
      using index tablespace OFMP_IDX;

/*==============================================================*/
/* Index: BACKOFFICE_ERROR_MESSAGE_DEAL_                        */
/*==============================================================*/
create index VT_OWNER.BACKOFFICE_ERROR_MESSAGE_DEAL_ on VT_OWNER.BACKOFFICE_ERROR_MESSAGE (
   DEAL ASC
)
tablespace OFMP_IDX;

grant DELETE,INSERT,SELECT,UPDATE on VT_OWNER.BACKOFFICE_ERROR_MESSAGE to VTUSER;

/*==============================================================*/
/* Table: BACKOFFICE_SCHEDULE                                   */
/*==============================================================*/
create table VT_OWNER.BACKOFFICE_SCHEDULE  (
   DEAL                 INTEGER                         not null,
   ORDER_REF            VARCHAR2(30),
   STATUS               VARCHAR(15),
   SCHEDULE_DATE        TIMESTAMP
)
tablespace OFMP_DATA;

alter table VT_OWNER.BACKOFFICE_SCHEDULE
   add constraint PK_BACKOFFICE_SCHEDULE primary key (DEAL)
      using index tablespace OFMP_IDX;

grant UPDATE,SELECT,INSERT,DELETE on VT_OWNER.BACKOFFICE_SCHEDULE to VTUSER;

/*==============================================================*/
/* Table: BANK                                                  */
/*==============================================================*/
create table VT_OWNER.BANK  (
   ID                   INTEGER                         not null,
   BACKOFFICE_ID        VARCHAR(32)                     not null,
   BACKOFFICE_PORTFOLIO INTEGER                         not null,
   BACKOFFICE_ID_CLS    VARCHAR(32),
   BACKOFFICE_PORTFOLIO_CLS INTEGER,
   CLS                  VARCHAR(1)                     default 'F' not null
)
tablespace OFMP_DATA;

alter table VT_OWNER.BANK
   add constraint CKC_CLS_BANK check (CLS in ('T','F'));

alter table VT_OWNER.BANK
   add constraint PK_BANK primary key (ID)
      using index tablespace OFMP_IDX;

grant UPDATE,SELECT,INSERT,DELETE on VT_OWNER.BANK to VTUSER;

/*==============================================================*/
/* Table: BANK_CODE                                             */
/*==============================================================*/
create table VT_OWNER.BANK_CODE  (
   BANK                 INTEGER                         not null,
   CODE                 VARCHAR(32)                     not null,
   SYSTEM_ID            VARCHAR(32)                     not null
);

alter table VT_OWNER.BANK_CODE
   add constraint PK_BANK_CODE primary key (CODE, SYSTEM_ID);

grant DELETE,INSERT,SELECT,UPDATE on VT_OWNER.BANK_CODE to VTUSER;

/*==============================================================*/
/* Table: BUSINESS_DAY                                          */
/*==============================================================*/
create table VT_OWNER.BUSINESS_DAY  (
   BUSINESS_DAY         DATE                            not null
);

alter table VT_OWNER.BUSINESS_DAY
   add constraint PK_BUSINESS_DAY primary key (BUSINESS_DAY);

/*==============================================================*/
/* Table: COUNTERPARTY                                          */
/*==============================================================*/
create table VT_OWNER.COUNTERPARTY  (
   ID                   INTEGER                         not null,
   NAME                 VARCHAR2(128)                   not null,
   TYPE                 VARCHAR(12)                     not null,
   GENERATION           INTEGER                        default 0 not null
)
tablespace OFMP_DATA;

alter table VT_OWNER.COUNTERPARTY
   add constraint CKC_TYPE_COUNTERP check (TYPE in ('CUSTOMER','BANK'));

alter table VT_OWNER.COUNTERPARTY
   add constraint PK_COUNTERPARTY primary key (ID)
      using index tablespace OFMP_IDX;

/*==============================================================*/
/* Index: NAME_KEY                                              */
/*==============================================================*/
create unique index VT_OWNER.NAME_KEY on VT_OWNER.COUNTERPARTY (
   NAME ASC
);

grant UPDATE,SELECT,INSERT,DELETE on VT_OWNER.COUNTERPARTY to VTUSER;

/*==============================================================*/
/* Table: CURRENCY                                              */
/*==============================================================*/
create table VT_OWNER.CURRENCY  (
   ISOCODE              VARCHAR(3)                      not null,
   NAME                 VARCHAR2(32)                    not null,
   CLS                  VARCHAR(1)                     default 'F' not null
)
tablespace OFMP_DATA;

alter table VT_OWNER.CURRENCY
   add constraint CKC_CLS_CURRENCY check (CLS in ('T','F'));

alter table VT_OWNER.CURRENCY
   add constraint PK_CURRENCY primary key (ISOCODE)
      using index tablespace OFMP_IDX;

grant UPDATE,SELECT,INSERT,DELETE on VT_OWNER.CURRENCY to VTUSER;

/*==============================================================*/
/* Table: CURRENCY_RATES                                        */
/*==============================================================*/
create table VT_OWNER.CURRENCY_RATES  (
   ISOCODE              VARCHAR(3)                      not null,
   FIXING_DATE          DATE                            not null,
   RATE                 NUMBER(26,12)                   not null,
   GENERATION           INTEGER                        default 0 not null
);

alter table VT_OWNER.CURRENCY_RATES
   add constraint PK_CURRENCY_RATES primary key (ISOCODE, FIXING_DATE);

grant DELETE,INSERT,SELECT,UPDATE on VT_OWNER.CURRENCY_RATES to VTUSER;

/*==============================================================*/
/* Table: CUSTOMER                                              */
/*==============================================================*/
create table VT_OWNER.CUSTOMER  (
   ID                   INTEGER                         not null,
   SHORT_NAME           VARCHAR(64)
)
tablespace OFMP_DATA;

alter table VT_OWNER.CUSTOMER
   add constraint PK_CUSTOMER primary key (ID)
      using index tablespace OFMP_IDX;

grant UPDATE,SELECT,INSERT,DELETE on VT_OWNER.CUSTOMER to VTUSER;

/*==============================================================*/
/* Table: DEAL                                                  */
/*==============================================================*/
create table VT_OWNER.DEAL  (
   ID                   INTEGER                         not null,
   STATUS               VARCHAR(12)                     not null,
   TRADE_DATE           TIMESTAMP                       not null,
   PRODUCT              VARCHAR(15)                     not null,
   SOURCE               VARCHAR(15)                     not null,
   MESSAGE              VARCHAR2(124),
   GENERATION           INTEGER                        default 0 not null,
   DEALER               INTEGER,
   COUNTERPARTY         INTEGER                         not null,
   EXPORT_TO_BO         VARCHAR(1)                     default 'F' not null,
   CREATE_DATE          TIMESTAMP                       not null,
   HOLDER               INTEGER,
   BUSINESS_LINE        VARCHAR(15)
)
tablespace OFMP_DATA;

alter table VT_OWNER.DEAL
   add constraint CKC_STATUS_DEAL check (STATUS in ('NEW','VALIDATED','CLOSED','CANCELLED','REVERTED'));

alter table VT_OWNER.DEAL
   add constraint CKC_PRODUCT_DEAL check (PRODUCT in ('SPOT','FORWARD','SWAP','LOAN','DEPOSIT','TRANSFER','<Val1>'));

alter table VT_OWNER.DEAL
   add constraint CKC_SOURCE_DEAL check (SOURCE in ('OFMP','TOF','BACKOFFICE','SWIFT'));

alter table VT_OWNER.DEAL
   add constraint CKC_EXPORT_TO_BO_DEAL check (EXPORT_TO_BO in ('T','F'));

alter table VT_OWNER.DEAL
   add constraint CKC_BUSINESS_LINE_DEAL check (BUSINESS_LINE is null or (BUSINESS_LINE in ('DEFAULT','SALES','ACCOUNTING','PROP')));

alter table VT_OWNER.DEAL
   add constraint PK_DEAL primary key (ID)
      using index tablespace OFMP_IDX;

/*==============================================================*/
/* Index: DEAL_TRADEDATE_IDX                                    */
/*==============================================================*/
create index VT_OWNER.DEAL_TRADEDATE_IDX on VT_OWNER.DEAL (
   TRADE_DATE DESC
);

/*==============================================================*/
/* Index: DEAL_STATUS_IDX                                       */
/*==============================================================*/
create index VT_OWNER.DEAL_STATUS_IDX on VT_OWNER.DEAL (
   STATUS ASC
);

/*==============================================================*/
/* Index: DEAL_DEALER_IDX                                       */
/*==============================================================*/
create index VT_OWNER.DEAL_DEALER_IDX on VT_OWNER.DEAL (
   DEALER ASC
)
tablespace OFMP_IDX;

/*==============================================================*/
/* Index: DEAL_COUNTERPARTY_IDX                                 */
/*==============================================================*/
create index VT_OWNER.DEAL_COUNTERPARTY_IDX on VT_OWNER.DEAL (
   COUNTERPARTY ASC
)
tablespace OFMP_IDX;

grant UPDATE,SELECT,INSERT,DELETE on VT_OWNER.DEAL to VTUSER;

/*==============================================================*/
/* Table: DEALER                                                */
/*==============================================================*/
create table VT_OWNER.DEALER  (
   ID                   INTEGER                         not null,
   GENERATION           INTEGER                        default 0 not null
)
tablespace OFMP_DATA;

alter table VT_OWNER.DEALER
   add constraint PK_DEALER primary key (ID)
      using index tablespace OFMP_IDX;

grant UPDATE,SELECT,INSERT,DELETE on VT_OWNER.DEALER to VTUSER;

/*==============================================================*/
/* Table: DEALER_CODE                                           */
/*==============================================================*/
create table VT_OWNER.DEALER_CODE  (
   SYSTEM_ID            VARCHAR(32)                     not null,
   CODE                 VARCHAR(32)                     not null,
   DEALER               INTEGER                         not null
);

alter table VT_OWNER.DEALER_CODE
   add constraint PK_DEALER_CODE primary key (SYSTEM_ID, CODE);

/*==============================================================*/
/* Table: FORWARD_DEAL                                          */
/*==============================================================*/
create table VT_OWNER.FORWARD_DEAL  (
   ID                   INTEGER                         not null,
   NEGOTIATED_CURRENCY  VARCHAR(3)                      not null,
   SECOND_CURRENCY      VARCHAR(3)                      not null,
   VALUE_DATE           DATE                            not null,
   NEGOTIATED_AMOUNT    NUMBER(26,8)                    not null,
   SECOND_AMOUNT        NUMBER(26,8)                    not null,
   SPOT_RATE            NUMBER(26,12)                   not null,
   SWAP_POINTS          NUMBER(26,12)                   not null,
   SPOT_RATE_DIRECTION  VARCHAR2(7)                     not null,
   FORWARD_RATE         NUMBER(26,12)                   not null,
   CLS                  VARCHAR(1)                     default 'F' not null,
   BUY_SELL             VARCHAR2(1)                     not null
)
tablespace OFMP_DATA;

alter table VT_OWNER.FORWARD_DEAL
   add constraint CKC_SPOT_RATE_DIRECTI_FORWARD_ check (SPOT_RATE_DIRECTION in ('NORMAL','INVERSE'));

alter table VT_OWNER.FORWARD_DEAL
   add constraint CKC_CLS_FORWARD_ check (CLS in ('T','F'));

alter table VT_OWNER.FORWARD_DEAL
   add constraint CKC_BUY_SELL_FORWARD_ check (BUY_SELL in ('B','S'));

alter table VT_OWNER.FORWARD_DEAL
   add constraint PK_FORWARD_DEAL primary key (ID)
      using index tablespace OFMP_IDX;

/*==============================================================*/
/* Index: FORWARD_DEAL_SECOND_CURRENCY_I                        */
/*==============================================================*/
create index VT_OWNER.FORWARD_DEAL_SECOND_CURRENCY_I on VT_OWNER.FORWARD_DEAL (
   SECOND_CURRENCY ASC
)
tablespace OFMP_IDX;

/*==============================================================*/
/* Index: FORWARD_DEAL_NEG_CURRENCY_IDX                         */
/*==============================================================*/
create index VT_OWNER.FORWARD_DEAL_NEG_CURRENCY_IDX on VT_OWNER.FORWARD_DEAL (
   NEGOTIATED_CURRENCY ASC
)
tablespace OFMP_IDX;

grant UPDATE,SELECT,INSERT,DELETE on VT_OWNER.FORWARD_DEAL to VTUSER;

/*==============================================================*/
/* Table: HISTORY                                               */
/*==============================================================*/
create table VT_OWNER.HISTORY  (
   ID                   INTEGER                         not null,
   TAG                  VARCHAR(32)                     not null,
   REFERENCE            VARCHAR(200)                    not null,
   TIMESTAMP            TIMESTAMP                       not null,
   MESSAGE              VARCHAR(1024)                   not null,
   DATA                 CLOB,
   OSUSER               VARCHAR(256)                    not null,
   USER_NAME            VARCHAR(256)                    not null,
   USER_ID              INTEGER
)
tablespace OFMP_DATA;

alter table VT_OWNER.HISTORY
   add constraint PK_HISTORY primary key (ID);

/*==============================================================*/
/* Index: REFERENCE_IDX                                         */
/*==============================================================*/
create index VT_OWNER.REFERENCE_IDX on VT_OWNER.HISTORY (
   REFERENCE ASC
);

grant DELETE,INSERT,SELECT,UPDATE on VT_OWNER.HISTORY to VTUSER;

/*==============================================================*/
/* Table: HISTORY_CAUSE                                         */
/*==============================================================*/
create table VT_OWNER.HISTORY_CAUSE  (
   ID                   INTEGER                         not null,
   CAUSE                VARCHAR2(30)                    not null,
   CAUSE_ID             VARCHAR(32)                     not null
)
tablespace OFMP_DATA;

comment on table VT_OWNER.HISTORY_CAUSE is
'Contains cause that generated the related History entry';

alter table VT_OWNER.HISTORY_CAUSE
   add constraint CKC_CAUSE_HISTORY_ check (CAUSE in ('DEAL','POSITION_CORRECTION'));

alter table VT_OWNER.HISTORY_CAUSE
   add constraint PK_HISTORY_CAUSE primary key (ID);

/*==============================================================*/
/* Table: LOAN_DEPOSIT_DEAL                                     */
/*==============================================================*/
create table VT_OWNER.LOAN_DEPOSIT_DEAL  (
   ID                   INTEGER                         not null,
   NEGOTIATED_AMOUNT    NUMBER(26,8)                    not null,
   NEGOTIATED_CURRENCY  VARCHAR2(3)                     not null,
   RATE                 NUMBER(26,12)                   not null,
   INTEREST_AMOUNT      NUMBER(26,8),
   START_DATE           DATE                            not null,
   MATURITY_DATE        DATE                            not null
)
tablespace OFMP_DATA;

alter table VT_OWNER.LOAN_DEPOSIT_DEAL
   add constraint PK_LOAN_DEPOSIT_DEAL primary key (ID);

/*==============================================================*/
/* Table: NOSTRO_ACCOUNT                                        */
/*==============================================================*/
create table VT_OWNER.NOSTRO_ACCOUNT  (
   ID                   INTEGER                         not null,
   BANK                 INTEGER                         not null,
   BACKOFFICE_ACCOUNT   VARCHAR2(40)                    not null,
   CURRENCY             VARCHAR2(3)                     not null
);

alter table VT_OWNER.NOSTRO_ACCOUNT
   add constraint PK_NOSTRO_ACCOUNT primary key (ID);

alter table VT_OWNER.NOSTRO_ACCOUNT
   add constraint AK_ACCOUNT_PER_CORRES_NOSTRO_A unique (BANK, CURRENCY);

alter table VT_OWNER.NOSTRO_ACCOUNT
   add constraint AK_ACCOUNT_PER_CURREN_NOSTRO_A unique (CURRENCY);

/*==============================================================*/
/* Index: BACKOFFICE_ACCOUNT_KEY                                */
/*==============================================================*/
create unique index VT_OWNER.BACKOFFICE_ACCOUNT_KEY on VT_OWNER.NOSTRO_ACCOUNT (
   BACKOFFICE_ACCOUNT ASC
);

/*==============================================================*/
/* Table: POSITION_CORRECTION                                   */
/*==============================================================*/
create table VT_OWNER.POSITION_CORRECTION  (
   ID                   INTEGER                         not null,
   GENERATION           INTEGER                        default 0 not null,
   CREATION_DATE        TIMESTAMP                       not null,
   DEALER               INTEGER,
   MESSAGE              VARCHAR2(124),
   REFERENCE_POSITION   INTEGER,
   KEY_TYPE             VARCHAR2(124)                   not null
);

alter table VT_OWNER.POSITION_CORRECTION
   add constraint CKC_KEY_TYPE_POSITION check (KEY_TYPE in ('PER_CURRENCY','PER_CURRENCY_VALUEDATE','PER_CURRENCY_VALUEDATE_CTPY'));

alter table VT_OWNER.POSITION_CORRECTION
   add constraint PK_POSITION_CORRECTION primary key (ID);

/*==============================================================*/
/* Table: PROFIT_LOSS                                           */
/*==============================================================*/
create table VT_OWNER.PROFIT_LOSS  (
   ID                   NUMBER(6)                       not null,
   TYPE                 VARCHAR(25)                     not null,
   VALUE_DATE           DATE                            not null,
   CURRENCY             VARCHAR(3)                      not null,
   DEAL_ID              INTEGER                         not null,
   VALUE                NUMBER(26,8)                    not null,
   CONVERTED_VALUE      NUMBER(26,8)                    not null
);

comment on column VT_OWNER.PROFIT_LOSS.CONVERTED_VALUE is
'Profit and Loss value converted in Reporting Currency';

alter table VT_OWNER.PROFIT_LOSS
   add constraint CKC_TYPE_PROFIT_L check (TYPE in ('ACCOUNT_MANAGER_SALES','PROP_TRADING'));

alter table VT_OWNER.PROFIT_LOSS
   add constraint PK_PROFIT_LOSS primary key (ID);

/*==============================================================*/
/* Index: PROFIT_LOSS_KEY                                       */
/*==============================================================*/
create unique index VT_OWNER.PROFIT_LOSS_KEY on VT_OWNER.PROFIT_LOSS (
   TYPE ASC,
   VALUE_DATE ASC,
   CURRENCY ASC,
   DEAL_ID ASC
);

/*==============================================================*/
/* Index: PL_DEAL_ID_IDX                                        */
/*==============================================================*/
create index VT_OWNER.PL_DEAL_ID_IDX on VT_OWNER.PROFIT_LOSS (
   DEAL_ID ASC
);

grant DELETE,INSERT,SELECT,UPDATE on VT_OWNER.PROFIT_LOSS to VTUSER;

/*==============================================================*/
/* Table: REFERENCE_POSITION                                    */
/*==============================================================*/
create table VT_OWNER.REFERENCE_POSITION  (
   ID                   INTEGER                         not null,
   CREATION_DATE        DATE                            not null,
   TYPE                 VARCHAR2(20)                    not null,
   SOURCE               VARCHAR2(20)                    not null,
   CURRENCY             VARCHAR(3),
   COUNTERPARTY         INTEGER,
   NOSTRO_ACCOUNT       INTEGER,
   VALUE                NUMBER(26,8)                    not null
);

comment on column VT_OWNER.REFERENCE_POSITION.TYPE is
'BackOffice opening position';

comment on column VT_OWNER.REFERENCE_POSITION.SOURCE is
'BackOffice opening position';

comment on column VT_OWNER.REFERENCE_POSITION.VALUE is
'BackOffice opening position';

alter table VT_OWNER.REFERENCE_POSITION
   add constraint CKC_TYPE_REFERENC check (TYPE in ('FX_SPOT','MM_T0'));

alter table VT_OWNER.REFERENCE_POSITION
   add constraint CKC_SOURCE_REFERENC check (SOURCE in ('BACKOFFICE','SWIFT','MANUAL_INPUT'));

alter table VT_OWNER.REFERENCE_POSITION
   add constraint PK_REFERENCE_POSITION primary key (ID);

/*==============================================================*/
/* Table: ROLE                                                  */
/*==============================================================*/
create table VT_OWNER.ROLE  (
   USR                  INTEGER                         not null,
   ROLE                 VARCHAR(50)                     not null,
   ID                   INTEGER                         not null
)
tablespace OFMP_DATA;

alter table VT_OWNER.ROLE
   add constraint PK_ROLE primary key (USR, ROLE, ID)
      using index tablespace OFMP_IDX;

grant UPDATE,SELECT,INSERT,DELETE on VT_OWNER.ROLE to VTUSER;

/*==============================================================*/
/* Table: SPOT_DEAL                                             */
/*==============================================================*/
create table VT_OWNER.SPOT_DEAL  (
   ID                   INTEGER                         not null,
   NEGOTIATED_CURRENCY  VARCHAR(3)                      not null,
   SECOND_CURRENCY      VARCHAR(3)                      not null,
   VALUE_DATE           DATE                            not null,
   NEGOTIATED_AMOUNT    NUMBER(26,8)                    not null,
   SECOND_AMOUNT        NUMBER(26,8)                    not null,
   RATE                 NUMBER(26,12)                   not null,
   RATE_DIRECTION       VARCHAR2(7)                     not null,
   CLS                  VARCHAR(1)                     default 'F' not null,
   BUY_SELL             VARCHAR2(1)                     not null
)
tablespace OFMP_DATA;

alter table VT_OWNER.SPOT_DEAL
   add constraint CKC_RATE_DIRECTION_SPOT_DEA check (RATE_DIRECTION in ('NORMAL','INVERSE'));

alter table VT_OWNER.SPOT_DEAL
   add constraint CKC_CLS_SPOT_DEA check (CLS in ('T','F'));

alter table VT_OWNER.SPOT_DEAL
   add constraint CKC_BUY_SELL_SPOT_DEA check (BUY_SELL in ('B','S'));

alter table VT_OWNER.SPOT_DEAL
   add constraint PK_SPOT_DEAL primary key (ID)
      using index tablespace OFMP_IDX;

/*==============================================================*/
/* Index: SPOT_DEAL_SECOND_CURRENCY_IDX                         */
/*==============================================================*/
create index VT_OWNER.SPOT_DEAL_SECOND_CURRENCY_IDX on VT_OWNER.SPOT_DEAL (
   SECOND_CURRENCY ASC
)
tablespace OFMP_IDX;

/*==============================================================*/
/* Index: SPOT_DEAL_NEG_CURRENCY_IDX                            */
/*==============================================================*/
create index VT_OWNER.SPOT_DEAL_NEG_CURRENCY_IDX on VT_OWNER.SPOT_DEAL (
   NEGOTIATED_CURRENCY ASC
)
tablespace OFMP_IDX;

grant UPDATE,SELECT,INSERT,DELETE on VT_OWNER.SPOT_DEAL to VTUSER;

/*==============================================================*/
/* Table: SWAP_DEAL                                             */
/*==============================================================*/
create table VT_OWNER.SWAP_DEAL  (
   ID                   INTEGER                         not null,
   TYPE                 VARCHAR(8)                      not null,
   RATE_DIRECTION       VARCHAR2(7)                     not null,
   BUY_SELL             VARCHAR2(1)                     not null
)
tablespace OFMP_DATA;

alter table VT_OWNER.SWAP_DEAL
   add constraint CKC_TYPE_SWAP_DEA check (TYPE in ('STANDARD','HEDGE'));

alter table VT_OWNER.SWAP_DEAL
   add constraint CKC_RATE_DIRECTION_SWAP_DEA check (RATE_DIRECTION in ('NORMAL','INVERSE'));

alter table VT_OWNER.SWAP_DEAL
   add constraint CKC_BUY_SELL_SWAP_DEA check (BUY_SELL in ('B','S'));

alter table VT_OWNER.SWAP_DEAL
   add constraint PK_SWAP_DEAL primary key (ID)
      using index tablespace OFMP_IDX;

grant UPDATE,SELECT,INSERT,DELETE on VT_OWNER.SWAP_DEAL to VTUSER;

/*==============================================================*/
/* Table: SWAP_FAR_LEG                                          */
/*==============================================================*/
create table VT_OWNER.SWAP_FAR_LEG  (
   ID                   INTEGER                         not null,
   NEGOTIATED_AMOUNT    NUMBER(26,8)                    not null,
   NEGOTIATED_CURRENCY  VARCHAR2(3)                     not null,
   SECOND_AMOUNT        NUMBER(26,8)                    not null,
   SECOND_CURRENCY      VARCHAR2(3)                     not null,
   SWAP_POINTS          NUMBER(26,12)                   not null,
   RATE                 NUMBER(26,12)                   not null,
   VALUE_DATE           DATE                            not null,
   CLS                  VARCHAR(1)                     default 'F' not null
)
tablespace OFMP_DATA;

alter table VT_OWNER.SWAP_FAR_LEG
   add constraint CKC_CLS_SWAP_FAR check (CLS in ('T','F'));

alter table VT_OWNER.SWAP_FAR_LEG
   add constraint PK_SWAP_FAR_LEG primary key (ID)
      using index tablespace OFMP_IDX;

grant UPDATE,SELECT,INSERT,DELETE on VT_OWNER.SWAP_FAR_LEG to VTUSER;

/*==============================================================*/
/* Table: SWAP_NEAR_LEG                                         */
/*==============================================================*/
create table VT_OWNER.SWAP_NEAR_LEG  (
   ID                   INTEGER                         not null,
   NEGOTIATED_AMOUNT    NUMBER(26,8)                    not null,
   NEGOTIATED_CURRENCY  VARCHAR2(3)                     not null,
   SECOND_AMOUNT        NUMBER(26,8)                    not null,
   SECOND_CURRENCY      VARCHAR2(3)                     not null,
   BASIS_RATE           NUMBER(26,12)                   not null,
   SPREAD               NUMBER(26,12)                   not null,
   RATE                 NUMBER(26,12)                   not null,
   VALUE_DATE           DATE                            not null,
   CLS                  VARCHAR(1)                     default 'F' not null
)
tablespace OFMP_DATA;

alter table VT_OWNER.SWAP_NEAR_LEG
   add constraint CKC_CLS_SWAP_NEA check (CLS in ('T','F'));

alter table VT_OWNER.SWAP_NEAR_LEG
   add constraint PK_SWAP_NEAR_LEG primary key (ID)
      using index tablespace OFMP_IDX;

grant UPDATE,SELECT,INSERT,DELETE on VT_OWNER.SWAP_NEAR_LEG to VTUSER;

/*==============================================================*/
/* Table: TOF_DEAL                                              */
/*==============================================================*/
create table VT_OWNER.TOF_DEAL  (
   ID                   INTEGER                         not null,
   TICKET_ID            NUMBER(11)                      not null,
   SYSTEM_ID            VARCHAR(32)                     not null
)
tablespace OFMP_DATA;

alter table VT_OWNER.TOF_DEAL
   add constraint PK_TOF_DEAL primary key (ID)
      using index tablespace OFMP_IDX;

/*==============================================================*/
/* Index: PRODDEAL_REUTTICKETID_IDX                             */
/*==============================================================*/
create index VT_OWNER.PRODDEAL_REUTTICKETID_IDX on VT_OWNER.TOF_DEAL (
   TICKET_ID ASC
)
tablespace OFMP_IDX;

grant UPDATE,SELECT,INSERT,DELETE on VT_OWNER.TOF_DEAL to VTUSER;

/*==============================================================*/
/* Table: TOF_ERROR_MESSAGE                                     */
/*==============================================================*/
create table VT_OWNER.TOF_ERROR_MESSAGE  (
   ID                   INTEGER                         not null,
   TICKET               NUMBER(11)                      not null,
   SYSTEM_ID            VARCHAR(32)                     not null,
   LABEL                VARCHAR2(4000)                  not null
)
tablespace OFMP_DATA;

alter table VT_OWNER.TOF_ERROR_MESSAGE
   add constraint PK_TOF_ERROR_MESSAGE primary key (ID)
      using index tablespace OFMP_IDX;

grant UPDATE,SELECT,INSERT,DELETE on VT_OWNER.TOF_ERROR_MESSAGE to VTUSER;

/*==============================================================*/
/* Table: TOF_TICKET                                            */
/*==============================================================*/
create table VT_OWNER.TOF_TICKET  (
   ID                   NUMBER(11)                      not null,
   SYSTEM_ID            VARCHAR(32)                     not null,
   RECEIVED_DATE        DATE                            not null,
   STATUS               VARCHAR2(10)                    not null,
   RF_500               VARCHAR2(256),
   RF_501               VARCHAR2(256),
   RF_502               VARCHAR2(256),
   RF_503               VARCHAR2(256),
   RF_504               VARCHAR2(256),
   RF_505               VARCHAR2(256),
   RF_506               VARCHAR2(256),
   RF_507               VARCHAR2(256),
   RF_508               VARCHAR2(256),
   RF_509               VARCHAR2(256),
   RF_510               VARCHAR2(256),
   RF_511               VARCHAR2(256),
   RF_512               VARCHAR2(256),
   RF_513               VARCHAR2(256),
   RF_514               VARCHAR2(256),
   RF_515               VARCHAR2(256),
   RF_516               VARCHAR2(256),
   RF_517               VARCHAR2(256),
   RF_518               VARCHAR2(256),
   RF_519               VARCHAR2(256),
   RF_520               VARCHAR2(256),
   RF_521               VARCHAR2(256),
   RF_522               VARCHAR2(256),
   RF_523               VARCHAR2(256),
   RF_524               VARCHAR2(256),
   RF_525               VARCHAR2(256),
   RF_526               VARCHAR2(256),
   RF_527               VARCHAR2(256),
   RF_528               VARCHAR2(256),
   RF_529               VARCHAR2(256),
   RF_530               VARCHAR2(256),
   RF_531               VARCHAR2(256),
   RF_532               VARCHAR2(256),
   RF_533               VARCHAR2(256),
   RF_534               VARCHAR2(256),
   RF_535               VARCHAR2(256),
   RF_536               VARCHAR2(256),
   RF_537               VARCHAR2(256),
   RF_538               VARCHAR2(256),
   RF_539               VARCHAR2(256),
   RF_540               VARCHAR2(256),
   RF_541               VARCHAR2(256),
   RF_542               VARCHAR2(256),
   RF_543               VARCHAR2(256),
   RF_544               VARCHAR2(256),
   RF_545               VARCHAR2(256),
   RF_546               VARCHAR2(256),
   RF_547               VARCHAR2(256),
   RF_548               VARCHAR2(4000),
   RF_549               VARCHAR2(256),
   RF_550               VARCHAR2(256),
   RF_551               VARCHAR2(256),
   RF_552               VARCHAR2(256),
   RF_553               VARCHAR2(256),
   RF_554               VARCHAR2(256),
   RF_555               VARCHAR2(256),
   RF_556               VARCHAR2(256),
   RF_557               VARCHAR2(256),
   RF_558               VARCHAR2(256),
   RF_559               VARCHAR2(256),
   RF_560               VARCHAR2(256),
   RF_561               VARCHAR2(256),
   RF_562               VARCHAR2(256),
   RF_563               VARCHAR2(256),
   RF_564               VARCHAR2(256),
   RF_565               VARCHAR2(256),
   RF_566               VARCHAR2(256),
   RF_567               VARCHAR2(256),
   RF_568               VARCHAR2(256),
   RF_569               VARCHAR2(256),
   RF_570               VARCHAR2(256),
   RF_571               VARCHAR2(256),
   RF_572               VARCHAR2(256),
   RF_573               VARCHAR2(256),
   RF_574               VARCHAR2(256),
   RF_575               VARCHAR2(256),
   RF_576               VARCHAR2(256),
   RF_577               VARCHAR2(256),
   RF_578               VARCHAR2(256),
   RF_579               VARCHAR2(256),
   RF_580               VARCHAR2(256),
   RF_581               VARCHAR2(256),
   RF_582               VARCHAR2(256),
   RF_583               VARCHAR2(256),
   RF_584               VARCHAR2(256),
   RF_599               VARCHAR2(4000)
)
tablespace OFMP_DATA;

alter table VT_OWNER.TOF_TICKET
   add constraint CKC_STATUS_TOF_TICK check (STATUS in ('PROCESSING','PROCESSED','ERROR','CANCELLED'));

alter table VT_OWNER.TOF_TICKET
   add constraint PK_REUTERS_TICKET primary key (ID, SYSTEM_ID)
      using index tablespace OFMP_IDX;

grant UPDATE,SELECT,INSERT,DELETE on VT_OWNER.TOF_TICKET to VTUSER;

/*==============================================================*/
/* Table: TRANSFER_DEAL                                         */
/*==============================================================*/
create table VT_OWNER.TRANSFER_DEAL  (
   ID                   INTEGER                         not null,
   NEGOTIATED_AMOUNT    NUMBER(26,8)                    not null,
   NEGOTIATED_CURRENCY  VARCHAR2(3)                     not null,
   DIRECTION            VARCHAR(15)                     not null,
   TRANSFER_DATE        DATE                            not null
)
tablespace OFMP_DATA;

alter table VT_OWNER.TRANSFER_DEAL
   add constraint PK_TRANSFER_DEAL primary key (ID);

/*==============================================================*/
/* Table: USERS                                                 */
/*==============================================================*/
create table VT_OWNER.USERS  (
   ID                   INTEGER                         not null,
   NAME                 VARCHAR(256)                    not null,
   OSUSER               VARCHAR(256)                    not null,
   GENERATION           INTEGER                        default 0 not null,
   ENABLED              VARCHAR(1)                     default 'T' not null
)
tablespace OFMP_DATA;

alter table VT_OWNER.USERS
   add constraint CKC_ENABLED_USERS check (ENABLED in ('T','F'));

alter table VT_OWNER.USERS
   add constraint PK_USERS primary key (ID)
      using index tablespace OFMP_IDX;

/*==============================================================*/
/* Index: OSUSER_KEY                                            */
/*==============================================================*/
create unique index VT_OWNER.OSUSER_KEY on VT_OWNER.USERS (
   OSUSER ASC
);

grant UPDATE,SELECT,INSERT,DELETE on VT_OWNER.USERS to VTUSER;

/*==============================================================*/
/* Table: OFMP_DEAL                                         */
/*==============================================================*/
create table VT_OWNER.OFMP_DEAL  (
   ID                   INTEGER                         not null
)
tablespace OFMP_DATA;

alter table VT_OWNER.OFMP_DEAL
   add constraint PK_OFMP_DEAL primary key (ID)
      using index tablespace OFMP_IDX;

grant UPDATE,SELECT,INSERT,DELETE on VT_OWNER.OFMP_DEAL to VTUSER;

/*==============================================================*/
/* View: V_FX_DEAL                                              */
/*==============================================================*/
create or replace view VT_OWNER.V_FX_DEAL as
select 
    DEALS.ID as ID, STATUS, TRADE_DATE, CREATE_DATE, PRODUCT, SWAP_TYPE, SOURCE, SYSTEM_ID, BUY_SELL, MESSAGE, 
    NEGOTIATED_CURRENCY, SECOND_CURRENCY, VALUE_DATE, NEGOTIATED_AMOUNT, SECOND_AMOUNT,
    SPOT_RATE, RATE_DIRECTION, SWAP_POINTS, FORWARD_RATE, TICKET_ID, DEALS.CLS, 
    DEALS.NEAR_LEG_CLS, DEALS.FAR_LEG_CLS, CP.NAME AS COUNTERPARTY, CP.ID AS COUNTERPARTY_ID, U.NAME AS DEALER, U.ID AS DEALER_ID,
    NVL(BACKOFFICE_STATUS.BACKOFFICE_STATUS, 'NEW') AS BACKOFFICE_STATUS, BACKOFFICE_STATUS.BACKOFFICE_TRANSACTION, 
    BANK.BACKOFFICE_ID AS BACKOFFICE_ID, BANK.BACKOFFICE_ID_CLS AS BACKOFFICE_ID_CLS, FAR_LEG_VALUE_DATE, 
    BUSINESS_LINE, H.NAME as HOLDER, H.ID AS HOLDER_ID 
from 
          (
               select
                      DEAL.ID as ID, DEALER, STATUS, TRADE_DATE, CREATE_DATE, PRODUCT, NULL as SWAP_TYPE, SOURCE, BUY_SELL, MESSAGE, 
                      NEGOTIATED_CURRENCY, SECOND_CURRENCY, VALUE_DATE, NEGOTIATED_AMOUNT, SECOND_AMOUNT,    
                      RATE as SPOT_RATE, RATE_DIRECTION, 0 as SWAP_POINTS, 0 as FORWARD_RATE, COUNTERPARTY, 
                      CLS, NULL as NEAR_LEG_CLS, NULL as FAR_LEG_CLS, NULL as FAR_LEG_VALUE_DATE, 
                      BUSINESS_LINE, HOLDER 
               from 
                      DEAL, SPOT_DEAL
               where
                      DEAL.ID = SPOT_DEAL.ID              
               union              
               select
                      DEAL.ID as ID, DEALER, STATUS, TRADE_DATE, CREATE_DATE, PRODUCT, NULL as SWAP_TYPE, SOURCE, BUY_SELL, MESSAGE, 
                      NEGOTIATED_CURRENCY, SECOND_CURRENCY, VALUE_DATE, NEGOTIATED_AMOUNT, SECOND_AMOUNT,    
                      SPOT_RATE, SPOT_RATE_DIRECTION as RATE_DIRECTION, SWAP_POINTS, FORWARD_RATE, COUNTERPARTY,
                      CLS, NULL as NEAR_LEG_CLS, NULL as FAR_LEG_CLS, NULL as FAR_LEG_VALUE_DATE,
                      BUSINESS_LINE, HOLDER 
               from 
                      DEAL, FORWARD_DEAL
               where
                      DEAL.ID = FORWARD_DEAL.ID                      
               union              
               select
                      DEAL.ID as ID, DEALER, STATUS, TRADE_DATE, CREATE_DATE, PRODUCT, TYPE as SWAP_TYPE, SOURCE, BUY_SELL, MESSAGE, 
                      NEAR_LEG.NEGOTIATED_CURRENCY as NEGOTIATED_CURRENCY, NEAR_LEG.SECOND_CURRENCY as SECOND_CURRENCY, 
                      NEAR_LEG.VALUE_DATE as VALUE_DATE, NEAR_LEG.NEGOTIATED_AMOUNT as NEGOTIATED_AMOUNT, NEAR_LEG.SECOND_AMOUNT as SECOND_AMOUNT,    
                      NEAR_LEG.BASIS_RATE as SPOT_RATE, RATE_DIRECTION, FAR_LEG.SWAP_POINTS as SWAP_POINTS, FAR_LEG.RATE as FORWARD_RATE, COUNTERPARTY, 
                      NEAR_LEG.CLS AS NEAR_LEG_CLS, FAR_LEG.CLS AS FAR_LEG_CLS, NULL AS CLS, FAR_LEG.VALUE_DATE as FAR_LEG_VALUE_DATE, 
                      BUSINESS_LINE, HOLDER 
               from 
                      DEAL, SWAP_DEAL SWAP, SWAP_NEAR_LEG NEAR_LEG, SWAP_FAR_LEG FAR_LEG 
               where
                      DEAL.ID = SWAP.ID
                  and DEAL.ID = NEAR_LEG.ID
                  and DEAL.ID = FAR_LEG.ID
          ) DEALS,    
          (
               select
                      ID, NULL as TICKET_ID, NULL as SYSTEM_ID
               from 
                      OFMP_DEAL              
               union              
               select
                      ID,  TICKET_ID, SYSTEM_ID
               from 
                      TOF_DEAL                      
               union               
               select
                      ID, NULL as TICKET_ID, NULL as SYSTEM_ID
               from 
                      BACKOFFICE_DEAL
               where
                      ACCOUNT_MANAGER_ID is not null               
          ) DEAL_SOURCES,                    
          (
               select DEAL, STATUS as BACKOFFICE_STATUS, NULL as BACKOFFICE_TRANSACTION
               from BACKOFFICE_SCHEDULE            
               union            
               select ID, 'EXECUTED', TRANSACTION_REF as BACKOFFICE_TRANSACTION
               from BACKOFFICE_DEAL                                          
          ) BACKOFFICE_STATUS,                      
          COUNTERPARTY CP,
          BANK BANK,
          USERS U,
          USERS H
where DEALS.ID = DEAL_SOURCES.ID 
      and DEALS.COUNTERPARTY = CP.ID(+) 
      and DEALS.COUNTERPARTY = BANK.ID(+) 
      and DEALS.DEALER = U.ID (+)
      and DEALS.HOLDER = H.ID (+)  
      and DEALS.ID = BACKOFFICE_STATUS.DEAL (+);

/*==============================================================*/
/* View: V_MM_DEAL                                              */
/*==============================================================*/
create or replace view VT_OWNER.V_MM_DEAL(ID, STATUS, TRADE_DATE, CREATE_DATE, PRODUCT, SOURCE, SYSTEM_ID, MESSAGE, NEGOTIATED_CURRENCY, START_DATE, MATURITY_DATE, NEGOTIATED_AMOUNT, RATE, INTEREST_AMOUNT, TICKET_ID, COUNTERPARTY, COUNTERPARTY_ID, DEALER, DEALER_ID, BACKOFFICE_STATUS, BACKOFFICE_TRANSACTION, BACKOFFICE_ID, BACKOFFICE_ID_CLS, BUSINESS_LINE, HOLDER, HOLDER_ID, TRANSFER_DATE, DIRECTION) as
select 
    DEALS.ID as ID, STATUS, TRADE_DATE, CREATE_DATE, PRODUCT, SOURCE, SYSTEM_ID, MESSAGE, 
    NEGOTIATED_CURRENCY, START_DATE, MATURITY_DATE, NEGOTIATED_AMOUNT, RATE, INTEREST_AMOUNT, TICKET_ID, 
    CP.NAME AS COUNTERPARTY, CP.ID AS COUNTERPARTY_ID, U.NAME AS DEALER, U.ID AS DEALER_ID,
    NVL(BACKOFFICE_STATUS.BACKOFFICE_STATUS, 'NEW') AS BACKOFFICE_STATUS, BACKOFFICE_STATUS.BACKOFFICE_TRANSACTION, 
    BANK.BACKOFFICE_ID AS BACKOFFICE_ID, BANK.BACKOFFICE_ID_CLS AS BACKOFFICE_ID_CLS, 
    BUSINESS_LINE, H.NAME AS HOLDER, H.ID AS HOLDER_ID, TRANSFER_DATE, DIRECTION  
from 
          (
              select
	                  DEAL.ID as ID, DEALER, HOLDER, BUSINESS_LINE, STATUS, TRADE_DATE, CREATE_DATE, PRODUCT, SOURCE, MESSAGE, 
	                  NEGOTIATED_CURRENCY, NEGOTIATED_AMOUNT, 
                      START_DATE, MATURITY_DATE, RATE, INTEREST_AMOUNT,  COUNTERPARTY,
                      NULL as TRANSFER_DATE, NULL as DIRECTION
	          from 
	                  DEAL, LOAN_DEPOSIT_DEAL
	          where
	                  DEAL.ID = LOAN_DEPOSIT_DEAL.ID
              union 
              select
	                  DEAL.ID as ID, DEALER, HOLDER, BUSINESS_LINE, STATUS, TRADE_DATE, CREATE_DATE, PRODUCT, SOURCE, MESSAGE, 
	                  NEGOTIATED_CURRENCY, NEGOTIATED_AMOUNT,
                      NULL as START_DATE, NULL as MATURITY_DATE, NULL as RATE, NULL as INTEREST_AMOUNT,  NULL as COUNTERPARTY,
                      TRANSFER_DATE, DIRECTION
	          from 
	                  DEAL, TRANSFER_DEAL
                  where
	                  DEAL.ID = TRANSFER_DEAL.ID
          ) DEALS,    
          (
               select
                      ID, NULL as TICKET_ID, NULL as SYSTEM_ID
               from 
                      OFMP_DEAL              
               union              
               select
                      ID,  TICKET_ID, SYSTEM_ID
               from 
                      TOF_DEAL                      
               union               
               select
                      ID, NULL as TICKET_ID, NULL as SYSTEM_ID
               from 
                      BACKOFFICE_DEAL
               where
                      ACCOUNT_MANAGER_ID is not null
          ) DEAL_SOURCES,                    
          (
               select DEAL, STATUS as BACKOFFICE_STATUS, NULL as BACKOFFICE_TRANSACTION
               from BACKOFFICE_SCHEDULE            
               union            
               select ID, 'EXECUTED', TRANSACTION_REF as BACKOFFICE_TRANSACTION
               from BACKOFFICE_DEAL                                          
          ) BACKOFFICE_STATUS,                      
          COUNTERPARTY CP,
          BANK BANK,
          USERS U,
          USERS H
where DEALS.ID = DEAL_SOURCES.ID 
      and DEALS.COUNTERPARTY = CP.ID(+) 
      and DEALS.COUNTERPARTY = BANK.ID(+) 
      and DEALS.DEALER = U.ID (+) 
      and DEALS.HOLDER = H.ID (+) 
      and DEALS.ID = BACKOFFICE_STATUS.DEAL (+);

alter table VT_OWNER.ATOMIC_POSITION
   add constraint FK_ATOMIC_POSITION_CTPY foreign key (COUNTERPARTY)
      references VT_OWNER.COUNTERPARTY (ID);

alter table VT_OWNER.ATOMIC_POSITION
   add constraint FK_ATOMIC_POSITION_CURRENCY foreign key (CURRENCY)
      references VT_OWNER.CURRENCY (ISOCODE);

alter table VT_OWNER.ATOMIC_POSITION
   add constraint FK_ATOMIC_POSITION_DEAL foreign key (DEAL)
      references VT_OWNER.DEAL (ID);

alter table VT_OWNER.ATOMIC_POSITION
   add constraint FK_ATOMIC_POSITION_HOLDER foreign key (HOLDER)
      references VT_OWNER.DEALER (ID);

alter table VT_OWNER.BACKOFFICE_DEAL
   add constraint BACKOFFICE_DEAL_DEAL_ID_FK foreign key (ID)
      references VT_OWNER.DEAL (ID)
      on delete cascade;

alter table VT_OWNER.BACKOFFICE_ERROR_MESSAGE
   add constraint APSERRMESS_APSSCHED_DEAL_FK foreign key (DEAL)
      references VT_OWNER.BACKOFFICE_SCHEDULE (DEAL)
      on delete cascade;

alter table VT_OWNER.BACKOFFICE_SCHEDULE
   add constraint BACKOFFICE_SCHEDULE_DEAL_ID_FK foreign key (DEAL)
      references VT_OWNER.DEAL (ID)
      on delete cascade;

alter table VT_OWNER.BANK
   add constraint BANK_COUNTERPARTY_ID_FK foreign key (ID)
      references VT_OWNER.COUNTERPARTY (ID)
      on delete cascade;

alter table VT_OWNER.BANK_CODE
   add constraint FK_BANK_COD_REFERENCE_BANK foreign key (BANK)
      references VT_OWNER.BANK (ID)
      on delete cascade;

alter table VT_OWNER.CURRENCY_RATES
   add constraint CURRENCY_CURRENCY_RATES_FK foreign key (ISOCODE)
      references VT_OWNER.CURRENCY (ISOCODE);

alter table VT_OWNER.CUSTOMER
   add constraint CUSTOMER_CTPY_ID_FK foreign key (ID)
      references VT_OWNER.COUNTERPARTY (ID)
      on delete cascade;

alter table VT_OWNER.DEAL
   add constraint DEAL_CTPY_ID_FK foreign key (COUNTERPARTY)
      references VT_OWNER.COUNTERPARTY (ID);

alter table VT_OWNER.DEAL
   add constraint DEAL_DEALER_ID_FK foreign key (DEALER)
      references VT_OWNER.DEALER (ID);

alter table VT_OWNER.DEAL
   add constraint FK_DEAL_FK_DEALER_DEALER foreign key (HOLDER)
      references VT_OWNER.DEALER (ID);

alter table VT_OWNER.DEALER
   add constraint FK_DEALER_FK_DEALER_USERS foreign key (ID)
      references VT_OWNER.USERS (ID);

alter table VT_OWNER.DEALER_CODE
   add constraint FK_DEALER_C_FK_DEALER_DEALER foreign key (DEALER)
      references VT_OWNER.DEALER (ID);

alter table VT_OWNER.FORWARD_DEAL
   add constraint DEAL_FWD_DEAL_FK foreign key (ID)
      references VT_OWNER.DEAL (ID)
      on delete cascade not deferrable;

alter table VT_OWNER.FORWARD_DEAL
   add constraint FWD_DEAL_CURRENCY_ISOCODE_FK foreign key (NEGOTIATED_CURRENCY)
      references VT_OWNER.CURRENCY (ISOCODE)
      not deferrable;

alter table VT_OWNER.FORWARD_DEAL
   add constraint FWD_DEAL_CURRENCY_SEC_CURR_FK foreign key (SECOND_CURRENCY)
      references VT_OWNER.CURRENCY (ISOCODE)
      not deferrable;

alter table VT_OWNER.HISTORY
   add constraint HISTORY_USERS_FK foreign key (USER_ID)
      references VT_OWNER.USERS (ID);

alter table VT_OWNER.HISTORY_CAUSE
   add constraint HISTORY_HISTORY_CAUSE_FK foreign key (ID)
      references VT_OWNER.HISTORY (ID)
      on delete cascade;

alter table VT_OWNER.LOAN_DEPOSIT_DEAL
   add constraint FK_LOAN_DEP_LOAN_DEPO_DEAL foreign key (ID)
      references VT_OWNER.DEAL (ID);

alter table VT_OWNER.NOSTRO_ACCOUNT
   add constraint NOSTRO_ACCOUNT_BANK_ID_FK foreign key (BANK)
      references VT_OWNER.BANK (ID)
      on delete cascade;

alter table VT_OWNER.POSITION_CORRECTION
   add constraint FK_POSITION_CORRECTION_DEALER foreign key (DEALER)
      references VT_OWNER.DEALER (ID);

alter table VT_OWNER.POSITION_CORRECTION
   add constraint FK_POSITION_CORR_REF_POSITION foreign key (REFERENCE_POSITION)
      references VT_OWNER.REFERENCE_POSITION (ID);

alter table VT_OWNER.PROFIT_LOSS
   add constraint PROFIT_LOSS_CURRENCY_FK foreign key (CURRENCY)
      references VT_OWNER.CURRENCY (ISOCODE);

alter table VT_OWNER.PROFIT_LOSS
   add constraint PROFIT_LOSS_DEAL_FK foreign key (DEAL_ID)
      references VT_OWNER.DEAL (ID)
      on delete cascade;

alter table VT_OWNER.REFERENCE_POSITION
   add constraint FK_REFERENCE_POSITION_CTPY foreign key (COUNTERPARTY)
      references VT_OWNER.COUNTERPARTY (ID);

alter table VT_OWNER.REFERENCE_POSITION
   add constraint FK_REFERENCE_POSITION_CURRENCY foreign key (CURRENCY)
      references VT_OWNER.CURRENCY (ISOCODE);

alter table VT_OWNER.ROLE
   add constraint ROLE_USERS_ID_FK foreign key (USR)
      references VT_OWNER.USERS (ID);

alter table VT_OWNER.SPOT_DEAL
   add constraint SPOT_DEAL_CURRENCY_NEG_CURR_FK foreign key (NEGOTIATED_CURRENCY)
      references VT_OWNER.CURRENCY (ISOCODE)
      not deferrable;

alter table VT_OWNER.SPOT_DEAL
   add constraint SPOT_DEAL_CURRENCY_SEC_CURR_FK foreign key (SECOND_CURRENCY)
      references VT_OWNER.CURRENCY (ISOCODE)
      not deferrable;

alter table VT_OWNER.SPOT_DEAL
   add constraint SPOT_DEAL_DEAL_ID_FK foreign key (ID)
      references VT_OWNER.DEAL (ID)
      on delete cascade not deferrable;

alter table VT_OWNER.SWAP_DEAL
   add constraint SWAP_DEAL_ID_FK foreign key (ID)
      references VT_OWNER.DEAL (ID)
      on delete cascade;

alter table VT_OWNER.SWAP_FAR_LEG
   add constraint SWAP_FAR_LEG_SWAP_ID_FK foreign key (ID)
      references VT_OWNER.SWAP_DEAL (ID)
      on delete cascade;

alter table VT_OWNER.SWAP_NEAR_LEG
   add constraint SWAP_NEAR_LEG_SWAP_ID_FK foreign key (ID)
      references VT_OWNER.SWAP_DEAL (ID)
      on delete cascade;

alter table VT_OWNER.TOF_DEAL
   add constraint PROP_DEAL_DEAL_ID_FK foreign key (ID)
      references VT_OWNER.DEAL (ID)
      on delete cascade;

alter table VT_OWNER.TOF_DEAL
   add constraint PROP_DEAL_REUTERS_TICKET_ID_FK foreign key (TICKET_ID, SYSTEM_ID)
      references VT_OWNER.TOF_TICKET (ID, SYSTEM_ID);

alter table VT_OWNER.TOF_ERROR_MESSAGE
   add constraint RTERRMESS_RT_TICKET_ID_FK foreign key (TICKET, SYSTEM_ID)
      references VT_OWNER.TOF_TICKET (ID, SYSTEM_ID)
      on delete cascade;

alter table VT_OWNER.TRANSFER_DEAL
   add constraint FK_TRANSFER_REFERENCE_DEAL foreign key (ID)
      references VT_OWNER.DEAL (ID);

alter table VT_OWNER.OFMP_DEAL
   add constraint CUSTOMER_DEAL_DEAL_ID_FK foreign key (ID)
      references VT_OWNER.DEAL (ID)
      on delete cascade;

/*==============================================================*/
/* Synonym: ATOMIC_POSITION_SEQ                                 */
/*==============================================================*/
create or replace public synonym ATOMIC_POSITION_SEQ for VT_OWNER.ATOMIC_POSITION_SEQ;

/*==============================================================*/
/* Synonym: BACKOFFICE_ERROR_MESSAGE_SEQ                        */
/*==============================================================*/
create or replace public synonym BACKOFFICE_ERROR_MESSAGE_SEQ for VT_OWNER.BACKOFFICE_ERROR_MESSAGE_SEQ;

/*==============================================================*/
/* Synonym: COUNTERPARTY_SEQ                                    */
/*==============================================================*/
create or replace public synonym COUNTERPARTY_SEQ for VT_OWNER.COUNTERPARTY_SEQ;

/*==============================================================*/
/* Synonym: DEAL_SEQ                                            */
/*==============================================================*/
create or replace public synonym DEAL_SEQ for VT_OWNER.DEAL_SEQ;

/*==============================================================*/
/* Synonym: NOSTRO_ACCOUNT_SEQ                                  */
/*==============================================================*/
create or replace public synonym NOSTRO_ACCOUNT_SEQ for VT_OWNER.NOSTRO_ACCOUNT_SEQ;

/*==============================================================*/
/* Synonym: POSITION_CORRECTION_SEQ                             */
/*==============================================================*/
create or replace public synonym POSITION_CORRECTION_SEQ for VT_OWNER.POSITION_CORRECTION_SEQ;

/*==============================================================*/
/* Synonym: POSITION_SEQ                                        */
/*==============================================================*/
create or replace public synonym POSITION_SEQ for VT_OWNER.POSITION_SEQ;

/*==============================================================*/
/* Synonym: PROFIT_LOSS_SEQ                                     */
/*==============================================================*/
create or replace public synonym PROFIT_LOSS_SEQ for VT_OWNER.PROFIT_LOSS_SEQ;

/*==============================================================*/
/* Synonym: REFERENCE_POSITION_SEQ                              */
/*==============================================================*/
create or replace public synonym REFERENCE_POSITION_SEQ for VT_OWNER.REFERENCE_POSITION_SEQ;

/*==============================================================*/
/* Synonym: REUTERS_ERROR_MESSAGE_SEQ                           */
/*==============================================================*/
create or replace public synonym REUTERS_ERROR_MESSAGE_SEQ for VT_OWNER.TOF_ERROR_MESSAGE_SEQ;

/*==============================================================*/
/* Synonym: ROLE_SEQ                                            */
/*==============================================================*/
create or replace public synonym ROLE_SEQ for VT_OWNER.ROLE_SEQ;

/*==============================================================*/
/* Synonym: USER_SEQ                                            */
/*==============================================================*/
create or replace public synonym USER_SEQ for VT_OWNER.USER_SEQ;

/*==============================================================*/
/* Synonym: ACCOUNTING_DEAL                                     */
/*==============================================================*/
create or replace public synonym ACCOUNTING_DEAL for VT_OWNER.ACCOUNTING_DEAL;

/*==============================================================*/
/* Synonym: ATOMIC_POSITION                                     */
/*==============================================================*/
create or replace public synonym ATOMIC_POSITION for VT_OWNER.ATOMIC_POSITION;

/*==============================================================*/
/* Synonym: BACKOFFICE_DEAL                                     */
/*==============================================================*/
create or replace public synonym BACKOFFICE_DEAL for VT_OWNER.BACKOFFICE_DEAL;

/*==============================================================*/
/* Synonym: BACKOFFICE_ERROR_MESSAGE                            */
/*==============================================================*/
create or replace public synonym BACKOFFICE_ERROR_MESSAGE for VT_OWNER.BACKOFFICE_ERROR_MESSAGE;

/*==============================================================*/
/* Synonym: BACKOFFICE_SCHEDULE                                 */
/*==============================================================*/
create or replace public synonym BACKOFFICE_SCHEDULE for VT_OWNER.BACKOFFICE_SCHEDULE;

/*==============================================================*/
/* Synonym: BANK                                                */
/*==============================================================*/
create or replace public synonym BANK for VT_OWNER.BANK;

/*==============================================================*/
/* Synonym: BANK_CODE                                           */
/*==============================================================*/
create or replace public synonym BANK_CODE for VT_OWNER.BANK_CODE;

/*==============================================================*/
/* Synonym: BUSINESS_DAY                                        */
/*==============================================================*/
create or replace public synonym BUSINESS_DAY for VT_OWNER.BUSINESS_DAY;

/*==============================================================*/
/* Synonym: COUNTERPARTY                                        */
/*==============================================================*/
create or replace public synonym COUNTERPARTY for VT_OWNER.COUNTERPARTY;

/*==============================================================*/
/* Synonym: CURRENCY                                            */
/*==============================================================*/
create or replace public synonym CURRENCY for VT_OWNER.CURRENCY;

/*==============================================================*/
/* Synonym: CURRENCY_RATES                                      */
/*==============================================================*/
create or replace public synonym CURRENCY_RATES for VT_OWNER.CURRENCY_RATES;

/*==============================================================*/
/* Synonym: CUSTOMER                                            */
/*==============================================================*/
create or replace public synonym CUSTOMER for VT_OWNER.CUSTOMER;

/*==============================================================*/
/* Synonym: DEAL                                                */
/*==============================================================*/
create or replace public synonym DEAL for VT_OWNER.DEAL;

/*==============================================================*/
/* Synonym: DEALER                                              */
/*==============================================================*/
create or replace public synonym DEALER for VT_OWNER.DEALER;

/*==============================================================*/
/* Synonym: DEALER_CODE                                         */
/*==============================================================*/
create or replace public synonym DEALER_CODE for VT_OWNER.DEALER_CODE;

/*==============================================================*/
/* Synonym: FRONT_OFFICE_ID                                     */
/*==============================================================*/
create or replace public synonym FRONT_OFFICE_ID for VT_OWNER.DEALER_CODE;

/*==============================================================*/
/* Synonym: HISTORY                                             */
/*==============================================================*/
create or replace public synonym HISTORY for VT_OWNER.HISTORY;

/*==============================================================*/
/* Synonym: HISTORY_CAUSE                                       */
/*==============================================================*/
create or replace public synonym HISTORY_CAUSE for VT_OWNER.HISTORY_CAUSE;

/*==============================================================*/
/* Synonym: LOAN_DEPOSIT_DEAL                                   */
/*==============================================================*/
create or replace public synonym LOAN_DEPOSIT_DEAL for VT_OWNER.LOAN_DEPOSIT_DEAL;

/*==============================================================*/
/* Synonym: NOSTRO_ACCOUNT                                      */
/*==============================================================*/
create or replace public synonym NOSTRO_ACCOUNT for VT_OWNER.NOSTRO_ACCOUNT;

/*==============================================================*/
/* Synonym: POSITION_CORRECTION                                 */
/*==============================================================*/
create or replace public synonym POSITION_CORRECTION for VT_OWNER.POSITION_CORRECTION;

/*==============================================================*/
/* Synonym: PROFIT_LOSS                                         */
/*==============================================================*/
create or replace public synonym PROFIT_LOSS for VT_OWNER.PROFIT_LOSS;

/*==============================================================*/
/* Synonym: REFERENCE_POSITION                                  */
/*==============================================================*/
create or replace public synonym REFERENCE_POSITION for VT_OWNER.REFERENCE_POSITION;

/*==============================================================*/
/* Synonym: REUTERS_BANK_CODE                                   */
/*==============================================================*/
create or replace public synonym REUTERS_BANK_CODE for VT_OWNER.BANK_CODE;

/*==============================================================*/
/* Synonym: REUTERS_ERROR_MESSAGE                               */
/*==============================================================*/
create or replace public synonym REUTERS_ERROR_MESSAGE for VT_OWNER.TOF_ERROR_MESSAGE;

/*==============================================================*/
/* Synonym: REUTERS_TICKET                                      */
/*==============================================================*/
create or replace public synonym REUTERS_TICKET for VT_OWNER.TOF_TICKET;

/*==============================================================*/
/* Synonym: ROLE                                                */
/*==============================================================*/
create or replace public synonym ROLE for VT_OWNER.ROLE;

/*==============================================================*/
/* Synonym: SPOT_DEAL                                           */
/*==============================================================*/
create or replace public synonym SPOT_DEAL for VT_OWNER.SPOT_DEAL;

/*==============================================================*/
/* Synonym: SWAP_DEAL                                           */
/*==============================================================*/
create or replace public synonym SWAP_DEAL for VT_OWNER.SWAP_DEAL;

/*==============================================================*/
/* Synonym: SWAP_FAR_LEG                                        */
/*==============================================================*/
create or replace public synonym SWAP_FAR_LEG for VT_OWNER.SWAP_FAR_LEG;

/*==============================================================*/
/* Synonym: SWAP_NEAR_LEG                                       */
/*==============================================================*/
create or replace public synonym SWAP_NEAR_LEG for VT_OWNER.SWAP_NEAR_LEG;

/*==============================================================*/
/* Synonym: TOF_DEAL                                            */
/*==============================================================*/
create or replace public synonym TOF_DEAL for VT_OWNER.TOF_DEAL;

/*==============================================================*/
/* Synonym: TRANSFER_DEAL                                       */
/*==============================================================*/
create or replace public synonym TRANSFER_DEAL for VT_OWNER.TRANSFER_DEAL;

/*==============================================================*/
/* Synonym: USERS                                               */
/*==============================================================*/
create or replace public synonym USERS for VT_OWNER.USERS;

/*==============================================================*/
/* Synonym: OFMP_DEAL                                       */
/*==============================================================*/
create or replace public synonym OFMP_DEAL for VT_OWNER.OFMP_DEAL;

/*==============================================================*/
/* Synonym: V_FX_DEAL                                           */
/*==============================================================*/
create or replace public synonym V_FX_DEAL for VT_OWNER.V_FX_DEAL;

/*==============================================================*/
/* Synonym: V_MM_DEAL                                           */
/*==============================================================*/
create or replace public synonym V_MM_DEAL for VT_OWNER.V_MM_DEAL;
/

exit