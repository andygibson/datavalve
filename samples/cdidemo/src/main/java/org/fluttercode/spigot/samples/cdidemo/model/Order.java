/*
* Copyright 2010, Andrew M Gibson
*
* www.andygibson.net
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.fluttercode.spigot.samples.cdidemo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ORDERS")
/**
 * @author Andy Gibson
 * 
 */
public class Order implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static String ORDER_STATUS_PENDING = "PENDING";
	public static String ORDER_STATUS_CANCELLED = "CANCELLED";
	public static String ORDER_STATUS_SHIPPED = "SHIPPED";
	public static String ORDER_STATUS_DELIVERED = "DELIVERED";

	
	
	public Order(Integer value, Person person,String orderStatus) {
		super();
		this.value = value;		
		this.person = person;
		this.orderStatus = orderStatus;
	}
	
	public Order(Integer value, Person person) {
		this(value,person,ORDER_STATUS_PENDING);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="ORDER_VALUE")
	private Integer value;
	
	@Column(name="STATUS")
	private String orderStatus;
	
	@ManyToOne
	@JoinColumn(name="PERSON_ID")
	private Person person;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	
}
