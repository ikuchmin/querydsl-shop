-- begin SHOP_STORAGE_ITEM
create table SHOP_STORAGE_ITEM (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    PRODUCT_ID uuid not null,
    STORAGE_ID uuid,
    COUNT_ integer not null,
    --
    primary key (ID)
)^
-- end SHOP_STORAGE_ITEM
-- begin SHOP_PRODUCT
create table SHOP_PRODUCT (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    --
    primary key (ID)
)^
-- end SHOP_PRODUCT
-- begin SHOP_ORDER
create table SHOP_ORDER (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NUMBER_ varchar(255) not null,
    CUSTOMER_ID uuid,
    ORDER_STATUS varchar(50) not null,
    --
    primary key (ID)
)^
-- end SHOP_ORDER
-- begin SHOP_STORAGE
create table SHOP_STORAGE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    --
    primary key (ID)
)^
-- end SHOP_STORAGE
-- begin SHOP_ORDER_ITEM
create table SHOP_ORDER_ITEM (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    PRODUCT_ID uuid,
    ORDER_ID uuid,
    COUNT_ integer not null,
    --
    primary key (ID)
)^
-- end SHOP_ORDER_ITEM
-- begin SHOP_CUSTOMER
create table SHOP_CUSTOMER (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    FIRST_NAME varchar(255),
    LAST_NAME varchar(255),
    --
    primary key (ID)
)^
-- end SHOP_CUSTOMER
-- begin SHOP_ORDER_STORAGE_ITEM
create table SHOP_ORDER_STORAGE_ITEM (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    ORDER_ID uuid,
    STORAGE_ID uuid,
    --
    primary key (ID)
)^
-- end SHOP_ORDER_STORAGE_ITEM
