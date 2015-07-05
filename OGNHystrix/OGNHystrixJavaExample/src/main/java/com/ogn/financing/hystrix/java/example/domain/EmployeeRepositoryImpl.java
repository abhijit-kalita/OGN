package com.ogn.financing.hystrix.java.example.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

	private final List<Employee> employees = new ArrayList<Employee>();

	public EmployeeRepositoryImpl() {
		this.employees.add(new Employee(1L, "First", "Employee"));
		this.employees.add(new Employee(2L, "Second", "Employee"));
		this.employees.add(new Employee(3L, "Third", "Employee"));
	}

	@HystrixCommand(fallbackMethod = "fallback")
	@Override
	public List<Employee> findAll(String isfail) {
		if(isfail.equals("fail")){
			throw new RuntimeException("fail now");
		}
		return this.employees;
	}

	@Override
	public Employee findById(Long id) {
		Employee employee = null;
		for (Employee emp : employees) {
			if (id != null && emp.getId().equals(id)) {
				employee = emp;
			}
		}
		return employee;
	}
	
	

	public List<Employee> fallback(String msg) {
		List<Employee> list = new ArrayList<Employee>();
		list.add(new Employee(9L, "Test", "Test"));
		return list;
	}

}
