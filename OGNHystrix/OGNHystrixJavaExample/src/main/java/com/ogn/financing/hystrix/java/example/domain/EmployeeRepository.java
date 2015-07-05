package com.ogn.financing.hystrix.java.example.domain;


import java.util.List;

public interface EmployeeRepository {

	List<Employee> findAll(String isfail);

	Employee findById(Long id);
	


}
