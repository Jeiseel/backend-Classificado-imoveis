package com.dsc.housemarket.Controller;

import java.util.Optional;

import javax.validation.Valid;

import com.dsc.housemarket.Repository.FeatureRepository;
import com.dsc.housemarket.Repository.PropertyRepository;
import com.dsc.housemarket.SecurityConfiguration.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dsc.housemarket.Models.User;
import com.dsc.housemarket.Repository.UserRepository;

import static com.dsc.housemarket.Utils.UserUtils.getUserData;

@CrossOrigin("http://localhost")
@RestController
@RequestMapping("api")
public class UserController {

	private final UserRepository userDAO;
	private final PropertyRepository propertyDAO;
	private final FeatureRepository featureDAO;

	@Autowired
	public UserController(UserRepository userDAO, PropertyRepository propertyDAO, FeatureRepository featureDAO) {
	    this.userDAO = userDAO;
        this.propertyDAO = propertyDAO;
        this.featureDAO = featureDAO;
    }

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
			return new ResponseEntity<String>("You don't have permission", HttpStatus.UNPROCESSABLE_ENTITY);
		}

        Object userData = getUserData();

		if(!existingUser.get().getEmail().equals(userData)) {
            return new ResponseEntity<String>("You don't have permission", HttpStatus.UNPROCESSABLE_ENTITY);
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

        Optional<User> existingUser = userDAO.findById(id);
        if(existingUser.equals(Optional.empty())) {
            return new ResponseEntity<String>("You don't have permission", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Object userData = getUserData();

        if(!existingUser.get().getEmail().equals(userData)) {
            return new ResponseEntity<String>("You don't have permission", HttpStatus.UNPROCESSABLE_ENTITY);
        }

		userDAO.deleteById(id);

		return new ResponseEntity<String>("The User has been deleted", HttpStatus.OK);
	}
}
