package com.dsc.housemarket.Repository;

import java.util.Optional;

import com.dsc.housemarket.Models.Property;
import org.springframework.data.repository.CrudRepository;

public interface PropertyRepository extends CrudRepository<Property, Long> {
	Optional<Property> findById(long id);
	Optional<Property> findByName(String name);
	/*
	Optional<Property> findByDescription(String description);
	Optional<Property> findBySuperDescription(String superdescription);
	Optional<Property> findByfeatures(Feature features);
	 */
}
