package com.dsc.housemarket.Controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.dsc.housemarket.Models.Property;
import com.dsc.housemarket.Repository.PropertyRepository;
import com.dsc.housemarket.RepositoryImplementation.FeatureRepositoryImplementation;
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

	@Autowired
	private FeatureRepositoryImplementation featureRepositoryImplementation;


	private final FeatureRepository featureDAO;
	private final PropertyRepository propertyDAO;

	@Autowired
	public FeatureController(FeatureRepository featureDAO, PropertyRepository propertyDAO) {
		this.featureDAO = featureDAO;
		this.propertyDAO = propertyDAO;
	}

	@GetMapping("/feature/all")
	private ResponseEntity<Iterable<Feature>> listAll(){
		return new ResponseEntity<Iterable<Feature>>(featureDAO.findAll(), HttpStatus.OK);
	}

	@GetMapping("/feature/all/{id}")
	private ResponseEntity<?> getProperty(@PathVariable long id){

		Optional<Feature> feature = featureRepositoryImplementation.getFeatureById(id);
		if(!feature.equals(Optional.empty())) { return new ResponseEntity<Feature>(feature.get(), HttpStatus.OK); }
		return new ResponseEntity<String>("", HttpStatus.NOT_FOUND);

	}

	@PostMapping("/feature/new/{property_id}")
	public ResponseEntity<String> addFeature(@PathVariable long property_id, @RequestBody Feature feature) {

		Boolean hasCreated = featureRepositoryImplementation.createFeature(property_id, feature);

		if(!hasCreated) {
			return new ResponseEntity<String>("Error", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return new ResponseEntity<String>("Feature Added", HttpStatus.CREATED);

	}

	@PutMapping("/feature/{feature_id}")
	public Feature updateFeature(@PathVariable long feature_id, @Valid @RequestBody Feature featureRequest) {
		return featureRepositoryImplementation.editFeature(feature_id, featureRequest);
	}


	@DeleteMapping("/feature/{feature_id}")
	public ResponseEntity<?> deleteFeature(@PathVariable long id){
		return featureDAO.findById(id).map(feature -> {featureDAO.delete(feature); return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("ID" + id + "não encontrado"));
	}






















}
