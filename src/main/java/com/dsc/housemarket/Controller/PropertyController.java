package com.dsc.housemarket.Controller;

import java.util.Optional;

import javax.validation.Valid;

import com.dsc.housemarket.Models.Feature;
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
@RequestMapping("api")
public class PropertyController {

	private final PropertyRepository propertiesDAO;
	private final UserRepository userDAO;
	private final FeatureRepository featureDAO;

	@Autowired
	public PropertyController(PropertyRepository propertiesDAO, FeatureRepository featureDAO, UserRepository userDAO, FeatureRepository featureDAO1, FeatureRepository featureDAO2) {
		this.propertiesDAO = propertiesDAO;
		this.userDAO = userDAO;
		this.featureDAO = featureDAO2;
	}

	@GetMapping("/property/all")
	public ResponseEntity<?> listAll(){
		return new ResponseEntity<>(propertiesDAO.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/property/{id}")
	private ResponseEntity<?> seachById(@PathVariable long id){
		Optional<Property> property = propertiesDAO.findById(id);
		if(!property.equals(Optional.empty())) { return new ResponseEntity<>(property, HttpStatus.OK); }
		return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/property/new")
	public ResponseEntity<String> addProperty(@RequestBody Property property) {

		Optional <Property> currentProperty = propertiesDAO.findByName(property.getName());
		if(currentProperty.isPresent())
				return new ResponseEntity<String>("Property already exists", HttpStatus.BAD_REQUEST);

		Object userData = getUserData();

		property.setCreator(userData.toString());

		Feature newFeature = featureDAO.save(property.getFeatures());

		property.setFeatures(newFeature);

		Property savedProperty = propertiesDAO.save(property);

		if(savedProperty != null){
			Optional<User> user = userDAO.findByEmail(userData.toString());
			user.get().addProperty(savedProperty);
			userDAO.save(user.get());
			return new ResponseEntity<String>("Property Created", HttpStatus.CREATED);
		}

		return new ResponseEntity<String>("Error", HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@PutMapping("/property/{id}")
	public ResponseEntity<String> updateProperty(@PathVariable long id, @Valid @RequestBody Property propertyRequest) {
		Optional<Property> existsProperty = propertiesDAO.findById(id);

		if(existsProperty.equals(Optional.empty())) {
			return new ResponseEntity<String>("This Property Don't exists", HttpStatus.NOT_FOUND);
		}

		Boolean isOwner = VerifyIfUserOfRequestIsCreatorOfProperty(existsProperty.get());

		if(!isOwner) { return new ResponseEntity<String>("You not are the Owner of this property", HttpStatus.FORBIDDEN); }

		if (propertyRequest.getName() != null) {
			existsProperty.get().setName(propertyRequest.getName());
		}
		if (propertyRequest.getPhotos() != null) {
			existsProperty.get().setPhotos(propertyRequest.getPhotos());
		}
		/*
		if (propertyRequest.getFeatures() != null) {
			existsProperty.get().setFeatures(propertyRequest.getFeatures());
		}
		 */
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

		return new ResponseEntity<String>("Property has been updated", HttpStatus.OK);
		
	}
	
	@DeleteMapping("/property/{id}")
	public ResponseEntity<String> deleteProperty(@PathVariable long id){

		Optional<Property> existsProperty = propertiesDAO.findById(id);

		if(existsProperty.equals(Optional.empty())) { return new ResponseEntity<>("This Property don't exists", HttpStatus.NOT_FOUND); }

		Boolean isOwner = VerifyIfUserOfRequestIsCreatorOfProperty(existsProperty.get());

		if(!isOwner) { return new ResponseEntity<String>("You not are the Owner of this property", HttpStatus.FORBIDDEN); }

		featureDAO.delete(existsProperty.get().getFeatures());

		Optional<User> propertyOwner = userDAO.findByEmail(existsProperty.get().getCreator());

		propertyOwner.get().removeProperty(existsProperty.get());
		userDAO.save(propertyOwner.get());

		propertiesDAO.delete(existsProperty.get());

		return new ResponseEntity<String>("The Property has been deleted", HttpStatus.OK);
	}
}
