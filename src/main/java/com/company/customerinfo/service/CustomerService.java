package com.company.customerinfo.service;


import com.company.customerinfo.model.Customer;
import com.company.customerinfo.model.QCustomer;
import com.company.customerinfo.repository.CustomerRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class.getName());

    private PlatformTransactionManager transactionManager;
    private final EntityManager entityManager;
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository, EntityManager entityManager,
                           PlatformTransactionManager transactionManager) {
        this.customerRepository = customerRepository;
        this.entityManager = entityManager;
        this.transactionManager=transactionManager;
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

    public List<Customer> findAllByQueryDSL(){

        var qCustomer = QCustomer.customer;
        BooleanExpression booleanExpression = qCustomer.id.isNotNull();
        OrderSpecifier<String> orderSpecifier = qCustomer.name.asc();
        Iterable<Customer> result = customerRepository.findAll(booleanExpression, orderSpecifier);

        LOGGER.info("{}", result);

        return (List<Customer>) result;
    }

    @Transactional
    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }

    public Optional<Customer> findCustomerByID(Integer id) {
        return customerRepository.findById(id);
    }

}