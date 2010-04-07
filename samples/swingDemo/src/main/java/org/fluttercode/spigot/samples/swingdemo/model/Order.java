/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of Spigot.
 *
 * Spigot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Spigot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Spigot.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.spigot.samples.swingdemo.model;

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
