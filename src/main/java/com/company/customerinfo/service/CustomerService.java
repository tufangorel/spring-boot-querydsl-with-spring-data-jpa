package com.company.customerinfo.service;


import com.company.customerinfo.model.Customer;
import com.company.customerinfo.model.QCustomer;
import com.company.customerinfo.repository.CustomerRepository;
import com.querydsl.jpa.impl.JPAQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class.getName());

    private final EntityManager entityManager;
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository, EntityManager entityManager) {
        this.customerRepository = customerRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public Customer save(Customer customer){
        return customerRepository.save(customer);
    }

    public List<Customer> findAll(){
        var qCustomer = QCustomer.customer;
        var query = new JPAQuery(entityManager);
        List<Customer> result = query.from(qCustomer).fetch();
        LOGGER.info("Customer list : "+result);
        return result;
    }

    @Transactional
    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }

    public Optional<Customer> findCustomerByID(Integer id) {
        return customerRepository.findById(id);
    }

}