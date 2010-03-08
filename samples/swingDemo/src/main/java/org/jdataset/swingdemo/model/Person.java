package org.jdataset.swingdemo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PERSONS")
public class Person implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "FIRST_NAME", length = 16)
	private String firstName;

	@Column(name = "LAST_NAME", length = 16)
	private String lastName;

	@Column(name = "PHONE")
	private String phone;

	public Person(String firstName, String lastName,String phone) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone =  phone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getName() {
		return lastName + ", " + firstName;
	}

	@Override
	public String toString() {
		return super.toString() + " id = " + getId() + " name = " + getName();
	}

	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
