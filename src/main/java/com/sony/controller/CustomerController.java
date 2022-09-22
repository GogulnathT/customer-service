package com.sony.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sony.entity.Customer;
import com.sony.entity.CustomerList;
import com.sony.entity.ErrorInfo;
import com.sony.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
	
	@Autowired
	private CustomerService service;
	
	@PutMapping(path="/{customerId}",
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Object> handleput(@PathVariable String customerId,
			@RequestBody Customer customer){
		try {
			customer = service.updateCustomer(customerId, customer);
			return ResponseEntity.ok(customer);
		}catch (Exception e) {
			return ResponseEntity.status(500).body(new ErrorInfo(e.getMessage()));
		}
	}
	
	@PostMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Object> handlePost(@RequestBody Customer customer){
		try {
			customer = service.addNewCustomer(customer);
			return ResponseEntity.status(HttpStatus.CREATED).body(customer);
		}catch (Exception e) {
			return ResponseEntity.status(500).body(new ErrorInfo(e.getMessage()));
		}
	}
	
	@GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public CustomerList handleGetCustomers(){
		return new CustomerList(service.getAllCustomers());
	}
	
	@GetMapping("/{customerId}")
	public ResponseEntity<Object> handleGetOneCustomer(@PathVariable String customerId){
		Customer customer = service.getCustomerById(customerId);
		if(customerId==null) {
			return ResponseEntity.status(404).body("No data found for id: "+customerId);
		}
		return ResponseEntity.ok(customer);
	}
	
	
}
