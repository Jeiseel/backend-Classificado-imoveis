package com.dsc.housemarket.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dsc.housemarket.Models.Feature;
import com.dsc.housemarket.Models.Photo;
import com.dsc.housemarket.Models.Property;

public interface PropertyRepository extends JpaRepository <Property, Long>{
	Optional<Property> findById(long id);
	Optional<Property> findByName(String name);
	Optional<Property> findByDescription(String description);
	Optional<Property> findBySuperDescription(String superdescription);
	Optional<Property> findByfeatures(Feature features);
	Optional<Property> findByPhotos(Photo photos);
}
