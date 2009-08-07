package org.jdataset.db.jpa;

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
