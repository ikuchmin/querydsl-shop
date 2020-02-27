-- begin SHOP_STORAGE_ITEM
alter table SHOP_STORAGE_ITEM add constraint FK_SHOP_STORAGE_ITEM_ON_PRODUCT foreign key (PRODUCT_ID) references SHOP_PRODUCT(ID)^
alter table SHOP_STORAGE_ITEM add constraint FK_SHOP_STORAGE_ITEM_ON_STORAGE foreign key (STORAGE_ID) references SHOP_STORAGE(ID)^
create index IDX_SHOP_STORAGE_ITEM_ON_PRODUCT on SHOP_STORAGE_ITEM (PRODUCT_ID)^
create index IDX_SHOP_STORAGE_ITEM_ON_STORAGE on SHOP_STORAGE_ITEM (STORAGE_ID)^
-- end SHOP_STORAGE_ITEM
-- begin SHOP_ORDER
alter table SHOP_ORDER add constraint FK_SHOP_ORDER_ON_CUSTOMER foreign key (CUSTOMER_ID) references SHOP_CUSTOMER(ID)^
create unique index IDX_SHOP_ORDER_UK_NUMBER_ on SHOP_ORDER (NUMBER_) where DELETE_TS is null ^
create index IDX_SHOP_ORDER_ON_CUSTOMER on SHOP_ORDER (CUSTOMER_ID)^
-- end SHOP_ORDER
-- begin SHOP_ORDER_ITEM
alter table SHOP_ORDER_ITEM add constraint FK_SHOP_ORDER_ITEM_ON_PRODUCT foreign key (PRODUCT_ID) references SHOP_PRODUCT(ID)^
alter table SHOP_ORDER_ITEM add constraint FK_SHOP_ORDER_ITEM_ON_ORDER foreign key (ORDER_ID) references SHOP_ORDER(ID)^
create index IDX_SHOP_ORDER_ITEM_ON_PRODUCT on SHOP_ORDER_ITEM (PRODUCT_ID)^
create index IDX_SHOP_ORDER_ITEM_ON_ORDER on SHOP_ORDER_ITEM (ORDER_ID)^
-- end SHOP_ORDER_ITEM
-- begin SHOP_ORDER_STORAGE_ITEM
alter table SHOP_ORDER_STORAGE_ITEM add constraint FK_SHOP_ORDER_STORAGE_ITEM_ON_ORDER foreign key (ORDER_ID) references SHOP_ORDER(ID)^
alter table SHOP_ORDER_STORAGE_ITEM add constraint FK_SHOP_ORDER_STORAGE_ITEM_ON_STORAGE foreign key (STORAGE_ID) references SHOP_STORAGE(ID)^
create index IDX_SHOP_ORDER_STORAGE_ITEM_ON_ORDER on SHOP_ORDER_STORAGE_ITEM (ORDER_ID)^
create index IDX_SHOP_ORDER_STORAGE_ITEM_ON_STORAGE on SHOP_ORDER_STORAGE_ITEM (STORAGE_ID)^
-- end SHOP_ORDER_STORAGE_ITEM
