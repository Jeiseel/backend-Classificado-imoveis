package com.dsc.housemarket.Controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.dsc.housemarket.Models.User;
import com.dsc.housemarket.Repository.UserRepository;
import com.dsc.housemarket.Exception.ResourceNotFoundException;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private UserRepository users;
	
	@GetMapping
	private List<User>list(){
		return users.findAll();
	}
	
	@GetMapping("/{user_id}")
	private ResponseEntity <User> searchById(@PathVariable long user_id){
		Optional<User> user = users.findById(user_id);
		return user.isPresent() ? ResponseEntity.ok(user.get()): ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public User addUser(@RequestBody User user) {
		Optional<User> currentUser = users.findByName(user.getName());
		if(currentUser.isPresent())
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " -- > Already Registered User < --");
		return users.save(user);
	}
	
	@PutMapping("/user/{user_id}")
	public User updateUser(@PathVariable long user_id, @Valid @RequestBody User userRequest) {
		return (User) users.findById(user_id).map( user -> {
			user.setName(user.getName());
			user.setEmail(user.getEmail());
			user.setPassword(user.getPassword());
			user.setPhone(user.getPhone());
			
			return users.save(user);
		}).orElseThrow(() -> new ResourceNotFoundException("User_id" + user_id +"not found"));
	}
	
	@DeleteMapping("/user/{user_id}")
	public ResponseEntity<?> deleteUSer(@PathVariable long user_id){
		return users.findById(user_id).map(user -> {users.delete(user); return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("id" + user_id +"not found"));
	}
}
