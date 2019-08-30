package com.dsc.housemarket.Controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("user")
public class UserController {

	private final UserRepository userDAO;

	@Autowired
	public UserController(UserRepository userDAO) { this.userDAO = userDAO;	}

	@GetMapping
	public ResponseEntity<?> listAll(){
		return new ResponseEntity<>(userDAO.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> searchById(@PathVariable long id){
		Optional<User> user = userDAO.findById(id);
		if(!user.equals(Optional.empty())) { return new ResponseEntity<>(user, HttpStatus.OK); }
		return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
	public ResponseEntity<?> addUser(@RequestBody User user) {
		Optional<User> currentUser = userDAO.findByEmail(user.getEmail());
		if(!currentUser.equals(Optional.empty()))
				return new ResponseEntity<>("The Email is already Registered", HttpStatus.UNPROCESSABLE_ENTITY);
		return new ResponseEntity<>(userDAO.save(user), HttpStatus.CREATED);
	}
	
	@PutMapping("/user/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") long id, @Valid @RequestBody User userRequest) {
		Optional<User> existingUser = userDAO.findById(id);
		if(existingUser.equals(Optional.empty())) {
			return new ResponseEntity<>("This User Don't exists", HttpStatus.NOT_FOUND);
		}

		if (existingUser.get().getName() != null) {
			existingUser.get().setName(userRequest.getName());
		}
		if (existingUser.get().getEmail() != null) {
			existingUser.get().setEmail(userRequest.getEmail());
		}
		if (existingUser.get().getPhone() != null) {
			existingUser.get().setPhone(userRequest.getPhone());
		}

		userDAO.save(existingUser.get());

		return new ResponseEntity<>("User has been updated", HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUSer(@PathVariable long id){

		Boolean existsUser = userDAO.existsById(id);

		if(!existsUser) { return new ResponseEntity<>("This User don't exists", HttpStatus.NOT_FOUND); }

		userDAO.deleteById(id);

		return new ResponseEntity<>("The User has been deleted", HttpStatus.OK);
	}
}
