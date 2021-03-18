package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Order;

import java.util.List;

public interface OrdersService
{

    List<Order> findOrdersWithAdvanceAmount();

    Order findOrdersById(long ordernum);
}
