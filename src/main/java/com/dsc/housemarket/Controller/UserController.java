package com.dsc.housemarket.Controller;

import java.util.Optional;

import javax.validation.Valid;

import com.dsc.housemarket.Repository.FeatureRepository;
import com.dsc.housemarket.Repository.PropertyRepository;
import com.dsc.housemarket.RepositoryImplementation.UserRepositoryImplementation;
import com.dsc.housemarket.SecurityConfiguration.PasswordEncoder;
import com.sun.org.apache.xpath.internal.operations.Bool;
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

import static com.dsc.housemarket.Utils.UserUtils.getUserData;

@RestController
@RequestMapping("api")
public class UserController {

	@Autowired
	private UserRepositoryImplementation userRepositoryImplementation;

	@GetMapping("/user/all")
	public ResponseEntity<?> listAll(){
		Iterable<User> users = userRepositoryImplementation.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<?> searchById(@PathVariable long id){
		Optional<User> user = userRepositoryImplementation.getUserById(id);
		if(!user.equals(Optional.empty())) { return new ResponseEntity<>(user, HttpStatus.OK); }
		return new ResponseEntity<String>("Not Found", HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> addUser(@RequestBody User user) {
		Boolean hasCreated = userRepositoryImplementation.createNewUser(user);

		if(!hasCreated) {
			return new ResponseEntity<String>("The Email is already Registered", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return new ResponseEntity<String>("Account Created", HttpStatus.CREATED);
	}
	
	@PutMapping("/user/{id}")
	public ResponseEntity<String> updateUser(@PathVariable("id") long id, @Valid @RequestBody User userRequest) {
		Boolean userUpdated = userRepositoryImplementation.updateUser(id, userRequest);

		if(!userUpdated) {
			return new ResponseEntity<String>("You don't have permission", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return new ResponseEntity<String>("User has been updated", HttpStatus.OK);
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<String> deleteUSer(@PathVariable long id){
		Boolean userDeleted = userRepositoryImplementation.deleteUser(id);

		if(!userDeleted) {
			return new ResponseEntity<String>("You don't have permission", HttpStatus.FORBIDDEN);
		}

		return new ResponseEntity<String>("The User has been deleted", HttpStatus.OK);
	}
}
