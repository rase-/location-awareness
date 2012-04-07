/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.service;

import java.util.ArrayList;
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
    @Transactional
    public void sendOrAcceptFriendRequestByNameToById(String username, Long id) {
        User addingUser = userRepository.findByUsername(username);
        User addedUser = userRepository.findOne(id);

        if (!addingUser.getFriends().contains(addedUser)) {
            addingUser.getFriends().add(addedUser);
        }
        if (!addedUser.getFriends().contains(addingUser)) {
            addedUser.getFriends().add(addingUser);
        }

        userRepository.save(addedUser);
        userRepository.save(addingUser);
    }

    @Override
    @Transactional(readOnly = true)
    public User findOne(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUnaddedAndNotSelf(String username) {
        User user = userRepository.findByUsername(username);
        List<User> friends = user.getFriends();
        ArrayList<User> unadded = new ArrayList<User>();
        for (User u : userRepository.findAll()) {
            if (!u.equals(user) && !friends.contains(u)) {
                unadded.add(u);
            }
        }
        return unadded;
    }
}
