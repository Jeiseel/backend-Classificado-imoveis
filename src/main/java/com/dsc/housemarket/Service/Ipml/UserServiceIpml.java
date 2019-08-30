package com.dsc.housemarket.Service.Ipml;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import com.dsc.housemarket.Models.User;
import com.dsc.housemarket.Repository.UserRepository;
import com.dsc.housemarket.Services.UserService;

//classe que ultiliza os serviços da interface user service.

@Service
/*
public class UserServiceIpml implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User findByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}
	
	@Override
	public User createdOrUpdate(User user) {
		return this.userRepository.save(user);
	}
	
	@Override
	public Optional<User> findById(long user_id){
		return this.userRepository.findById(user_id);
	}
	
	@Override
	public void delete(long user_id) {
		this.userRepository.deleteById(user_id);
	}
	
	// Metodo para paginação, listando os dados por paginas.
	//@SuppressWarnings("deprecation")


	@Override
	public Page<User> findAll(int page, int count){
		Pageable pages = new PageRequest(page, count);
		return (Page<User>) this.userRepository.findAll(pages);
	}

	
	
	

}

 */
public class UserServiceIpml{}