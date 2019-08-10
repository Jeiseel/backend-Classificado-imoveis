package com.dsc.housemarket.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long user_id;
	
	@Size(max = 80)
	@NotEmpty
	@Column
	private String name;
	
	@Size(max = 80)
	@NotEmpty
	@Column
	private String email;
	
	@Size(max = 20)
	@Size(min = 8)
	@NotEmpty
	@Column
	private String password;
	
	@Size(max = 11)
	@NotEmpty
	@Column
	private String phone;
	
	
	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

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
}
