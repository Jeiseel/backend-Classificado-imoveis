package com.dsc.housemarket.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.dsc.housemarket.Exception.ResourceNotFoundException;
import com.dsc.housemarket.Models.Photo;
import com.dsc.housemarket.Repository.PhotoRepository;

@RestController
@RequestMapping("/photo")
@CrossOrigin(origins = "*")
public class PhotoController {
	
	@Autowired
	private PhotoRepository photos;
	
	@GetMapping
	private List<Photo>list(){
		return photos.findAll();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Photo addPhoto(@RequestBody Photo photo) {
		Optional<Photo> currentPhoto = photos.findById(photo.getId());
		if(currentPhoto.isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " -- > Already Registered User < --");
		return photos.save(photo);
	}
	
	@DeleteMapping("/photo/{photo_id}")
	public ResponseEntity<?> deletePhoto(@PathVariable long id){
		return photos.findById(id).map(photo -> {photos.delete(photo); return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("id"+ id +"not found"));

	}

}