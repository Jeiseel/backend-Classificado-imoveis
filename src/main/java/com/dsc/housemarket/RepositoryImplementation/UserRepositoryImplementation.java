package com.dsc.housemarket.RepositoryImplementation;

import com.dsc.housemarket.Models.User;
import com.dsc.housemarket.Repository.UserRepository;
import com.dsc.housemarket.SecurityConfiguration.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.dsc.housemarket.Utils.UserUtils.getUserData;

@Component
@Primary
public class UserRepositoryImplementation implements UserRepository {

    @Autowired
    private UserRepository userDAO;

    public Iterable<User> getAllUsers() {
        Iterable<User> users = userDAO.findAll();

        for (User user: users) { user.setPassword(null); }

        return users;
    }

    public Optional<User> getUserById(long id){
        Optional<User> user = userDAO.findById(id);
        if(!user.equals(Optional.empty())) { user.get().setPassword(null); }
        return user;
    }

    public Boolean createNewUser(User newUser) {
        Optional<User> currentUser = userDAO.findByEmail(newUser.getEmail());
        if(!currentUser.equals(Optional.empty())) { return false; }

        String passwdEncrypted = PasswordEncoder.encodePassword(newUser.getPassword());
        newUser.setPassword(passwdEncrypted);

        newUser.setAdmin(false);

        userDAO.save(newUser);

        return true;
    }

    public Boolean updateUser(long id, User userRequest) {
        Optional<User> existingUser = userDAO.findById(id);
        if(existingUser.equals(Optional.empty())) {
            return false;
        }

        Object userData = getUserData();

        if(!existingUser.get().getEmail().equals(userData)) {
            return false;
        }

        if (userRequest.getName() != null) {
            existingUser.get().setName(userRequest.getName());
        }
        if (userRequest.getEmail() != null) {
            existingUser.get().setEmail(userRequest.getEmail());
        }
        if (userRequest.getPhone() != null) {
            existingUser.get().setPhone(userRequest.getPhone());
        }

        existingUser.get().setAdmin(false);

        userDAO.save(existingUser.get());

        return true;
    }

    public Boolean deleteUser(long id) {
        Optional<User> existingUser = userDAO.findById(id);

        if(existingUser.equals(Optional.empty())) { return false; }

        Object userData = getUserData();

        if(!existingUser.get().getEmail().equals(userData)) { return false; }

        userDAO.deleteById(id);

        return true;
    }


    @Override
    public Optional<User> findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public Iterable<User> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends User> S save(S s) {
        return null;
    }

    @Override
    public <S extends User> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    @Override
    public Iterable<User> findAllById(Iterable<Long> iterable) {
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
    public void delete(User user) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
