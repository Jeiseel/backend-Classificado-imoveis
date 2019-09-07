package com.dsc.housemarket.Utils;

import com.dsc.housemarket.Models.Property;
import com.dsc.housemarket.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {

    private final UserRepository userDAO;

    @Autowired
    public UserUtils(UserRepository userDAO) { this.userDAO = userDAO;	}

    public static Object getUserData(){
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

    public static Boolean VerifyIfUserOfRequestIsCreatorOfProperty(Property property) {
        Object user = getUserData();
        return user.toString().equals(property.getCreator().toString());
    }

}
