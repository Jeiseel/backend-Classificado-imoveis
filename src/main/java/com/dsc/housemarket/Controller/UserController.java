package com.dsc.housemarket.Controller;

import java.util.Optional;

import javax.validation.Valid;

import com.dsc.housemarket.SecurityConfiguration.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dsc.housemarket.Models.User;
import com.dsc.housemarket.Repository.UserRepository;

@RestController
@RequestMapping("api")
public class UserController {

	private final UserRepository userDAO;

	@Autowired
	public UserController(UserRepository userDAO) { this.userDAO = userDAO;	}

	@GetMapping("/user/all")
	public ResponseEntity<?> listAll(){

		Iterable<User> users = userDAO.findAll();

		for (User user: users) { user.setPassword(null); }

		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<?> searchById(@PathVariable long id){
		Optional<User> user = userDAO.findById(id);
		if(!user.equals(Optional.empty())) { return new ResponseEntity<>(user, HttpStatus.OK); }
		return new ResponseEntity<String>("Not Found", HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> addUser(@RequestBody User user) {
		Optional<User> currentUser = userDAO.findByEmail(user.getEmail());
		if(!currentUser.equals(Optional.empty()))
				return new ResponseEntity<String>("The Email is already Registered", HttpStatus.UNPROCESSABLE_ENTITY);

		String passwdEncrypted = PasswordEncoder.encodePassword(user.getPassword());
		user.setPassword(passwdEncrypted);

		user.setAdmin(false);

		userDAO.save(user);

		return new ResponseEntity<String>("Account Created", HttpStatus.CREATED);
	}
	
	@PutMapping("/user/{id}")
	public ResponseEntity<String> updateUser(@PathVariable("id") long id, @Valid @RequestBody User userRequest) {
		Optional<User> existingUser = userDAO.findById(id);
		if(existingUser.equals(Optional.empty())) {
			return new ResponseEntity<String>("This User Don't exists", HttpStatus.NOT_FOUND);
		}

		if (userRequest.getName() != null) {
			existingUser.get().setName(userRequest.getName());
		}
		if (userRequest.getEmail() != null) {
			existingUser.get().setEmail(userRequest.getEmail());
		}
		if (userRequest.getPhone() != null) {
			existingUser.get().setPhone(userRequest.getPhone());
		}

		existingUser.get().setAdmin(false);

		userDAO.save(existingUser.get());

		return new ResponseEntity<String>("User has been updated", HttpStatus.OK);
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<String> deleteUSer(@PathVariable long id){

		Boolean existsUser = userDAO.existsById(id);

		if(!existsUser) { return new ResponseEntity<String>("This User don't exists", HttpStatus.NOT_FOUND); }

		userDAO.deleteById(id);

		return new ResponseEntity<String>("The User has been deleted", HttpStatus.OK);
	}
}
