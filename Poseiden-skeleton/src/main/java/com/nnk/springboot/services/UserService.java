package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    /**
     * @return all the User objects registered in the database
     */
    public List<User> loadAllUser() {
        return userRepository.findAll();
    }
    
    /**
     * @return a specify User object according to the Id
     */
    public User findUserByID(Integer id){
        if (id != null) {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                return user.get();
            }
        } else
            log.error("No User found with id " + id);
        return null;
    }

    /**
     * update a User in the database
     */
    public void updateUser(User user) {
        userRepository.save(user);
    }
    
    /**
     * delete a User in the database
     * @param user
     */
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
