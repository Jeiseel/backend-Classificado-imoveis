package com.dsc.housemarket.Repository;

import com.dsc.housemarket.Models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByEmail(String email);

	/*

	Optional<User> findByName(String name);
	User findByEmail(String email);
	Optional<User> findByPassword(String password);
	Optional<User> findByPhone(String phone);

	Object findAll(Pageable pages);
	 */
}
