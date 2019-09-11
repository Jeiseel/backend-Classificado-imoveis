package com.dsc.housemarket.Controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.dsc.housemarket.Models.Property;
import com.dsc.housemarket.Repository.PropertyRepository;
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
import org.springframework.web.server.ResponseStatusException;

import com.dsc.housemarket.Exception.ResourceNotFoundException;
import com.dsc.housemarket.Models.Feature;
import com.dsc.housemarket.Repository.FeatureRepository;

import static com.dsc.housemarket.Utils.UserUtils.VerifyIfUserOfRequestIsCreatorOfProperty;

@RestController
@RequestMapping("api")
public class FeatureController {

	private final FeatureRepository featureDAO;
	private final PropertyRepository propertyDAO;

	@Autowired
	public FeatureController(FeatureRepository featureDAO, PropertyRepository propertyDAO) {
		this.featureDAO = featureDAO;
		this.propertyDAO = propertyDAO;
	}

	@GetMapping("/feature/all")
	private List<Feature>list(){
		return featureDAO.findAll();
	}

	@PostMapping("/feature/new/{property_id}")
	public ResponseEntity<String> addFeature(@PathVariable long property_id, @RequestBody Feature feature) {
		Optional<Property> property = propertyDAO.findById(property_id);
		if(property.equals(Optional.empty()))
			return new ResponseEntity<String>("This property don't exists", HttpStatus.NOT_FOUND);

		Boolean isOwner = VerifyIfUserOfRequestIsCreatorOfProperty(property.get());

		if(!isOwner) { return new ResponseEntity<String>("You not are the Owner of this property", HttpStatus.FORBIDDEN); }

		Feature newFeature = featureDAO.save(feature);

		property.get().setFeatures(newFeature);

		propertyDAO.save(property.get());

		return new ResponseEntity<String>("Feature Added", HttpStatus.CREATED);

	}

	@PutMapping("/{feature_id}")
	public Feature updateFeature(@PathVariable long feature_id, @Valid @RequestBody Feature featureRequest) {
		return (Feature) featureDAO.findById(feature_id).map(feature -> {
			feature.setArea(feature.getArea());
			feature.setRooms(feature.getRooms());
			feature.setType(feature.getType());
			return featureDAO.save(feature);
		}).orElseThrow(() -> new ResourceNotFoundException("Feature ID" + feature_id + "Não foi encontrado ou não existe!"));
	}


	@DeleteMapping("/{feature_id}")
	public ResponseEntity<?> deleteFeature(@PathVariable long id){
		return featureDAO.findById(id).map(feature -> {featureDAO.delete(feature); return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("ID" + id + "não encontrado"));
	}






















}
