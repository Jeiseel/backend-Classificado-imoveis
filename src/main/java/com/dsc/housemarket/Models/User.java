package com.dsc.housemarket.Models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotEmpty
	private String name;

	@NotEmpty
	@Column(unique = true)
	private String email;

	@NotEmpty
	private String password;

	@NotEmpty
	private String phone;

	@Column
	@OneToMany(cascade = CascadeType.REMOVE)
	private List<Property> propertyList;

	private boolean admin = false;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public List<Property> getPropertyList() {
		return propertyList;
	}

	public void addProperty(Property property) {
		this.propertyList.add(property);
	}

	public void removeProperty(Property property) { this.propertyList.remove(property); }
}
