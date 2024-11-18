package com.example.backend.order.repository;

import java.util.Collection;
import java.util.List;

import com.example.backend.order.entity.Order;
import com.example.backend.order.entity.OrderItem;
import com.example.backend.order.entity.ParentOrder;
import com.example.backend.table.dto.TableOrderResponseVo;

public interface OrderItemRepositoryCustom {
    List<OrderItem> findOrderItemByOrder(List<Order> orders);
    List<OrderItem> findOrderItemByParentOrder(ParentOrder parentOrder);
    List<TableOrderResponseVo> findAllDishOptionsAndChoicesByParentOrder(ParentOrder parentOrder);
}
