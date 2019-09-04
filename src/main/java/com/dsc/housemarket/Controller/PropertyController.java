package com.dsc.housemarket.Controller;

import java.util.Optional;

import javax.validation.Valid;

import com.dsc.housemarket.Repository.FeatureRepository;
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

@RestController
@RequestMapping("/property")
public class PropertyController {

	private final PropertyRepository propertiesDAO;

	@Autowired
	public PropertyController(PropertyRepository propertiesDAO, FeatureRepository featureDAO) {
		this.propertiesDAO = propertiesDAO;
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
		return new ResponseEntity<>(propertiesDAO.save(property), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateProperty(@PathVariable long id, @Valid @RequestBody Property propertyRequest) {
		Optional<Property> existsProperty = propertiesDAO.findById(id);

		if(existsProperty.equals(Optional.empty())) {
			return new ResponseEntity<>("This Property Don't exists", HttpStatus.NOT_FOUND);
		}

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
		if (propertyRequest.getSuperdescription() != null) {
			existsProperty.get().setSuperdescription(propertyRequest.getSuperdescription());
		}

		propertiesDAO.save(existsProperty.get());

		return new ResponseEntity<>("Property has been updated", HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProperty(@PathVariable long id){

		Boolean existsProperty = propertiesDAO.existsById(id);

		if(!existsProperty) { return new ResponseEntity<>("This Property don't exists", HttpStatus.NOT_FOUND); }

		propertiesDAO.deleteById(id);

		return new ResponseEntity<>("The Property has been deleted", HttpStatus.OK);
	}
}
