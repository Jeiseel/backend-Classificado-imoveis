package com.dsc.housemarket.Utils;

import com.dsc.housemarket.Models.Property;
import com.dsc.housemarket.Models.User;
import com.dsc.housemarket.Repository.FeatureRepository;
import com.dsc.housemarket.Repository.PropertyRepository;
import com.dsc.housemarket.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class UserUtils {

    private  UserRepository userDAO;
    private  PropertyRepository propertyDAO;
    private  FeatureRepository featureDAO;

    @Autowired
    public UserUtils(UserRepository userDAO, PropertyRepository propertyDAO, FeatureRepository featureDAO) {
        this.userDAO = userDAO;
        this.propertyDAO = propertyDAO;
        this.featureDAO = featureDAO;
    }

    public static Object getUserData(){
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

    public static Boolean VerifyIfUserOfRequestIsCreatorOfProperty(Property property) {
        Object user = getUserData();
        return user.toString().equals(property.getCreator().toString());
    }

}
