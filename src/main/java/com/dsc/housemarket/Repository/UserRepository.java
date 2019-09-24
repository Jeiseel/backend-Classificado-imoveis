package com.dsc.housemarket.Repository;

import com.dsc.housemarket.Models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	Optional<User> findByEmail(String email);
}
