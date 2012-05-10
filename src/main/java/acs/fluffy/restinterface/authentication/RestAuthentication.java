/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package acs.fluffy.restinterface.authentication;

import acs.fluffy.domain.User;
import acs.fluffy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tonykova
 */
@Service
public class RestAuthentication implements AuthenticationService {
    @Autowired
    UserRepository userRepository;
    
    @Override
    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
    
}
