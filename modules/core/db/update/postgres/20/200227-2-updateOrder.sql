alter table SHOP_ORDER add column NUMBER_ varchar(255) ^
update SHOP_ORDER set NUMBER_ = '' where NUMBER_ is null ;
alter table SHOP_ORDER alter column NUMBER_ set not null ;
