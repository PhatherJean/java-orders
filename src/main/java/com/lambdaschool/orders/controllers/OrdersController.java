package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.services.OrdersService;
import com.lambdaschool.orders.services.OrdersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController
{
    @Autowired
    OrdersService ordersService;

    @GetMapping(value = "/advanceamount")
    public ResponseEntity<?> getOrdersWithAdvanceAmount()
    {
        List<Order> myOrderList = ordersService.findOrdersWithAdvanceAmount();
        return new ResponseEntity<>(myOrderList, HttpStatus.OK);
    }

    @GetMapping(value = "/order/{ordernum",
    produces = "application/json")
    public ResponseEntity<?> getOrdersByNumber(@PathVariable long ordernum)
    {
        Order o = ordersService.findOrdersById(ordernum);
        return new ResponseEntity<>(o, HttpStatus.OK);
    }

    @PostMapping(value = "/order", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addNewOrder(@Valid @RequestBody Order newOrder)
    {
        newOrder.setOrdnum(0);
        newOrder = ordersService.save(newOrder);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{ordernum}")
                .buildAndExpand(newOrder.getOrdnum())
                .toUri();
        responseHeaders.setLocation(newOrderURI);

        return new ResponseEntity<>(null, responseHeaders,HttpStatus.CREATED);
    }

    @PutMapping(value = "/order/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateOrder(@RequestBody Order updateOrder, @PathVariable long id)
    {
        updateOrder.setOrdnum(id);
        ordersService.save(updateOrder);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/order/{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable long id)
    {
        ordersService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
