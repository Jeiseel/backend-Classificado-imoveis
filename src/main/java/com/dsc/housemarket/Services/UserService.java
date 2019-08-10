package com.dsc.housemarket.Services;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.dsc.housemarket.Models.User;

public interface UserService {
	
	User findByEmail(String email);
	User createdOrUpdate(User user);
	Optional<User> findById(long user_id);
	void delete(long user_id);
	Page<User> findAll(int page, int count);
	

}
