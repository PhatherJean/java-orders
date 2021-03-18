package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.services.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/customers")
public class CustomersController
{
    @Autowired
    private CustomersService customersService;

    @GetMapping(value = "/orders", produces = "application/json")
    public ResponseEntity<?> listAllCustomers()
    {
        List<Customer> myCustomers = customersService.findAllCustomers();
        return new ResponseEntity<>(myCustomers, HttpStatus.OK);
    }

    @GetMapping(value = "/customer/{custid}",
    produces = "application/json")
    public ResponseEntity<?> getCustomersById(@PathVariable long custid)
    {
        Customer c = customersService.findCustomersById(custid);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }

    @GetMapping(value = "/namelike/{custnane}",
    produces = "application/json")
    public ResponseEntity<?> findCustomerByName(@PathVariable String custname)
    {
        List<Customer> myCustomerList = customersService.findByCustomerName(custname);
        return new ResponseEntity<>(myCustomerList, HttpStatus.OK);
    }

    @PostMapping(value = "/customer",
    consumes = "application/json",
    produces = "application/json")
    public ResponseEntity<?> addNewCustomer(@Valid @RequestBody Customer newCustomer)
    {
        newCustomer.setCustcode(0);
        newCustomer = customersService.save(newCustomer);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{custid}")
                .buildAndExpand(newCustomer.getCustcode())
                .toUri();
        responseHeaders.setLocation(newCustomerURI);

        return new ResponseEntity<>(newCustomer, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/customer/{custid}",
    consumes = "apllication/json",
    produces = "application/json")
    public ResponseEntity<?> replaceCustomer(@RequestBody Customer updateCustomer, @PathVariable long custid)
    {
        updateCustomer.setCustcode(custid);
        updateCustomer = customersService.save(updateCustomer);
        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }

    @PatchMapping(value = "/customer/{custid}",
    consumes = "application/json",
    produces = "application/json")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer updateCustomer, @PathVariable long custid)
    {
        customersService.update(updateCustomer, custid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/customer/{custid}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable long custid)
    {
        customersService.delete(custid);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
