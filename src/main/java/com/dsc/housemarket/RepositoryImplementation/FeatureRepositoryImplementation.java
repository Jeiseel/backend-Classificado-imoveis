package com.dsc.housemarket.RepositoryImplementation;

import com.dsc.housemarket.Exception.ResourceNotFoundException;
import com.dsc.housemarket.Models.Feature;
import com.dsc.housemarket.Models.Property;
import com.dsc.housemarket.Repository.FeatureRepository;
import com.dsc.housemarket.Repository.PropertyRepository;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.dsc.housemarket.Utils.UserUtils.VerifyIfUserOfRequestIsCreatorOfProperty;

@Component
@Primary
public class FeatureRepositoryImplementation implements FeatureRepository {

    @Autowired
    private FeatureRepository featureDAO;
    private PropertyRepository propertyDAO;


    public Iterable<Feature> getAllFeatures() {
        return featureDAO.findAll();
    }

    public Optional<Feature> getFeatureById(long id) {
        return featureDAO.findById(id);
    }

    public Boolean createFeature(long property_id, Feature feature) {

        Optional<Property> property = propertyDAO.findById(property_id);
        if(property.equals(Optional.empty()))
            return false;

        Boolean isOwner = VerifyIfUserOfRequestIsCreatorOfProperty(property.get());

        if(!isOwner) { return false; }

        Feature newFeature = featureDAO.save(feature);

        property.get().setFeatures(newFeature);

        propertyDAO.save(property.get());

        return true;
    }

    public Feature editFeature(long feature_id, Feature featureRequest) {
        return (Feature) featureDAO.findById(feature_id).map(feature -> {
            feature.setArea(featureRequest.getArea());
            feature.setRooms(featureRequest.getRooms());
            feature.setType(featureRequest.getType());
            return featureDAO.save(feature);
        }).orElseThrow(() -> new ResourceNotFoundException("Error"));
    }

    @Override
    public Optional<Feature> findById(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Feature> findByType(String type) {
        return Optional.empty();
    }

    @Override
    public Optional<Feature> findByArea(float area) {
        return Optional.empty();
    }

    @Override
    public Optional<Feature> findByRooms(int rooms) {
        return Optional.empty();
    }

    @Override
    public List<Feature> findAll() {
        return null;
    }

    @Override
    public List<Feature> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Feature> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Feature> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Feature feature) {

    }

    @Override
    public void deleteAll(Iterable<? extends Feature> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Feature> S save(S s) {
        return null;
    }

    @Override
    public <S extends Feature> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Feature> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Feature> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<Feature> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Feature getOne(Long aLong) {
        return null;
    }

    @Override
    public <S extends Feature> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Feature> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Feature> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Feature> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Feature> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Feature> boolean exists(Example<S> example) {
        return false;
    }
}
