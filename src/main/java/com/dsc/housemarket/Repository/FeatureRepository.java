package com.dsc.housemarket.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dsc.housemarket.Models.Feature;

public interface FeatureRepository extends JpaRepository <Feature, Long> {
	Optional<Feature> findById(long id);
	Optional<Feature> findByType(String type);
	Optional<Feature> findByArea(float area);
	Optional<Feature> findByRooms(int rooms);

}
