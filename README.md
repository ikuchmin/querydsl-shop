## Introduction

The project demonstrates how to use [QueryDSL](https://github.com/ikuchmin/querydsl-cuba) add-on in CUBA applications. It can be run as usual CUBA project.   

## Usage

The project presents service beans that implement methods, using type-safe queries.

Here is an example of using `join` in query. It is an extract from the `public List<Order> findCommittedOrdersByStorage(Id<Storage, UUID> storageId, String view)` method in the `OrderRepositoryServiceBeanWorker` service bean.

```java
queryFactory.select(order)
            .from(orderStorageItem).join(orderStorageItem.order, order)
            .where(orderStorageItem.storage.id.eq(storageId.getValue()))
            .orderBy(order.updateTs.desc())
            .fetch(view);
```

Comparing can be performed like in the following example. It is an extract from the `public List<StorageForOrder> findStoragesWhichCanProvideOrder(Id<Order, UUID> orderId, View storageView, View storageItemView)` method in the `StorageRepositoryServiceBeanWorker` service bean:

```java
List<StorageItem> appropriateStorageItems = queryFactory.select(storageItem)
            .from(storageItem, orderItem)
            .where(orderItem.product.id.eq(storageItem.product.id)
                    .and(orderItem.count.lt(storageItem.count))
                    .and(orderItem.order.id.eq(orderId.getValue())))
            .orderBy(storageItem.storage.updateTs.desc())
            .fetch(storageWithItems);

```