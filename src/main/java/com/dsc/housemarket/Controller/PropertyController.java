package com.dsc.housemarket.Controller;

import java.util.Optional;

import javax.validation.Valid;

import com.dsc.housemarket.Models.User;
import com.dsc.housemarket.Repository.FeatureRepository;
import com.dsc.housemarket.Repository.UserRepository;
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

import com.dsc.housemarket.Models.Property;
import com.dsc.housemarket.Repository.PropertyRepository;

import static com.dsc.housemarket.Utils.UserUtils.*;

@RestController
@RequestMapping("/property")
public class PropertyController {

	private final PropertyRepository propertiesDAO;
	private final UserRepository userDAO;

	@Autowired
	public PropertyController(PropertyRepository propertiesDAO, FeatureRepository featureDAO, UserRepository userDAO) {
		this.propertiesDAO = propertiesDAO;
		this.userDAO = userDAO;
	}

	@GetMapping
	public ResponseEntity<?> listAll(){
		return new ResponseEntity<>(propertiesDAO.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	private ResponseEntity<?> seachById(@PathVariable long id){
		Optional<Property> property = propertiesDAO.findById(id);
		if(!property.equals(Optional.empty())) { return new ResponseEntity<>(property, HttpStatus.OK); }
		return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
	public ResponseEntity<?> addProperty(@RequestBody Property property) {

		Optional <Property> currentProperty = propertiesDAO.findByName(property.getName());
		if(currentProperty.isPresent())
				return new ResponseEntity<>(" -- > Already Registered Property < --", HttpStatus.BAD_REQUEST);

		Object userData = getUserData();

		property.setCreator(userData.toString());

		Property savedProperty = propertiesDAO.save(property);

		if(savedProperty != null){
			Optional<User> user = userDAO.findByEmail(userData.toString());
			user.get().addProperty(savedProperty);
			userDAO.save(user.get());
			return new ResponseEntity<>("OK", HttpStatus.CREATED);
		}

		return new ResponseEntity<>("Error", HttpStatus.UNPROCESSABLE_ENTITY);


	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateProperty(@PathVariable long id, @Valid @RequestBody Property propertyRequest) {
		Optional<Property> existsProperty = propertiesDAO.findById(id);

		if(existsProperty.equals(Optional.empty())) {
			return new ResponseEntity<>("This Property Don't exists", HttpStatus.NOT_FOUND);
		}

		Boolean isOwner = VerifyIfUserOfRequestIsCreatorOfProperty(existsProperty.get());

		if(!isOwner) { return new ResponseEntity<>("You not are the Owner of this property", HttpStatus.FORBIDDEN); }

		if (propertyRequest.getName() != null) {
			existsProperty.get().setName(propertyRequest.getName());
		}
		if (propertyRequest.getPhotos() != null) {
			existsProperty.get().setPhotos(propertyRequest.getPhotos());
		}
		if (propertyRequest.getFeatures() != null) {
			existsProperty.get().setFeatures(propertyRequest.getFeatures());
		}
		if (propertyRequest.getValue() == 0.0f) {
			existsProperty.get().setValue(propertyRequest.getValue());
		}
		if (propertyRequest.getDescription() != null) {
			existsProperty.get().setDescription(propertyRequest.getDescription());
		}
		if (propertyRequest.getSuperDescription() != null) {
			existsProperty.get().setSuperDescription(propertyRequest.getSuperDescription());
		}

		propertiesDAO.save(existsProperty.get());

		return new ResponseEntity<>("Property has been updated", HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProperty(@PathVariable long id){

		Optional<Property> existsProperty = propertiesDAO.findById(id);

		if(existsProperty.equals(Optional.empty())) { return new ResponseEntity<>("This Property don't exists", HttpStatus.NOT_FOUND); }

		Boolean isOwner = VerifyIfUserOfRequestIsCreatorOfProperty(existsProperty.get());

		if(!isOwner) { return new ResponseEntity<>("You not are the Owner of this property", HttpStatus.FORBIDDEN); }

		propertiesDAO.delete(existsProperty.get());
		//propertiesDAO.deleteById(id);

		return new ResponseEntity<>("The Property has been deleted", HttpStatus.OK);
	}
}
