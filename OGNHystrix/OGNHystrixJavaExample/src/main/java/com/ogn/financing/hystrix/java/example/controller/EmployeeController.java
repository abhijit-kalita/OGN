package com.ogn.financing.hystrix.java.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;




import com.ogn.financing.hystrix.java.example.domain.Employee;
import com.ogn.financing.hystrix.java.example.domain.EmployeeRepository;


@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableHystrix
@Controller
@RequestMapping("/employees")
@ExposesResourceFor(Employee.class)
public class EmployeeController {
	
	private final EmployeeRepository repository;

	private final EntityLinks entityLinks;

	@Autowired
	public EmployeeController(EmployeeRepository repository, EntityLinks entityLinks) {
		this.repository = repository;
		this.entityLinks = entityLinks;
	}

	@RequestMapping(value = "/booleanString/{isfail}",method = RequestMethod.GET)
	HttpEntity<Resources<Employee>> geALLEmployees(@PathVariable String isfail) {
		Resources<Employee> resources = new Resources<Employee>(this.repository.findAll(isfail));
		System.out.println(resources);	
		resources.add(this.entityLinks.linkToCollectionResource(Employee.class));
		return new ResponseEntity<Resources<Employee>>(resources, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	HttpEntity<Resource<Employee>> getEmployeeById(@PathVariable Long id) {
		Resource<Employee> resource = new Resource<Employee>(this.repository.findById(id));
		resource.add(this.entityLinks.linkToSingleResource(Employee.class, id));
		return new ResponseEntity<Resource<Employee>>(resource, HttpStatus.OK);
	}


}
