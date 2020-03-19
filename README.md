# Introduction

There is a project to demonstrate how to use QueryDSL CUBA.platform App Component 

## Recommend to see

### Join

Method: `com.company.shop.service.OrderRepositoryServiceBeanWorker.findCommittedOrdersByStorage`

##### Query:

```java
queryFactory.select(order)
            .from(orderStorageItem).join(orderStorageItem.order, order)
            .where(orderStorageItem.storage.id.eq(storageId.getValue()))
            .orderBy(order.updateTs.desc())
            .fetch(view);
```

### Comparing

Method: `com.company.shop.service.StorageRepositoryServiceBeanWorker.findStoragesWhichCanProvideOrder(com.haulmont.cuba.core.entity.contracts.Id<com.company.shop.entity.Order,java.util.UUID>, com.haulmont.cuba.core.global.View, com.haulmont.cuba.core.global.View)`

##### Query:

```java
List<StorageItem> appropriateStorageItems = queryFactory.select(storageItem)
            .from(storageItem, orderItem)
            .where(orderItem.product.id.eq(storageItem.product.id)
                    .and(orderItem.count.lt(storageItem.count))
                    .and(orderItem.order.id.eq(orderId.getValue())))
            .orderBy(storageItem.storage.updateTs.desc())
            .fetch(storageWithItems);

```

## How to run

It is a typical CUBA.platform project. You can run it as usual CUBA.platform project. 