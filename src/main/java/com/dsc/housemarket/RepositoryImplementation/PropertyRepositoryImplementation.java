package com.dsc.housemarket.RepositoryImplementation;

import com.dsc.housemarket.Models.Feature;
import com.dsc.housemarket.Models.Property;
import com.dsc.housemarket.Models.User;
import com.dsc.housemarket.Repository.FeatureRepository;
import com.dsc.housemarket.Repository.PropertyRepository;
import com.dsc.housemarket.Repository.UserRepository;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static com.dsc.housemarket.Utils.UserUtils.VerifyIfUserOfRequestIsCreatorOfProperty;
import static com.dsc.housemarket.Utils.UserUtils.getUserData;

public class PropertyRepositoryImplementation implements PropertyRepository {

    @Autowired
    private PropertyRepository propertyDAO;
    private UserRepository userDAO;
    private FeatureRepository featureDAO;

    public Iterable<Property> getAllProperties() {
       return propertyDAO.findAll();
    }

    public Optional<Property> getPropertyById(long id) {
        return propertyDAO.findById(id);
    }

    public Boolean createNewProperty(Property property) {

        Optional <Property> currentProperty = propertyDAO.findByName(property.getName());
        if(currentProperty.isPresent()){
            return false;
        }

        Object userData = getUserData();

        property.setCreator(userData.toString());

        Feature newFeature = featureDAO.save(property.getFeatures());

        property.setFeatures(newFeature);

        Property savedProperty = propertyDAO.save(property);

        Optional<User> user = userDAO.findByEmail(userData.toString());
        user.get().addProperty(savedProperty);
        userDAO.save(user.get());

        return true;
    }

    public Boolean editProperty(long id, Property propertyRequest) {
        Optional<Property> existsProperty = propertyDAO.findById(id);

        if(existsProperty.equals(Optional.empty())) {
            return false;
        }

        Boolean isOwner = VerifyIfUserOfRequestIsCreatorOfProperty(existsProperty.get());

        if(!isOwner) { return false; }

        if (propertyRequest.getName() != null) {
            existsProperty.get().setName(propertyRequest.getName());
        }
        if (propertyRequest.getPhotos() != null) {
            existsProperty.get().setPhotos(propertyRequest.getPhotos());
        }

        if (propertyRequest.getFeatures() != null) {
            existsProperty.get().setFeatures(propertyRequest.getFeatures());
        }

        if (propertyRequest.getValue() == 0.0f) {
            existsProperty.get().setValue(propertyRequest.getValue());
        }
        if (propertyRequest.getDescription() != null) {
            existsProperty.get().setDescription(propertyRequest.getDescription());
        }
        if (propertyRequest.getSuperDescription() != null) {
            existsProperty.get().setSuperDescription(propertyRequest.getSuperDescription());
        }

        propertyDAO.save(existsProperty.get());

        return true;
    }

    public Boolean deleteProperty(long id) {
        Optional<Property> existsProperty = propertyDAO.findById(id);

        if(existsProperty.equals(Optional.empty())) { return false; }

        Boolean isOwner = VerifyIfUserOfRequestIsCreatorOfProperty(existsProperty.get());

        if(!isOwner) { return false; }

        Optional<User> propertyOwner = userDAO.findByEmail(existsProperty.get().getCreator());

        propertyOwner.get().removeProperty(existsProperty.get());
        userDAO.save(propertyOwner.get());

        propertyDAO.delete(existsProperty.get());

        return true;
    }

    @Override
    public Optional<Property> findById(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Property> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public <S extends Property> S save(S s) {
        return null;
    }

    @Override
    public <S extends Property> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Property> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Property> findAll() {
        return null;
    }

    @Override
    public Iterable<Property> findAllById(Iterable<Long> iterable) {
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
    public void delete(Property property) {

    }

    @Override
    public void deleteAll(Iterable<? extends Property> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
