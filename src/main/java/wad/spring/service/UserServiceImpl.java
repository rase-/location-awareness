/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.spring.domain.User;
import wad.spring.repository.UserRepository;

/**
 *
 * @author tonykovanen
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void sendOrAcceptFriendRequestByNameToById(String username, Long id) {
        User addingUser = userRepository.findByUsername(username);
        User addedUser = userRepository.findOne(id);
        if (addedUser.getPendingFriendRequests().contains(addingUser)) {
            addedUser.getPendingFriendRequests().add(addingUser);
        } 
        
        if (addingUser.getPendingFriendRequests().contains(addedUser)) {
            addedUser.getPendingFriendRequests().remove(addingUser);
            addingUser.getPendingFriendRequests().remove(addedUser);
            addedUser.getFriends().add(addingUser);
            addingUser.getFriends().add(addedUser);
        }
        
        userRepository.save(addedUser);
        userRepository.save(addingUser);
    }

    @Override
    public User findOny(Long id) {
        return userRepository.findOne(id);
    }
}
