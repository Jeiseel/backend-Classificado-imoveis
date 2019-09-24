package com.dsc.housemarket.Controller;

import java.util.Optional;

import javax.validation.Valid;

import com.dsc.housemarket.Models.Feature;
import com.dsc.housemarket.Models.User;
import com.dsc.housemarket.Repository.FeatureRepository;
import com.dsc.housemarket.Repository.UserRepository;
import com.dsc.housemarket.RepositoryImplementation.PropertyRepositoryImplementation;
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

import com.dsc.housemarket.Models.Property;
import com.dsc.housemarket.Repository.PropertyRepository;

import static com.dsc.housemarket.Utils.UserUtils.*;

@RestController
@RequestMapping("api")
public class PropertyController {

	@Autowired
	private PropertyRepositoryImplementation propertyRepositoryImplementation;

	@GetMapping("/property/all")
	public ResponseEntity<Iterable<Property>> listAll(){
		return new ResponseEntity<Iterable<Property>>(propertyRepositoryImplementation.getAllProperties(), HttpStatus.OK);
	}
	
	@GetMapping("/property/{id}")
	private ResponseEntity<?> seachById(@PathVariable long id){
		Optional<Property> property = propertyRepositoryImplementation.getPropertyById(id);
		if(!property.equals(Optional.empty())) { return new ResponseEntity<>(property, HttpStatus.OK); }
		return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/property/new")
	public ResponseEntity<String> addProperty(@RequestBody Property property) {

		Boolean hasCreated = propertyRepositoryImplementation.createNewProperty(property);

		if(!hasCreated) {
			return new ResponseEntity<String>("Property already exists", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return new ResponseEntity<String>("Property Created", HttpStatus.CREATED);

	}
	
	@PutMapping("/property/{id}")
	public ResponseEntity<String> updateProperty(@PathVariable long id, @Valid @RequestBody Property propertyRequest) {

		Boolean hasEdited = propertyRepositoryImplementation.editProperty(id, propertyRequest);

		if(!hasEdited) {
			return new ResponseEntity<String>("You not are the Owner of this property", HttpStatus.FORBIDDEN);
		}

		return new ResponseEntity<String>("Property has been updated", HttpStatus.OK);
		
	}
	
	@DeleteMapping("/property/{id}")
	public ResponseEntity<String> deleteProperty(@PathVariable long id){

		Boolean hasDeleted = propertyRepositoryImplementation.deleteProperty(id);

		if(!hasDeleted) {
			return new ResponseEntity<String>("You not are the Owner of this property", HttpStatus.FORBIDDEN);
		}

		return new ResponseEntity<String>("The Property has been deleted", HttpStatus.OK);
	}
}
