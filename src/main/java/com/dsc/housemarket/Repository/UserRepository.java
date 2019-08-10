package com.dsc.housemarket.Repository;

import com.dsc.housemarket.Models.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository <User, Long>{
	Optional<User> findById(Long user_id);
	Optional<User> findByName(String name);
	User findByEmail(String email);
	Optional<User> findByPassword(String password);
	Optional<User> findByPhone(String phone);
}
