package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.AgentsRepository;
import com.lambdaschool.orders.repositories.CustomersRepository;
import com.lambdaschool.orders.repositories.PaymentRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customersService")
public class CustomersServiceImpl implements CustomersService
{
    @Autowired
    private AgentsRepository agentsrepos;
    @Autowired
    private CustomersRepository custrepos;
    @Autowired
    private PaymentRepository paymentrepos;

    @Override
    public List<Customer> findAllCustomers()
    {
        List<Customer> list = new ArrayList<>();

        custrepos.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Override
    public List<Customer> findByCustomerName(String custname)
    {
        return custrepos.findByCustnameContainingIgnoringCase(custname);
    }

    @Override
    public Customer findCustomersById(long id) throws
            EntityNotFoundException
    {
        return custrepos.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Customer " + id + " Not Found"));
    }

    @Transactional
    @Override
    public Customer save(Customer customer)
    {
        Customer newCustomer = new Customer();

        if(customer.getCustcode() != 0)
        {
            custrepos.findById(customer.getCustcode())
                    .orElseThrow(()-> new EntityNotFoundException("Customer " + customer.getCustcode() + " Not Found!"));

            newCustomer.setCustcode(customer.getCustcode());
        }

        newCustomer.setCustname(customer.getCustname());
        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setWorkingarea(customer.getWorkingarea());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setRecieveamt(customer.getRecieveamt());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setPhone(customer.getPhone());


        newCustomer.setAgent(agentsrepos.findById(customer.getAgent()
                .getAgentcode())
                .orElseThrow(()-> new EntityNotFoundException("Agent " + customer.getAgent() + " Not Found")));

        newCustomer.getOrders()
                 .clear();
        for (Order o : customer.getOrders())
        {
            Order newOrder = new Order(o.getOrdamount(),
                                o.getAdvanceamount(),
                    o.getOrderdescription(),
                    newCustomer);

            newOrder.getPayments()
                    .clear();
            for (Payment p : o.getPayments())
            {
                Payment newPayment = paymentrepos.findById(p.getPaymentid())
                        .orElseThrow(()-> new EntityNotFoundException("Payment "+ p.getPaymentid() + " Not Found!"));
               newOrder.getPayments().add(newPayment);
            }
            newCustomer.getOrders()
                    .add(newOrder);
        }

        return custrepos.save(newCustomer);
    }

    @Transactional
    @Override
    public Customer update(Customer customer, long id)
    {
        Customer currentCustomer = custrepos.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Customer "+ id + " Not Found!"));

        if (customer.getCustname() != null)
        {
            currentCustomer.setCustname(customer.getCustname());
        }

        if (customer.getCustcity() != null)
        {
            currentCustomer.setCustcity(customer.getCustcity());
        }

        if (customer.getWorkingarea() != null)
        {
            currentCustomer.setWorkingarea(customer.getWorkingarea());
        }

        if (customer.getCustcountry() != null)
        {
            currentCustomer.setCustcountry(customer.getCustcountry());
        }

        if (customer.getGrade() != null)
        {
            currentCustomer.setGrade(customer.getGrade());
        }

        if (customer.hasvalueforopeningamt)
        {
            currentCustomer.setOpeningamt(customer.getOpeningamt());
        }

        if (customer.hasvalueforrecieveamt)
        {
            currentCustomer.setRecieveamt(customer.getRecieveamt());
        }

        if (customer.hasvalueforpaymentamt)
        {
            currentCustomer.setPaymentamt(customer.getPaymentamt());
        }

        if (customer.hasvalueforoutstandingamt)
        {
            currentCustomer.setOutstandingamt(customer.getOutstandingamt());
        }

        if (customer.getPhone() != null)
        {
            currentCustomer.setPhone(customer.getPhone());
        }

        if (customer.getAgent() != null)
        {
            currentCustomer.setAgent(agentsrepos.findById(customer.getAgent()
            .getAgentcode())
            .orElseThrow(()-> new EntityNotFoundException("Agent " + customer.getAgent() + " Not Found!")));
        }

        currentCustomer.getOrders()
                .clear();
        for (Order o : customer.getOrders())
        {
            Order currentOrder = new Order(o.getOrdamount(),
                    o.getAdvanceamount(),
                    o.getOrderdescription(),
                    currentCustomer);

            currentOrder.getPayments()
                    .clear();
            for (Payment p : o.getPayments())
            {
                Payment newPayment = paymentrepos.findById(p.getPaymentid())
                        .orElseThrow(()-> new EntityNotFoundException("Payment "+ p.getPaymentid() + " Not Found!"));
                currentOrder.getPayments().add(newPayment);
            }
            currentCustomer.getOrders()
                    .add(currentOrder);
        }

        return custrepos.save(currentCustomer);
    }

    @Transactional
    @Override
    public void delete(long id)
    {
        if(custrepos.findById(id)
        .isPresent())
        {
            custrepos.deleteById(id);
        } else
        {
            throw new EntityNotFoundException("Customer " + id + " Not Found!");
        }
    }
}
