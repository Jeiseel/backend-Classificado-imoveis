package com.dsc.housemarket.Controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.dsc.housemarket.Models.Feature;
import com.dsc.housemarket.Repository.FeatureRepository;

@RestController
@RequestMapping("feature")
public class FeatureController {

	@Autowired
	private FeatureRepository features;
	
	@GetMapping
	private List<Feature>list(){
		return features.findAll();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Feature addFeature(@RequestBody Feature feature) {
		Optional<Feature> currentFeature = features.findById(feature.getId());
		if(currentFeature.isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"-- > Algo deu errado aqui no metodo addFeature em Controlles < --");
		return features.save(feature);
	}
	
	@PutMapping("/{feature_id}")
	public Feature updateFeature(@PathVariable long feature_id, @Valid @RequestBody Feature featureRequest) {
		return (Feature) features.findById(feature_id).map(feature -> {
			feature.setArea(feature.getArea());
			feature.setRooms(feature.getRooms());
			feature.setType(feature.getType());
			return features.save(feature);
		}).orElseThrow(() -> new ResourceNotFoundException("Feature ID" + feature_id + "Não foi encontrado ou não existe!"));
	}
	
	
	@DeleteMapping("/{feature_id}")
	public ResponseEntity<?> deleteFeature(@PathVariable long id){
		return features.findById(id).map(feature -> {features.delete(feature); return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("ID" + id + "não encontrado"));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
