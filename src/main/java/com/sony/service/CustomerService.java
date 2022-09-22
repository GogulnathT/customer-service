package com.sony.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sony.entity.Customer;
import com.sony.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository repo;

	public List<Customer> getAllCustomers() {
		return repo.findAll();
	}

	public Customer getCustomerById(String customerId) {
		Optional<Customer> op = repo.findById(customerId);
		return op.isEmpty() ? null: op.get();
	}

	public Customer addNewCustomer(Customer customer) {
		customer.setCustomerId(customer.getCustomerId());
		return repo.save(customer);
	}

	public Customer updateCustomer(String customerId, Customer customer) {
		Customer c1 = this.getCustomerById(customerId);
		if(c1==null) {
			throw new ServiceException("Customer Id does not exist");
		}
		customer.setCustomerId(customerId);
		return repo.save(customer);
	}
	
	
	
	
}
