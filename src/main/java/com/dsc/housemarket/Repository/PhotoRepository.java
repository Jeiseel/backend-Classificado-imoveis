package com.dsc.housemarket.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dsc.housemarket.Models.Photo;

public interface PhotoRepository extends JpaRepository <Photo, Long>{
	Optional<Photo> findById(long id);
	Optional<Photo> findByPath(String path);
	Optional<Photo> findByPropertyId(long propertyId);
}
