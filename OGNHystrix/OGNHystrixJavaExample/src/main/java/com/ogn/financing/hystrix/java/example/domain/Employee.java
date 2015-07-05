package com.ogn.financing.hystrix.java.example.domain;

public class Employee {

	private final Long id;

	private final String firstName;

	private final String lastName;

	public Employee(Long id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Long getId() {
		return this.id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

}
