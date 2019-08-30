package com.dsc.housemarket.Controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.dsc.housemarket.Exception.ResourceNotFoundException;
import com.dsc.housemarket.Models.Property;
import com.dsc.housemarket.Repository.PropertyRepository;

@RestController
@RequestMapping("/property")
public class PropertyController {

	private final PropertyRepository properties;

	@Autowired
	public PropertyController(PropertyRepository properties) {
		this.properties = properties;
	}

	@GetMapping
	private List<Property>list(){
		return properties.findAll();
	}
	
	@GetMapping("/property_id")
	private ResponseEntity <Property> seachById(@PathVariable long property_id){
		Optional <Property> property = properties.findById(property_id);
		return property.isPresent() ? ResponseEntity.ok(property.get()): ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Property addProperty(@RequestBody Property property) {
		Optional <Property> currentProperty = properties.findByName(property.getName());
		if(currentProperty.isPresent())
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " -- > Already Registered Property < --");
		return properties.save(property);
	}
	
	@PutMapping("/property/{porperty_id}")
	public Property updateProperty(@PathVariable long property_id, @Valid @RequestBody Property propertyRequest) {
		return (Property) properties.findById(property_id).map(property -> {
			property.setName(property.getName());
			property.setPhotos(property.getPhotos());
			property.setValue(property.getValue());
			property.setFeatures(property.getFeatures());
			property.setDescription(property.getDescription());
			property.setSuperdescription(property.getSuperdescription());
			return properties.save(property);
		}).orElseThrow(()-> new ResourceNotFoundException(" Property_id " + property_id +" not found "));
		
	}
	
	@DeleteMapping("/property/porperty_id")
	public ResponseEntity<?> deleteProperty(@PathVariable long property_id){
		return properties.findById(property_id).map(property -> {properties.delete(property); return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("ID" + property_id + "not found" ));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
