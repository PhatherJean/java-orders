package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Transactional
@Service(value = "ordersService")
public class OrdersServiceImpl implements OrdersService
{
    @Autowired
    private OrdersRepository ordersrepos;

    @Override
    public List<Order> findOrdersWithAdvanceAmount() {
        return  ordersrepos.findAllByAdvanceamountGreaterThan(0.00);
    }

    @Override
    public Order findOrdersById(long id) throws
            EntityNotFoundException
    {
        return ordersrepos.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Order " + id + " Not Found"));
    }

}
